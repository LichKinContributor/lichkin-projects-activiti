package com.lichkin.application.apis.ROOT.GetActivitiProcessTaskConfigList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusGetListService<I, O, SysActivitiProcessTaskConfigEntity> {

	@Override
	protected void initSQL(I sin, ApiKeyValues<I> params, QuerySQL sql) {
		// 主表
		sql.select(SysActivitiProcessTaskConfigR.id);
		sql.select(SysActivitiProcessTaskConfigR.formJson);

		// 筛选条件（业务项）
		String configId = sin.getConfigId();
		if (StringUtils.isNotBlank(configId)) {
			sql.eq(SysActivitiProcessTaskConfigR.configId, configId);
		}

		// 排序条件
		sql.addOrders(new Order(SysActivitiProcessTaskConfigR.step));
	}

}
