package com.lichkin.activiti.UpdateForm;

import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDsUsingStatusBean;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusUpdateUsingStatusService<LKRequestIDsUsingStatusBean, SysActivitiFormDataEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysActivitiFormDataR.id;
	}

}
