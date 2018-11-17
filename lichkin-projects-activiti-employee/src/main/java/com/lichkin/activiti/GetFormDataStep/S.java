package com.lichkin.activiti.GetFormDataStep;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataStepR;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusGetListService<I, O, SysActivitiFormDataStepEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		sql.select(SysActivitiFormDataStepR.taskName);
		sql.select(SysActivitiFormDataStepR.step);
		sql.select(SysActivitiFormDataStepR.formJson);
		sql.select(SysActivitiFormDataStepR.dataJson);

		sql.eq(SysActivitiFormDataStepR.formDataId, sin.getId());

		sql.addOrders(new Order(SysActivitiFormDataStepR.step));
	}

}
