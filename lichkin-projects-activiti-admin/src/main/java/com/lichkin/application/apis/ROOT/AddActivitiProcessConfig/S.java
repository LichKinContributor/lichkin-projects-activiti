package com.lichkin.application.apis.ROOT.AddActivitiProcessConfig;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysActivitiProcessConfigBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusInsertService<I, SysActivitiProcessConfigEntity> {

	@Autowired
	private SysActivitiProcessConfigBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysActivitiProcessConfig_EXIST(30000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysActivitiProcessConfigEntity> findExist(I sin, ApiKeyValues<I> params) {
		return busService.findExist(params, sin.getProcessCode(), busService.analysisDeptId(sin.getDeptId()));
	}


	@Override
	protected boolean forceCheck(I sin, ApiKeyValues<I> params) {
		return true;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, ApiKeyValues<I> params) {
		return ErrorCodes.SysActivitiProcessConfig_EXIST;
	}


	@Override
	protected void beforeSaveMain(I sin, ApiKeyValues<I> params, SysActivitiProcessConfigEntity entity) {
		entity.setDeptId(busService.analysisDeptId(sin.getDeptId()));
		entity.setAvailable(busService.analysisAvailable());
		entity.setUsingStatus(LKUsingStatusEnum.LOCKED);
	}


	@Override
	protected void clearSubs(I sin, ApiKeyValues<I> params, SysActivitiProcessConfigEntity entity, String id) {
		busService.clearActivitiProcessTaskConfig(id);
	}


	@Override
	protected void addSubs(I sin, ApiKeyValues<I> params, SysActivitiProcessConfigEntity entity, String id) {
		busService.addActivitiProcessTaskConfig(id, sin.getStepCount());
	}

}
