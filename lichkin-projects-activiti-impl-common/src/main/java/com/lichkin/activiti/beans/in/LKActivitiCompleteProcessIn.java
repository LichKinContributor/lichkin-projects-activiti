package com.lichkin.activiti.beans.in;

import lombok.Data;

@Data
public class LKActivitiCompleteProcessIn {

	/** 流程ID */
	protected final String processInstanceId;

	/** 审批人ID */
	protected final String userId;

	/** 审批备注信息 */
	protected final String comment;

}
