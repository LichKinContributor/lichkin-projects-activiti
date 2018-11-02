package com.lichkin.application.apis.api70000.US.n00;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysActivitiProcessConfigUS00Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysActivitiProcessConfigEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysActivitiProcessConfigR.id;
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		if (LKUsingStatusEnum.STAND_BY.equals(entity.getUsingStatus()) && LKUsingStatusEnum.USING.equals(sin.getUsingStatus())) {// 待发布到已发布
			return;
		}
		throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
	}

}
