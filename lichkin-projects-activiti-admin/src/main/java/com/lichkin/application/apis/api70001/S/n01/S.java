package com.lichkin.application.apis.api70001.S.n01;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiVoidService;
import com.lichkin.springframework.services.LKDBService;

@Service("SysActivitiProcessTaskConfigS01Service")
public class S extends LKDBService implements LKApiVoidService<I> {

	@Transactional
	@Override
	public void handle(I sin, String locale, String compId, String loginId) throws LKException {
		SysActivitiProcessConfigEntity config = dao.findOneById(SysActivitiProcessConfigEntity.class, sin.getId());
		config.setAvailable(true);
		dao.mergeOne(config);

		String[] taskIds = sin.getTaskId().split(LKFrameworkStatics.SPLITOR);
		String[] taskNames = sin.getTaskName().split(LKFrameworkStatics.SPLITOR);
		String[] approveres = sin.getEmployeeId().split(LKFrameworkStatics.SPLITOR);
		String[] userNames = sin.getUserName().split(LKFrameworkStatics.SPLITOR);

		for (int i = 0; i < taskIds.length; i++) {
			SysActivitiProcessTaskConfigEntity task = dao.findOneById(SysActivitiProcessTaskConfigEntity.class, taskIds[i]);
			task.setTaskName(taskNames[i]);
			task.setApprover(approveres[i]);
			task.setUserName(userNames[i]);
			dao.mergeOne(task);
		}
	}

}
