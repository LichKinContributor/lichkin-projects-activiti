package com.lichkin.activiti.services.impl;

import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.in.impl.I09804;
import com.lichkin.activiti.beans.out.impl.O09804;
import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

/**
 * 获取表单列表
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09804 extends LKApiBusGetPageService<I09804, O09804, SysActivitiFormDataEntity> {

	@Override
	protected void initSQL(I09804 sin, String locale, String compId, String loginId, QuerySQL sql) {
		sql.select(SysActivitiFormDataR.id);
		sql.select(SysActivitiFormDataR.processInstanceId);
		sql.select(SysActivitiProcessConfigR.processType);
		sql.select(SysActivitiFormDataR.approvalStatus);
		sql.select(SysActivitiFormDataR.insertTime);

		sql.innerJoin(SysActivitiProcessConfigEntity.class, new Condition(SysActivitiFormDataR.processConfigId, SysActivitiProcessConfigR.id));

		LKDictUtils4Activiti.processCode(sql, SysActivitiFormDataR.processCode, 0);

		sql.eq(SysActivitiFormDataR.compId, compId);
		sql.eq(SysActivitiFormDataR.approverLoginId, loginId);
		sql.neq(SysActivitiFormDataR.usingStatus, LKUsingStatusEnum.DEPRECATED);
		// 只查询启动流程成功的表单
		sql.isNotNull(SysActivitiFormDataR.processInstanceId);

		sql.addOrders(new Order(SysActivitiFormDataR.insertTime, false));
	}

}
