package com.lichkin.framework.activiti.beans.in.impl;

import java.util.List;

import com.lichkin.activiti.beans.in.LKActivitiStartProcessIn;
import com.lichkin.framework.activiti.beans.in.impl.inner.LKActivitiTaskInfoIn_SingleLineProcess;
import com.lichkin.framework.defines.enums.impl.ProcessKeyEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 启动流程入参 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class LKActivitiStartProcessIn_SingleLineProcess extends LKActivitiStartProcessIn {

	public LKActivitiStartProcessIn_SingleLineProcess(String processConfigId, ProcessKeyEnum processKey, String businessKey, String processName, ProcessTypeEnum processType, String comment) {
		super(processConfigId, processKey, businessKey, processName, processType, comment);
	}


	/** 流程节点信息 */
	private List<LKActivitiTaskInfoIn_SingleLineProcess> taskList;

}
