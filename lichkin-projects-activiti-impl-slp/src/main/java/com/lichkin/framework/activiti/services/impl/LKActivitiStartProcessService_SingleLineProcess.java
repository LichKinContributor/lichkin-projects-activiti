package com.lichkin.framework.activiti.services.impl;

import static com.lichkin.springframework.db.LKDBActivitiStatics.PLATFORM_TRANSACTION_MANAGER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.services.LKActivitiCommonService;
import com.lichkin.activiti.services.LKActivitiStartProcessService;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiStartProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.in.impl.inner.LKActivitiTaskInfoIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiStartProcessOut_SingleLineProcess;
import com.lichkin.framework.json.LKJsonUtils;

/**
 * 启动流程服务类 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class LKActivitiStartProcessService_SingleLineProcess extends LKActivitiCommonService implements LKActivitiStartProcessService<LKActivitiStartProcessIn_SingleLineProcess, LKActivitiStartProcessOut_SingleLineProcess> {

	@Autowired
	private RuntimeService runtimeService;


	/**
	 * 启动流程
	 * @param in 入参
	 * @return 流程实例
	 */
	@Override
	@Transactional(transactionManager = PLATFORM_TRANSACTION_MANAGER)
	public LKActivitiStartProcessOut_SingleLineProcess startProcess(LKActivitiStartProcessIn_SingleLineProcess in) {
		Map<String, Object> variables = new HashMap<>();
		// 设置通用流程变量
		variables.put(KEY_PROCESS_CONFIG_ID, in.getProcessConfigId());
		variables.put(KEY_PROCESS_KEY, in.getProcessKey());
		variables.put(KEY_PROCESS_NAME, in.getProcessName());
		variables.put(KEY_PROCESS_TYPE, in.getProcessType().toString());

		// 设置其它流程变量
		List<LKActivitiTaskInfoIn_SingleLineProcess> taskInfos = in.getTaskList();
		//
		variables.put(KEY_TASKINFOS, taskInfos);
		// 根据传的节点数量自动创建节点数
		variables.put(KEY_TASKINFOS_JSON, LKJsonUtils.toJson(taskInfos));
		// 设置流程发起人
		variables.put(KEY_START_USER_NAME, taskInfos.get(0).getUserName());

		// 启动流程
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(in.getProcessKey().toString(), in.getBusinessKey(), variables);

		// 完成第一步处理
		completeProcess(instance.getProcessInstanceId(), taskInfos.get(0).getUserId());

		// 初始化返回值
		LKActivitiStartProcessOut_SingleLineProcess out = new LKActivitiStartProcessOut_SingleLineProcess(instance.getProcessInstanceId());

		// 返回结果
		return out;
	}


	private void completeProcess(String processInstanceId, String userId) {
		// 查询当前要办理的task
		Task task = getCurrentTask(processInstanceId, userId);
		// 获取流程变量的参数信息
		Map<String, Object> variables = taskService.getVariables(task.getId());
		@SuppressWarnings("unchecked")
		List<LKActivitiTaskInfoIn_SingleLineProcess> taskInfos = (List<LKActivitiTaskInfoIn_SingleLineProcess>) variables.get(KEY_TASKINFOS);
		taskInfos.remove(0);
		variables.put(KEY_TASKINFOS, taskInfos);
		taskService.complete(task.getId(), variables);
	}

}
