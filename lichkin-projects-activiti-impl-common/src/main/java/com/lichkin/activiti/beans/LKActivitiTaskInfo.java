package com.lichkin.activiti.beans;

import java.util.Date;

import lombok.Data;

@Data
public class LKActivitiTaskInfo {

	/** 流程ID */
	private String processInstanceId;

	/** 流程类型 */
	private String processType;

	/** 流程名称 */
	private String processName;

	/** 流程发起人 */
	private String processStartUserName;

	/** 流程发起时间 */
	private Date processStartTime;

	/** 流程是否结束 */
	private boolean processIsEnd = false;

	/** 流程结束时间 */
	private Date processEndTime;

	/** 节点名称 */
	private String taskName;

	/** 节点名称 */
	private String taskComment;

	/** 节点开始时间 */
	private Date taskStartTime;

	/** 节点结束时间 */
	private Date taskEndTime;

	/** 当前流程活跃节点名称 */
	private String activeTaskName;

	/** 删除流程原因 */
	private String delReason;

}
