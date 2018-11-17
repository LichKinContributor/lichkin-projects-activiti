package com.lichkin.activiti.GetProcessTaskForm;

import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

@Service(Statics.SERVICE_NAME)
public class S extends LKDBService implements LKApiService<I, O> {

	@Override
	public O handle(I sin, String locale, String compId, String loginId) throws LKException {
		QuerySQL sqlObj = new QuerySQL(SysActivitiProcessTaskConfigEntity.class);
		sqlObj.select(

				SysActivitiProcessTaskConfigR.formJson,

				SysActivitiProcessTaskConfigR.approver,

				SysActivitiProcessTaskConfigR.userName

		);

		sqlObj.eq(SysActivitiProcessTaskConfigR.configId, sin.getProcessConfigId());
		sqlObj.eq(SysActivitiProcessTaskConfigR.step, sin.getStep());

		return dao.getOne(sqlObj, O.class);
	}

}
