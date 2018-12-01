package com.lichkin.activiti.GetFormDataStep;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataStepR;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusGetListService<I, O, SysActivitiFormDataStepEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysActivitiFormDataStepR.taskName);
		sql.select(SysActivitiFormDataStepR.step);
		sql.select(SysActivitiFormDataStepR.formJson);
		sql.select(SysActivitiFormDataStepR.dataJson);

		// 关联表

		// 字典表
//		int i = 0;

		// 筛选条件（必填项）
//		addConditionId(sql, SysActivitiFormDataStepR.id, params.getId());
//		addConditionLocale(sql, SysActivitiFormDataStepR.locale, params.getLocale());
//		addConditionCompId(true, sql, SysActivitiFormDataStepR.compId, params.getCompId(), params.getBusCompId());
		addConditionUsingStatus(true, params.getCompId(), sql, SysActivitiFormDataStepR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.STAND_BY, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		sql.eq(SysActivitiFormDataStepR.formDataId, sin.getId());

		// 排序条件
		sql.addOrders(new Order(SysActivitiFormDataStepR.step));
	}

}
