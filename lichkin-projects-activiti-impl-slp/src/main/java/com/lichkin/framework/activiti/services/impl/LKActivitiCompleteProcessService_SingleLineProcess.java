package com.lichkin.framework.activiti.services.impl;

import static com.lichkin.springframework.db.LKDBActivitiStatics.PLATFORM_TRANSACTION_MANAGER;

import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.services.LKActivitiCommonService;
import com.lichkin.activiti.services.LKActivitiCompleteProcessService;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiComplateProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.in.impl.inner.LKActivitiTaskInfoIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiCompleteProcessOut_SingleLineProcess;

/**
 * 办理流程服务类 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class LKActivitiCompleteProcessService_SingleLineProcess extends LKActivitiCommonService implements LKActivitiCompleteProcessService<LKActivitiComplateProcessIn_SingleLineProcess, LKActivitiCompleteProcessOut_SingleLineProcess> {

	@Autowired
	private TaskService taskService;


	@Override
	@Transactional(transactionManager = PLATFORM_TRANSACTION_MANAGER)
	public LKActivitiCompleteProcessOut_SingleLineProcess completeProcess(LKActivitiComplateProcessIn_SingleLineProcess in) {
		// 查询当前要办理的task
		Task task = getCurrentTask(in.getProcessInstanceId(), in.getUserId());
		// 获取流程变量的参数信息
		Map<String, Object> variables = taskService.getVariables(task.getId());
		@SuppressWarnings("unchecked")
		List<LKActivitiTaskInfoIn_SingleLineProcess> taskInfos = (List<LKActivitiTaskInfoIn_SingleLineProcess>) variables.get(KEY_TASKINFOS);
		taskInfos.remove(0);
		variables.put(KEY_TASKINFOS, taskInfos);
		// 增加审批时的备注信息
		if (StringUtils.isNotBlank(in.getComment())) {
			taskService.addComment(task.getId(), in.getProcessInstanceId(), in.getComment());
		}
		taskService.complete(task.getId(), variables);

		boolean processIsEnd = false;
		// 当前流程已经结束
		if (taskInfos.size() == 0) {
			processIsEnd = true;
		}
		return new LKActivitiCompleteProcessOut_SingleLineProcess(processIsEnd);
	}

}
