package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.LKActivitiTaskInfo;
import com.lichkin.activiti.beans.in.impl.LKActivitiGetDoneProcessIn;
import com.lichkin.activiti.beans.out.impl.LKActivitiGetDoneProcessOut;
import com.lichkin.activiti.services.LKActivitiCommonService;
import com.lichkin.framework.utils.LKBeanUtils;

/**
 * 获取已办流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class LKActivitiGetDoneProcessService extends LKActivitiCommonService {

	public List<LKActivitiGetDoneProcessOut> getDoneProcess(LKActivitiGetDoneProcessIn in) {
		List<HistoricTaskInstance> hisTaskList = historyService.createHistoricTaskInstanceQuery().taskAssignee(in.getUserId()).finished().orderByTaskCreateTime().desc().listPage(in.getFirstResult(), in.getMaxResults());

		List<LKActivitiTaskInfo> taskInfoList = new ArrayList<>();
		Set<String> idsSet = new HashSet<>();
		for (HistoricTaskInstance hisTask : hisTaskList) {
			idsSet.add(hisTask.getProcessInstanceId());

			LKActivitiTaskInfo taskInfo = new LKActivitiTaskInfo();
			taskInfo.setProcessInstanceId(hisTask.getProcessInstanceId());
			taskInfo.setTaskName(hisTask.getName());
			taskInfo.setTaskStartTime(hisTask.getStartTime());
			taskInfo.setTaskEndTime(hisTask.getEndTime());
			if (hisTask.getTaskDefinitionKey().equals("step1")) {
				continue;
			}
			taskInfoList.add(taskInfo);
		}
		setProcessInfo(taskInfoList, idsSet);

		List<LKActivitiGetDoneProcessOut> outList = new ArrayList<>();
		for (LKActivitiTaskInfo taskInfo : taskInfoList) {
			outList.add(LKBeanUtils.copyProperties(false, taskInfo, new LKActivitiGetDoneProcessOut(taskInfo.getProcessInstanceId())));
		}
		return outList;
	}

}
