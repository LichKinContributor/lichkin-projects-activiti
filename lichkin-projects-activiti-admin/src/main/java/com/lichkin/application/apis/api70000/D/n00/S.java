package com.lichkin.application.apis.api70000.D.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.application.services.bus.impl.SysActivitiProcessConfigBusService;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusDeleteService;

@Service("SysActivitiProcessConfigD00Service")
public class S extends LKApiBusDeleteService<I, SysActivitiProcessConfigEntity> {

	@Autowired
	private SysActivitiProcessConfigBusService busService;


	@Override
	protected int getIdColumnResId() {
		return SysActivitiProcessConfigR.id;
	}


	@Override
	protected boolean realDelete(I sin, String locale, String compId, String loginId) {
		return true;
	}


	@Override
	protected void beforeRealDelete(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		busService.clearActivitiProcessTaskConfig(id);
	}

}
