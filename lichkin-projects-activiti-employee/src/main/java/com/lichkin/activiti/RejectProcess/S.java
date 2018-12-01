package com.lichkin.activiti.RejectProcess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.application.services.ActivitiCallbackService;
import com.lichkin.application.services.impl.SysActivitiFormDataService;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiRejectProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiRejectProcessOut_SingleLineProcess;
import com.lichkin.framework.activiti.services.impl.LKActivitiRejectProcessService_SingleLineProcess;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.utils.LKEnumUtils;
import com.lichkin.springframework.configs.LKApplicationContext;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiVoidService;
import com.lichkin.springframework.services.LKDBService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service(Statics.SERVICE_NAME)
public class S extends LKDBService implements LKApiVoidService<I> {

	@Autowired
	private SysActivitiFormDataService activitiFormDataService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		process_type_config_error(30000),

		;

		private final Integer code;

	}


	@Override
	@Transactional
	public void handle(I sin, ApiKeyValues<I> params) throws LKException {
		if (sin.getProcessType() != null) {
			// 根据流程类型执行
			ProcessTypeEnum processType = LKEnumUtils.getEnum(ProcessTypeEnum.class, sin.getProcessType());
			switch (processType) {
				case SINGLE_LINE:
					singleLine(sin, params.getCompId() + "_" + params.getUser().getId());
					return;
			}
		}

		throw new LKRuntimeException(ErrorCodes.process_type_config_error);
	}


	@Autowired
	private LKActivitiRejectProcessService_SingleLineProcess slp;


	private void singleLine(I in, String userId) throws LKException {
		// 初始化入参
		LKActivitiRejectProcessIn_SingleLineProcess i = new LKActivitiRejectProcessIn_SingleLineProcess(in.getProcessInstanceId(), userId, in.getComment());

		@SuppressWarnings("unused")
		LKActivitiRejectProcessOut_SingleLineProcess o = slp.RejectProcess(i);

		// 修改表单驳回状态
		SysActivitiFormDataEntity formDataEntity = activitiFormDataService.reject(in.getProcessInstanceId());
		((ActivitiCallbackService) LKApplicationContext.getBean(formDataEntity.getProcessCode())).reject(formDataEntity);
	}

}
