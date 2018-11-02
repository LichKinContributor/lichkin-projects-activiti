package com.lichkin.framework.activiti.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.activiti.services.LKActivitiCommonService;
import com.lichkin.activiti.services.LKActivitiGetDetailProcessService;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiGetDetailProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.in.impl.inner.LKActivitiTaskInfoIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiGetDetailProcessOut_SingleLineProcess;
import com.lichkin.framework.json.LKJsonUtils;

/**
 * 获取流程详情服务类 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class LKActivitiGetDetailProcessService_SingleLineProcess extends LKActivitiCommonService implements LKActivitiGetDetailProcessService<LKActivitiGetDetailProcessIn_SingleLineProcess, LKActivitiGetDetailProcessOut_SingleLineProcess> {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;


	/**
	 * 获取流程详情
	 * @param in 入参
	 * @return 流程实例
	 */
	@Override
	public List<LKActivitiGetDetailProcessOut_SingleLineProcess> getDetailProcess(LKActivitiGetDetailProcessIn_SingleLineProcess in) {
		// 流程所有节点列表（包含未审批的）
		List<LKActivitiGetDetailProcessOut_SingleLineProcess> taskAllList = new ArrayList<>();

		// 从His中获取processInstance
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(in.getProcessInstanceId()).singleResult();
		if (processInstance != null) {
			String variableNames = "'" + KEY_PROCESS_NAME + "','" + KEY_TASKINFOS_JSON + "','" + KEY_PROCESS_CONFIG_ID + "'";
			List<HistoricVariableInstance> variableList = getHisVariableInfoByNames(in.getProcessInstanceId(), variableNames);

			String processName = "";
			String taskInfoJson = "";
			String processConfigId = "";
			for (HistoricVariableInstance variable : variableList) {
				if (variable.getVariableName().equals(KEY_PROCESS_NAME)) {
					processName = String.valueOf(variable.getValue());
				}
				if (variable.getVariableName().equals(KEY_TASKINFOS_JSON)) {
					taskInfoJson = String.valueOf(variable.getValue());
				}
				if (variable.getVariableName().equals(KEY_PROCESS_CONFIG_ID)) {
					processConfigId = String.valueOf(variable.getValue());
				}
			}

			List<LKActivitiTaskInfoIn_SingleLineProcess> taskInfos = LKJsonUtils.toList(taskInfoJson, LKActivitiTaskInfoIn_SingleLineProcess.class);

			// 根据ProcessDefinitionId查询BpmnModel 获取所有的流程节点
			BpmnModel model = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
			if (model != null) {
				int step = taskInfos.size();
				Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
				int i = 0;
				for (FlowElement e : flowElements) {
					// 只获取UserTask的节点
					if ((e instanceof UserTask) && (i < step)) {
						LKActivitiTaskInfoIn_SingleLineProcess taskInfo = taskInfos.get(i);
						LKActivitiGetDetailProcessOut_SingleLineProcess taskAll = new LKActivitiGetDetailProcessOut_SingleLineProcess(in.getProcessInstanceId());
						taskAll.setTaskId(e.getId());
						taskAll.setTaskName(taskInfo.getTaskName());
						taskAll.setApproveUserName(taskInfo.getUserName());
						taskAll.setProcessConfigId(processConfigId);
						taskAll.setBusinessKey(processInstance.getBusinessKey());
						taskAll.setProcessName(processName);
						taskAll.setProcessStartUserName(taskInfos.get(0).getUserName());
						taskAll.setProcessStartTime(processInstance.getStartTime());
						// 流程结束时才有endTime
						if (processInstance.getEndTime() != null) {
							taskAll.setProcessIsEnd(true);
							taskAll.setProcessEndTime(processInstance.getEndTime());
						}
						taskAllList.add(taskAll);
						i++;
					}
				}
			}

			getApproveHistory(in.getProcessInstanceId(), taskAllList);
		}
		return taskAllList;
	}


	private void getApproveHistory(String processInstanceId, List<LKActivitiGetDetailProcessOut_SingleLineProcess> taskAllList) {
		// 该流程实例的所有节点审批记录
		List<HistoricTaskInstance> hisTaskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByHistoricTaskInstanceStartTime().asc().list();
		for (HistoricTaskInstance taskInstance : hisTaskList) {
			for (LKActivitiGetDetailProcessOut_SingleLineProcess taskAll : taskAllList) {
				if (taskAll.getTaskId().equals(taskInstance.getTaskDefinitionKey())) {
					taskAll.setTaskName(taskInstance.getName());
					taskAll.setTaskStartTime(taskInstance.getStartTime());
					// 流程节点结束时间(未审批时此值为空)
					taskAll.setTaskEndTime(taskInstance.getEndTime());
					taskAll.setDeleteReason(taskInstance.getDeleteReason());
					break;
				}
			}

		}
	}

}
