package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09800;
import com.lichkin.activiti.beans.out.impl.O09800;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKException;
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
		List<O09800> processList = new ArrayList<>();

		// 查询部门特有的工作流
		QuerySQL sql = new QuerySQL(false, SysActivitiProcessConfigEntity.class);
		sql.eq(SysActivitiProcessConfigR.compId, compId);
		sql.eq(SysActivitiProcessConfigR.deptId, sin.getDeptId());
		sql.eq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.USING);
		sql.eq(SysActivitiProcessConfigR.available, Boolean.TRUE);
		List<SysActivitiProcessConfigEntity> deptProList = dao.getList(sql, SysActivitiProcessConfigEntity.class);
		StringBuffer processCodeBuf = new StringBuffer();
		if (CollectionUtils.isNotEmpty(deptProList)) {
			for (int i = 0; i < deptProList.size(); i++) {
				SysActivitiProcessConfigEntity processConfig = deptProList.get(i);
				if (i < (deptProList.size() - 1)) {
					processCodeBuf.append(processConfig.getProcessCode() + LKFrameworkStatics.SPLITOR);
				} else {
					processCodeBuf.append(processConfig.getProcessCode());
				}
				O09800 out = new O09800();
				out.setProcessConfigId(processConfig.getId());
				out.setProcessName(processConfig.getProcessName());
				out.setProcessCode(processConfig.getProcessCode());
				processList.add(out);
			}
		}

		if (StringUtils.isNotBlank(sin.getDeptId())) {
			// 查询公司的工作流（去除和部门重复的工作流信息）
			sql = new QuerySQL(false, SysActivitiProcessConfigEntity.class);
			sql.eq(SysActivitiProcessConfigR.compId, compId);
			sql.eq(SysActivitiProcessConfigR.deptId, "");
			sql.notIn(SysActivitiProcessConfigR.processCode, processCodeBuf.toString());
			sql.eq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.USING);
			sql.eq(SysActivitiProcessConfigR.available, Boolean.TRUE);
			List<SysActivitiProcessConfigEntity> compProList = dao.getList(sql, SysActivitiProcessConfigEntity.class);
			for (int i = 0; i < compProList.size(); i++) {
				O09800 out = new O09800();
				out.setProcessConfigId(compProList.get(i).getId());
				out.setProcessName(compProList.get(i).getProcessName());
				out.setProcessCode(compProList.get(i).getProcessCode());
				processList.add(out);
			}
		}

		return processList;
	}

}
