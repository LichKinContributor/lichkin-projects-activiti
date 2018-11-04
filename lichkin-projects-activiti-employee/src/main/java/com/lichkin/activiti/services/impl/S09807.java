package com.lichkin.activiti.services.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.in.impl.I09807;
import com.lichkin.activiti.beans.out.impl.O09807;
import com.lichkin.application.services.impl.SysActivitiStartProcessService;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiApiRequestLogStartProcessEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiService;

/**
 * 启动流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09807 extends SysActivitiStartProcessService implements LKApiService<I09807, O09807> {

	@Override
	@Transactional
	public O09807 handle(I09807 sin, String locale, String compId, String loginId) throws LKException {
		String userId = sin.getUserId();
		String processConfigId = sin.getProcessConfigId();

		// 记录请求日志
		SysActivitiApiRequestLogStartProcessEntity log = LKBeanUtils.newInstance(false, sin.getDatas(), SysActivitiApiRequestLogStartProcessEntity.class);
		log.setUserId(userId);
		log.setProcessConfigId(processConfigId);
		dao.persistOne(log);

		// 启动流程
		return new O09807(startProcess(userId, sin.getUserName(), sin.getFormDataId(), dao.findOneById(SysActivitiProcessConfigEntity.class, processConfigId)));
	}

}
