package com.lichkin.application.apis.api70000.P.n00;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.application.utils.LKDictUtils4Activiti;
import com.lichkin.framework.db.beans.Condition;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.db.beans.SysCompR;
import com.lichkin.framework.db.beans.SysDeptR;
import com.lichkin.framework.db.enums.LikeType;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.enums.impl.PlatformTypeEnum;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysCompEntity;
import com.lichkin.springframework.entities.impl.SysDeptEntity;
import com.lichkin.springframework.services.LKApiBusGetPageService;

@Service("SysActivitiProcessConfigP00Service")
public class S extends LKApiBusGetPageService<I, O, SysActivitiProcessConfigEntity> {

	@Override
	protected void initSQL(I sin, String locale, String compId, String loginId, QuerySQL sql) {
		// 主表
		sql.select(SysActivitiProcessConfigR.id);
		sql.select(SysActivitiProcessConfigR.insertTime);
		sql.select(SysActivitiProcessConfigR.processName);

		// 关联表
		sql.innerJoin(SysCompEntity.class, new Condition(SysCompR.id, SysActivitiProcessConfigR.compId));
		sql.leftJoin(SysDeptEntity.class, new Condition(SysDeptR.id, SysActivitiProcessConfigR.deptId));
		sql.select(SysCompR.compName);
		sql.select(SysDeptR.deptName);

		// 字典表
		int i = 0;
		LKDictUtils4Activiti.usingStatus4activiti(sql, SysActivitiProcessConfigR.usingStatus, i++);
		LKDictUtils4Activiti.processKey(sql, SysActivitiProcessConfigR.processKey, i++);
		LKDictUtils4Activiti.processType(sql, SysActivitiProcessConfigR.processType, i++);
		LKDictUtils4Activiti.processCode(sql, SysActivitiProcessConfigR.processCode, i++);
		LKDictUtils4Activiti.platformType(sql, SysActivitiProcessConfigR.platformType, i++);

		// 筛选条件（必填项）
		// 公司ID
		if (LKFrameworkStatics.LichKin.equals(compId)) {
			if (StringUtils.isNotBlank(sin.getCompId())) {
				sql.eq(SysActivitiProcessConfigR.compId, sin.getCompId());
			}
		} else {
			sql.eq(SysActivitiProcessConfigR.compId, compId);
		}
		// 在用状态
		LKUsingStatusEnum usingStatus = sin.getUsingStatus();
		if (usingStatus == null) {
			if (LKFrameworkStatics.LichKin.equals(compId)) {
				sql.neq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.DEPRECATED);
			} else {
				sql.eq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.USING);
			}
		} else {
			sql.eq(SysActivitiProcessConfigR.usingStatus, usingStatus);
		}

		// 筛选条件（业务项）
		String processName = sin.getProcessName();
		if (StringUtils.isNotBlank(processName)) {
			sql.like(SysActivitiProcessConfigR.processName, LikeType.ALL, processName);
		}

		String compName = sin.getCompName();
		if (StringUtils.isNotBlank(compName)) {
			sql.like(SysCompR.compName, LikeType.ALL, compName);
		}

		// 平台类型
		PlatformTypeEnum platformType = sin.getPlatformType();
		if (platformType != null) {
			sql.eq(SysActivitiProcessConfigR.platformType, platformType);
		}

		// 排序条件
		sql.addOrders(new Order(SysActivitiProcessConfigR.id, false));
	}

}
