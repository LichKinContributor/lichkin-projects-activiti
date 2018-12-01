package com.lichkin.activiti.GetProcessList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.enums.LKPlatform;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKListUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

@Service(Statics.SERVICE_NAME)
public class S extends LKDBService implements LKApiService<I, List<O>> {

	@Override
	@Transactional
	public List<O> handle(I sin, ApiKeyValues<I> params) throws LKException {
		// 查询部门特有的工作流
		List<O> listDept = getAvailableActivitiProcessConfigId(params.getCompId(), params.getDept().getId(), sin.getPlatformType());
		// 查询公司通用的工作流
		List<O> listComp = getAvailableActivitiProcessConfigId(params.getCompId(), null, sin.getPlatformType());
		// 返回以部门特有的工作流为主，去重补充公司通用的工作流。
		return LKListUtils.distinct(listDept, (o1, o2) -> o1.getProcessCode().compareTo(o2.getProcessCode()), listComp);
	}


	private List<O> getAvailableActivitiProcessConfigId(String compId, String deptId, LKPlatform platformType) {
		QuerySQL sql = new QuerySQL(SysActivitiProcessConfigEntity.class);

		sql.select(SysActivitiProcessConfigR.id, "processConfigId");
		sql.select(SysActivitiProcessConfigR.processName);
		sql.select(SysActivitiProcessConfigR.processCode);

		sql.eq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.USING);
		sql.eq(SysActivitiProcessConfigR.available, Boolean.TRUE);
		sql.eq(SysActivitiProcessConfigR.compId, compId);
		sql.eq(SysActivitiProcessConfigR.deptId, StringUtils.trimToEmpty(deptId));
		sql.eq(SysActivitiProcessConfigR.platformType, platformType);
		return dao.getList(sql, O.class);
	}

}
