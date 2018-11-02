package com.lichkin.application.apis.api70000.I.n00;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysActivitiProcessConfigBusService;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusInsertService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service("SysActivitiProcessConfigI00Service")
public class S extends LKApiBusInsertService<I, SysActivitiProcessConfigEntity> {

	@Autowired
	private SysActivitiProcessConfigBusService busService;


	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		SysActivitiProcessConfig_EXIST(140000),

		;

		private final Integer code;

	}


	@Override
	protected List<SysActivitiProcessConfigEntity> findExist(I sin, String locale, String compId, String loginId) {
		return busService.findExist(null, compId, sin.getCompId(), sin.getProcessCode(), busService.analysisDeptId(sin.getDeptId()));
	}


	@Override
	protected boolean forceCheck(I sin, String locale, String compId, String loginId) {
		return false;
	}


	@Override
	protected LKCodeEnum existErrorCode(I sin, String locale, String compId, String loginId) {
		return ErrorCodes.SysActivitiProcessConfig_EXIST;
	}


	@Override
	protected void beforeRestore(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, SysActivitiProcessConfigEntity exist) {
		entity.setUsingStatus(LKUsingStatusEnum.USING);
		entity.setCompId(exist.getCompId());
		entity.setDeptId(exist.getDeptId());
		entity.setAvailable(exist.getAvailable());
	}


	@Override
	protected void beforeAddNew(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity) {
		entity.setCompId(getCompId(compId, sin.getCompId()));
		entity.setDeptId(busService.analysisDeptId(sin.getDeptId()));
		entity.setAvailable(busService.analysisAvailable());
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity) {
		entity.setUsingStatus(LKUsingStatusEnum.LOCKED);
	}


	@Override
	protected void clearSubs(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		busService.clearActivitiProcessTaskConfig(id);
	}


	@Override
	protected void addSubs(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		busService.addActivitiProcessTaskConfig(id, sin.getStepCount());
	}

}
