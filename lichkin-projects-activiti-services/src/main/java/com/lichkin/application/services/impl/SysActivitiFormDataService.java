package com.lichkin.application.services.impl;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.db.beans.SysActivitiFormDataStepR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysActivitiFormDataService extends LKDBService {

	private SysActivitiFormDataEntity update(SysActivitiFormDataEntity formDataEntity, ApprovalStatusEnum approvalStatus) {
		formDataEntity.setApprovalStatus(approvalStatus);
		formDataEntity.setApprovalTime(LKDateTimeUtils.now());
		return dao.mergeOne(formDataEntity);
	}


	public SysActivitiFormDataEntity getByProcessInstanceId(String processInstanceId) {
		QuerySQL sql = new QuerySQL(false, SysActivitiFormDataEntity.class);
		sql.eq(SysActivitiFormDataR.processInstanceId, processInstanceId);
		return dao.getOne(sql, SysActivitiFormDataEntity.class);
	}


	public SysActivitiFormDataStepEntity getFormDataStep(String formDataId, int step) {
		QuerySQL sql = new QuerySQL(SysActivitiFormDataStepEntity.class);
		sql.eq(SysActivitiFormDataStepR.formDataId, formDataId);
		sql.eq(SysActivitiFormDataStepR.step, step);
		return dao.getOne(sql, SysActivitiFormDataStepEntity.class);
	}


	public SysActivitiFormDataEntity finish(SysActivitiFormDataEntity formDataEntity) {
		return update(formDataEntity, ApprovalStatusEnum.APPROVED);
	}


	public SysActivitiFormDataEntity finish(String processInstanceId) {
		return update(getByProcessInstanceId(processInstanceId), ApprovalStatusEnum.APPROVED);
	}


	public SysActivitiFormDataEntity reject(SysActivitiFormDataEntity formDataEntity) {
		return update(formDataEntity, ApprovalStatusEnum.REJECT);
	}


	public SysActivitiFormDataEntity reject(String processInstanceId) {
		return update(getByProcessInstanceId(processInstanceId), ApprovalStatusEnum.REJECT);
	}

}
