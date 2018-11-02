package com.lichkin.activiti.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lichkin.activiti.beans.LKActivitiTaskInfo;
import com.lichkin.defines.ActivitiStatics;

/**
 * 流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKActivitiCommonService implements ActivitiStatics {

	@Autowired
	protected TaskService taskService;

	@Autowired
	protected HistoryService historyService;

	@Autowired
	protected ManagementService managementService;


	protected Task getCurrentTask(String processInstanceId, String userId) {
		return taskService.createTaskQuery().processInstanceId(processInstanceId).taskCandidateOrAssigned(userId).singleResult();
	}


	protected List<HistoricVariableInstance> getHisVariableInfoByNames(String processInstanceIds, String variableNames) {
		return historyService.createNativeHistoricVariableInstanceQuery().sql("SELECT * FROM " + managementService.getTableName(HistoricVariableInstance.class) + " T WHERE T.NAME_ in (" + variableNames + ") and T.PROC_INST_ID_ in (" + processInstanceIds + ") ").list();
	}


	protected void setProcessInfo(List<LKActivitiTaskInfo> taskInfoList, Set<String> processInstanceIdsSet) {
		if (CollectionUtils.isNotEmpty(taskInfoList)) {
			List<HistoricProcessInstance> procList = historyService.createHistoricProcessInstanceQuery().processInstanceIds(processInstanceIdsSet).list();

			String processInstanceIds = StringUtils.join(processInstanceIdsSet, ",");
			String variableNames = "'" + KEY_PROCESS_TYPE + "','" + KEY_PROCESS_NAME + "','" + KEY_START_USER_NAME + "'";
			List<HistoricVariableInstance> variableList = getHisVariableInfoByNames(processInstanceIds, variableNames);

			// 查询每个流程当前活跃的任务节点
			List<Task> currentTasks = taskService.createTaskQuery().processInstanceIdIn(new ArrayList<>(processInstanceIdsSet)).active().list();

			// 设置流程相关信息
			for (LKActivitiTaskInfo taskInfo : taskInfoList) {
				// 设置流程开始时间
				for (HistoricProcessInstance proc : procList) {
					if (taskInfo.getProcessInstanceId().equals(proc.getId())) {
						taskInfo.setDelReason(proc.getDeleteReason());
						taskInfo.setProcessStartTime(proc.getStartTime());
						if (proc.getEndTime() != null) {
							taskInfo.setProcessIsEnd(true);
							taskInfo.setProcessEndTime(proc.getEndTime());
						}
						break;
					}
				}
				// 设置流程当前活跃的节点
				for (Task task : currentTasks) {
					if (taskInfo.getProcessInstanceId().equals(task.getProcessInstanceId())) {
						taskInfo.setActiveTaskName(task.getName());
						break;
					}
				}
				int setNumber = 0;
				for (HistoricVariableInstance variable : variableList) {
					if (taskInfo.getProcessInstanceId().equals(variable.getProcessInstanceId())) {
						// 设置流程类型
						if (variable.getVariableName().equals(KEY_PROCESS_TYPE)) {
							taskInfo.setProcessType(String.valueOf(variable.getValue()));
							setNumber++;
						}
						// 设置流程名
						if (variable.getVariableName().equals(KEY_PROCESS_NAME)) {
							taskInfo.setProcessName(String.valueOf(variable.getValue()));
							setNumber++;
						}
						// 设置流程发起人姓名
						if (variable.getVariableName().equals(KEY_START_USER_NAME)) {
							taskInfo.setProcessStartUserName(String.valueOf(variable.getValue()));
							setNumber++;
						}
						if (setNumber == 3) {
							break;
						}

					}
				}
			}
		}
	}

}
