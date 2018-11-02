package com.lichkin.activiti.beans.out.impl;

import java.util.Date;

import lombok.Data;

@Data
public class LKActivitiGetPendingProcessOut {

	/** 流程ID */
	private final String processInstanceId;

	/** 流程类型 */
	private String processType;

	/** 流程名称 */
	private String processName;

	/** 流程发起人 */
	private String processStartUserName;

	/** 流程发起时间 */
	private Date processStartTime;

	/** 节点名称 */
	private String taskName;

	/** 节点开始时间 */
	private Date taskStartTime;

}
