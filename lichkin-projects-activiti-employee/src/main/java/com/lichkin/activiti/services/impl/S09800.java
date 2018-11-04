package com.lichkin.activiti.services.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09800;
import com.lichkin.activiti.beans.out.impl.O09800;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKListUtils;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

/**
 * 获取流程列表
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09800 extends LKDBService implements LKApiService<I09800, List<O09800>> {

	@Override
	@Transactional
	public List<O09800> handle(I09800 sin, String locale, String compId, String loginId) throws LKException {
		// 查询部门特有的工作流
		List<O09800> listDept = getAvailableActivitiProcessConfigId(compId, sin.getDeptId());
		// 查询公司通用的工作流
		List<O09800> listComp = getAvailableActivitiProcessConfigId(compId, null);
		// 返回以部门特有的工作流为主，去重补充公司通用的工作流。
		return LKListUtils.distinct(listDept, (o1, o2) -> o1.getProcessCode().compareTo(o2.getProcessCode()), listComp);
	}


	private List<O09800> getAvailableActivitiProcessConfigId(String compId, String deptId) {
		QuerySQL sql = new QuerySQL(SysActivitiProcessConfigEntity.class);

		sql.select(SysActivitiProcessConfigR.id, "processConfigId");
		sql.select(SysActivitiProcessConfigR.processName);
		sql.select(SysActivitiProcessConfigR.processCode);

		sql.eq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.USING);
		sql.eq(SysActivitiProcessConfigR.available, Boolean.TRUE);
		sql.eq(SysActivitiProcessConfigR.compId, compId);
		sql.eq(SysActivitiProcessConfigR.deptId, StringUtils.trimToEmpty(deptId));
		return dao.getList(sql, O09800.class);
	}

}
