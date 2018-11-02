package com.lichkin.activiti.services.impl;

import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDsUsingStatusBean;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

/**
 * 修改表单状态
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09809 extends LKApiBusUpdateUsingStatusService<LKRequestIDsUsingStatusBean, SysActivitiFormDataEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysActivitiFormDataR.id;
	}

}
