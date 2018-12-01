package com.lichkin.application.apis.ROOT.UpdateActivitiProcessConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysActivitiProcessConfigBusService;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysActivitiProcessConfigEntity> {

	@Autowired
	private SysActivitiProcessConfigBusService busService;


	@Override
	protected boolean busCheck(I sin, ApiKeyValues<I> params, SysActivitiProcessConfigEntity entity, String id) {
		if (!LKUsingStatusEnum.LOCKED.equals(entity.getUsingStatus())) {// 待配置表单
			throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
		return true;
	}


	@Override
	protected void beforeCopyProperties(I sin, ApiKeyValues<I> params, SysActivitiProcessConfigEntity entity) {
		params.putObject("stepCountChanged", !sin.getStepCount().equals(entity.getStepCount()));
	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysActivitiProcessConfigEntity entity, String id) {
		if ((boolean) params.getObject("stepCountChanged")) {
			busService.clearActivitiProcessTaskConfig(sin.getId());
		}
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysActivitiProcessConfigEntity entity, String id) {
		if ((boolean) params.getObject("stepCountChanged")) {
			busService.addActivitiProcessTaskConfig(sin.getId(), sin.getStepCount());
		}
	}

}
