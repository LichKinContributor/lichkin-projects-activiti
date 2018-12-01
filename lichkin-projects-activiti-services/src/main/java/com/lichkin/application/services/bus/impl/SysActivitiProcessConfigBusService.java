package com.lichkin.application.services.bus.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.DeleteSQL;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysActivitiProcessConfigBusService extends LKDBService {

	public List<SysActivitiProcessConfigEntity> findExist(ApiKeyValues<?> params, String processCode, String deptId) {
		QuerySQL sql = new QuerySQL(false, SysActivitiProcessConfigEntity.class);

		addConditionId(sql, SysActivitiProcessConfigR.id, params.getId());
//		addConditionLocale(sql, SysActivitiProcessConfigR.locale, params.getLocale());
		addConditionCompId(true, sql, SysActivitiProcessConfigR.compId, params.getCompId(), params.getBusCompId());
//		addConditionUsingStatus(true, params.getCompId(), sql, SysActivitiProcessConfigR.usingStatus, params.getUsingStatus(), LKUsingStatusEnum.USING);

		if (deptId != null) {
			sql.eq(SysActivitiProcessConfigR.deptId, deptId);
		} else {
			sql.eq(SysActivitiProcessConfigR.deptId, "");
		}

		sql.eq(SysActivitiProcessConfigR.processCode, processCode);

		sql.neq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.DEPRECATED);

		return dao.getList(sql, SysActivitiProcessConfigEntity.class);
	}


	public String analysisDeptId(String deptId) {
		return StringUtils.trimToEmpty(deptId);
	}


	public Boolean analysisAvailable() {
		return Boolean.FALSE;
	}


	public void clearActivitiProcessTaskConfig(String id) {
		DeleteSQL sql = new DeleteSQL(SysActivitiProcessTaskConfigEntity.class);
		sql.eq(SysActivitiProcessTaskConfigR.configId, id);
		dao.delete(sql);
	}


	public void addActivitiProcessTaskConfig(String id, Byte stepCount) {
		List<SysActivitiProcessTaskConfigEntity> list = new ArrayList<>(stepCount);
		for (int i = 1; i <= stepCount; i++) {
			SysActivitiProcessTaskConfigEntity task = new SysActivitiProcessTaskConfigEntity();
			task.setConfigId(id);
			task.setStep((byte) i);
			task.setApprover("");
			task.setUserName("");
			task.setTaskName(i == 1 ? "发起" : "");
			task.setFormJson("[{\"plugin\":\"textbox\",\"options\":{\"name\":\"comment\",\"rows\":2}}]");
			list.add(task);
		}
		dao.persistList(list);
	}

}
