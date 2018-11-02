package com.lichkin.activiti.services.impl;

import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.in.impl.I09805;
import com.lichkin.activiti.beans.out.impl.O09805;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataStepR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

/**
 * 获取单个表单
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09805 extends LKApiBusGetListService<I09805, O09805, SysActivitiFormDataStepEntity> {

	@Override
	protected void initSQL(I09805 sin, String locale, String compId, String loginId, QuerySQL sql) {
		sql.select(SysActivitiFormDataStepR.taskName);
		sql.select(SysActivitiFormDataStepR.step);
		sql.select(SysActivitiFormDataStepR.formJson);
		sql.select(SysActivitiFormDataStepR.dataJson);

		sql.eq(SysActivitiFormDataStepR.formDataId, sin.getId());

		sql.addOrders(new Order(SysActivitiFormDataStepR.step));
	}

}
