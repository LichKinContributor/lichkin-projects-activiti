package com.lichkin.application.apis.api70000.U.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysActivitiProcessConfigBusService;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusUpdateWithoutCheckerService;

@Service("SysActivitiProcessConfigU00Service")
public class S extends LKApiBusUpdateWithoutCheckerService<I, SysActivitiProcessConfigEntity> {

	@Autowired
	private SysActivitiProcessConfigBusService busService;


	@Override
	protected boolean busCheck(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		if (!LKUsingStatusEnum.LOCKED.equals(entity.getUsingStatus())) {// 待配置表单
			throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
		return true;
	}


	@Override
	protected void beforeCopyProperties(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity) {
		sin.stepCountChanged = !sin.getStepCount().equals(entity.getStepCount());
	}


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		if (sin.stepCountChanged) {
			busService.clearActivitiProcessTaskConfig(sin.getId());
		}
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		if (sin.stepCountChanged) {
			busService.addActivitiProcessTaskConfig(sin.getId(), sin.getStepCount());
		}
	}

}
