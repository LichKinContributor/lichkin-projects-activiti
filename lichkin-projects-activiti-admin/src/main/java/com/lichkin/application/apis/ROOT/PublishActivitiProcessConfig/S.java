package com.lichkin.application.apis.ROOT.PublishActivitiProcessConfig;

import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusUpdateUsingStatusService<LKRequestIDsBean, SysActivitiProcessConfigEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysActivitiProcessConfigR.id;
	}


	@Override
	protected void beforeSaveMain(LKRequestIDsBean sin, ApiKeyValues<LKRequestIDsBean> params, SysActivitiProcessConfigEntity entity, String id) {
		switch (entity.getUsingStatus()) {
			case STAND_BY:
			// 待发布到已发布
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
