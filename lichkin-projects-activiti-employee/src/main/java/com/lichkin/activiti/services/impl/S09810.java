package com.lichkin.activiti.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09810;
import com.lichkin.activiti.beans.out.impl.O09810;
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
import com.lichkin.springframework.entities.impl.SysActivitiApiRequestLogCompleteProcessEntity;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 办理流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09810 extends LKDBService implements LKApiService<I09810, O09810> {

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
	public O09810 handle(I09810 sin, String locale, String compId, String loginId) throws LKException {
		// 根据流程类型执行
		ProcessTypeEnum processType = LKEnumUtils.getEnum(ProcessTypeEnum.class, sin.getProcessType());

		// 保存日志
		SysActivitiApiRequestLogCompleteProcessEntity log = LKBeanUtils.newInstance(false, sin.getDatas(), SysActivitiApiRequestLogCompleteProcessEntity.class);
		log.setUserId(sin.getUserId());
		log.setProcessType(processType);
		log.setProcessInstanceId(sin.getProcessInstanceId());
		dao.persistOne(log);

		if (sin.getProcessType() != null) {
			switch (processType) {
				case SINGLE_LINE:
					return completeProcessTask(sin);
			}
		}

		throw new LKRuntimeException(ErrorCodes.process_type_config_error);
	}


	@Autowired
	private LKActivitiCompleteProcessService_SingleLineProcess slp;


	/**
	 * 办理单线流程
	 * @param in 办理流程入参
	 * @return 办理流程结果
	 */
	private O09810 completeProcessTask(I09810 in) {
		// 初始化入参
		LKActivitiComplateProcessIn_SingleLineProcess i = new LKActivitiComplateProcessIn_SingleLineProcess(in.getProcessInstanceId(), in.getUserId());

		// 调用activiti办理
		LKActivitiCompleteProcessOut_SingleLineProcess o = slp.completeProcess(i);

		// 初始化出参
		O09810 out = new O09810(o.isProcessIsEnd());

		SysActivitiFormDataEntity formDataEntity = activitiFormDataService.getByProcessInstanceId(in.getProcessInstanceId());

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
