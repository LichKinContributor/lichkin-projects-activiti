package com.lichkin.activiti.CompleteProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.impl.SysActivitiFormDataService;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiComplateProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiCompleteProcessOut_SingleLineProcess;
import com.lichkin.framework.activiti.services.impl.LKActivitiCompleteProcessService_SingleLineProcess;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.framework.utils.LKEnumUtils;
import com.lichkin.springframework.configs.LKApplicationContext;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiApiRequestLogCompleteProcessEntity;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service(Statics.SERVICE_NAME)
public class S extends LKDBService implements LKApiService<I, O> {

	@Autowired
	private SysActivitiFormDataService activitiFormDataService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		complete_process_failed(30000),

		process_type_config_error(30000),

		;

		private final Integer code;

	}


	@Override
	@Transactional
	public O handle(I sin, ApiKeyValues<I> params) throws LKException {
		String userId = params.getCompId() + "_" + params.getUser().getId();

		// 根据流程类型执行
		ProcessTypeEnum processType = LKEnumUtils.getEnum(ProcessTypeEnum.class, sin.getProcessType());
		String processInstanceId = sin.getProcessInstanceId();

		// 保存日志
		SysActivitiApiRequestLogCompleteProcessEntity log = LKBeanUtils.newInstance(false, sin.getDatas(), SysActivitiApiRequestLogCompleteProcessEntity.class);
		log.setUserId(userId);
		log.setProcessType(processType);
		log.setProcessInstanceId(processInstanceId);
		dao.persistOne(log);

		if (sin.getProcessType() != null) {
			switch (processType) {
				case SINGLE_LINE:
					return singleLine(processInstanceId, userId);
			}
		}

		throw new LKRuntimeException(ErrorCodes.process_type_config_error);
	}


	@Autowired
	private LKActivitiCompleteProcessService_SingleLineProcess slp;


	private O singleLine(String processInstanceId, String userId) {
		// 初始化入参
		LKActivitiComplateProcessIn_SingleLineProcess i = new LKActivitiComplateProcessIn_SingleLineProcess(processInstanceId, userId);

		// 调用activiti办理
		LKActivitiCompleteProcessOut_SingleLineProcess o = slp.completeProcess(i);

		// 初始化出参
		O out = new O(o.isProcessIsEnd());

		SysActivitiFormDataEntity formDataEntity = activitiFormDataService.getByProcessInstanceId(processInstanceId);

		ActivitiCallbackService activitiCallbackService = (ActivitiCallbackService) LKApplicationContext.getBean(formDataEntity.getProcessCode());
		activitiCallbackService.approve(formDataEntity, (byte) (formDataEntity.getStepCount() - o.getSurplusStep()));

		// 流程结束 修改表单状态
		if (o.isProcessIsEnd()) {
			activitiCallbackService.finish(activitiFormDataService.finish(formDataEntity));
		}

		// 返回结果
		return out;
	}

}
