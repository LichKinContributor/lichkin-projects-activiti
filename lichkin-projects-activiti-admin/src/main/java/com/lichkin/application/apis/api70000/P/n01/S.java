package com.lichkin.application.apis.api70000.P.n01;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.db.beans.SysDeptR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysDeptEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysActivitiProcessConfigP01Service")
public class S extends LKApiBusGetPageService<I, O, SysActivitiProcessConfigEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysActivitiProcessConfigR.id);
//		sql.select(SysActivitiProcessConfigR.insertTime);
//		sql.select(SysActivitiProcessConfigR.usingStatus);// LKUsingStatusEnum
//		sql.select(SysActivitiProcessConfigR.available);
//		sql.select(SysActivitiProcessConfigR.stepCount);
//		sql.select(SysActivitiProcessConfigR.deptId);
		sql.select(SysActivitiProcessConfigR.processName);
//		sql.select(SysActivitiProcessConfigR.processCode);
//		sql.select(SysActivitiProcessConfigR.processType);// ProcessTypeEnum
//		sql.select(SysActivitiProcessConfigR.processKey);// ProcessKeyEnum

		// 关联表
		sql.leftJoin(SysDeptEntity.class, new Condition(SysDeptR.id, SysActivitiProcessConfigR.deptId));
		sql.select(SysDeptR.deptName);

		// 字典表

		// 筛选条件（必填项）
		sql.eq(SysActivitiProcessConfigR.compId, compId);
		sql.eq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.USING);

		// 筛选条件（业务项）
		String processName = sin.getProcessName();
		if (StringUtils.isNotBlank(processName)) {
			sql.like(SysActivitiProcessConfigR.processName, LikeType.ALL, processName);
		}

		// 排序条件
		sql.addOrders(new Order(SysActivitiProcessConfigR.id, false));
	}

}
