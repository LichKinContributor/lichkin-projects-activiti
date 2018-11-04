package com.lichkin.application.services.impl;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class ActivitiFormDataService extends LKDBService {

	public void updateActivitiFormData(String processInstanceId, ApprovalStatusEnum approvalStatus) {
		QuerySQL sql = new QuerySQL(false, SysActivitiFormDataEntity.class);
		sql.eq(SysActivitiFormDataR.processInstanceId, processInstanceId);
		SysActivitiFormDataEntity formDataEntity = dao.getOne(sql, SysActivitiFormDataEntity.class);
		formDataEntity.setApprovalStatus(approvalStatus);
		formDataEntity.setApprovalTime(LKDateTimeUtils.now());
		dao.mergeOne(formDataEntity);
	}

}
