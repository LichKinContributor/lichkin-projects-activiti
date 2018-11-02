package com.lichkin.activiti.services.impl;

import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.in.impl.I09806;
import com.lichkin.activiti.beans.out.impl.O09806;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

/**
 * 获取流程节点表单
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09806 extends LKDBService implements LKApiService<I09806, O09806> {

	@Override
	public O09806 handle(I09806 sin, String locale, String compId, String loginId) throws LKException {
		QuerySQL sqlObj = new QuerySQL(SysActivitiProcessTaskConfigEntity.class);
		sqlObj.select(

				SysActivitiProcessTaskConfigR.formJson,

				SysActivitiProcessTaskConfigR.approver,

				SysActivitiProcessTaskConfigR.userName

		);

		sqlObj.eq(SysActivitiProcessTaskConfigR.configId, sin.getProcessConfigId());
		sqlObj.eq(SysActivitiProcessTaskConfigR.step, sin.getStep());

		return dao.getOne(sqlObj, O09806.class);
	}

}
