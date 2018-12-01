package com.lichkin.activiti.GetFormPage;

import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusGetPageService<I, O, SysActivitiFormDataEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysActivitiFormDataR.id);
		sql.select(SysActivitiFormDataR.processInstanceId);
		sql.select(SysActivitiProcessConfigR.processType);
		sql.select(SysActivitiFormDataR.approvalStatus);
		sql.select(SysActivitiFormDataR.insertTime);

		// 关联表
		sql.innerJoin(SysActivitiProcessConfigEntity.class, new Condition(SysActivitiFormDataR.processConfigId, SysActivitiProcessConfigR.id));

		// 字典表
		int i = 0;
		LKDictUtils4Activiti.processCode(sql, SysActivitiFormDataR.processCode, i++);

		// 筛选条件（必填项）
//		addConditionId(sql, SysActivitiFormDataR.id, params.getId());
//		addConditionLocale(sql, SysActivitiFormDataR.locale, params.getLocale());
		addConditionCompId(true, sql, SysActivitiFormDataR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysActivitiFormDataR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		sql.eq(SysActivitiFormDataR.approverLoginId, params.getLoginId());
		sql.isNotNull(SysActivitiFormDataR.processInstanceId);

		// 排序条件
		sql.addOrders(new Order(SysActivitiFormDataR.insertTime, false));
	}

}
