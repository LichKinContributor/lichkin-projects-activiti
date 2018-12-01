package com.lichkin.activiti.StartProcess;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lichkin.application.services.impl.SysActivitiStartProcessService;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiApiRequestLogStartProcessEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiService;

@Service(Statics.SERVICE_NAME)
public class S extends SysActivitiStartProcessService implements LKApiService<I, O> {

	@Override
	@Transactional
	public O handle(I sin, ApiKeyValues<I> params) throws LKException {
		String userId = params.getCompId() + "_" + params.getUser().getId();
		String processConfigId = sin.getProcessConfigId();

		// 记录请求日志
		SysActivitiApiRequestLogStartProcessEntity log = LKBeanUtils.newInstance(false, sin.getDatas(), SysActivitiApiRequestLogStartProcessEntity.class);
		log.setUserId(userId);
		log.setProcessConfigId(processConfigId);
		dao.persistOne(log);

		// 启动流程
		return new O(startProcess(userId, params.getUser().getUserName(), sin.getFormDataId(), dao.findOneById(SysActivitiProcessConfigEntity.class, processConfigId)));
	}

}
