package com.lichkin.activiti.beans.out.impl;

import java.util.Date;

import lombok.Data;

@Data
public class LKActivitiGetFinishProcessOut {

	/** 流程ID */
	private final String processInstanceId;

	/** 流程发起时间 */
	private Date startTime;

	/** 流程结束时间 */
	private Date endTime;

	/** 删除流程原因 */
	private String deleteReason;

}
