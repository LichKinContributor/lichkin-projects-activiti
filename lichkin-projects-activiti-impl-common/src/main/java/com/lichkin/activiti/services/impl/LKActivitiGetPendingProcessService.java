package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.LKActivitiTaskInfo;
import com.lichkin.activiti.beans.in.impl.LKActivitiGetPendingProcessIn;
import com.lichkin.activiti.beans.out.impl.LKActivitiGetPendingProcessOut;
import com.lichkin.activiti.services.LKActivitiCommonService;
import com.lichkin.framework.utils.LKBeanUtils;

/**
 * 获取待办流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class LKActivitiGetPendingProcessService extends LKActivitiCommonService {

	public List<LKActivitiGetPendingProcessOut> getPendingProcess(LKActivitiGetPendingProcessIn in) {
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(in.getUserId()).active().orderByTaskCreateTime().asc().list();
		Set<String> idsSet = new HashSet<>();

		List<LKActivitiTaskInfo> taskInfoList = new ArrayList<>();
		for (Task task : taskList) {
			idsSet.add(task.getProcessInstanceId());
			LKActivitiTaskInfo taskInfo = new LKActivitiTaskInfo();
			taskInfo.setProcessInstanceId(task.getProcessInstanceId());
			taskInfo.setTaskName(task.getName());
			taskInfo.setTaskStartTime(task.getCreateTime());
			taskInfoList.add(taskInfo);
		}
		setProcessInfo(taskInfoList, idsSet);

		List<LKActivitiGetPendingProcessOut> outList = new ArrayList<>();
		for (LKActivitiTaskInfo taskInfo : taskInfoList) {
			LKActivitiGetPendingProcessOut out = new LKActivitiGetPendingProcessOut(taskInfo.getProcessInstanceId());
			outList.add(LKBeanUtils.copyProperties(taskInfo, out));
		}
		return outList;
	}

}
