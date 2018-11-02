package com.lichkin.activiti.beans.in.impl;

import lombok.Data;

@Data
public class LKActivitiGetPendingProcessIn {

	/** 用户ID */
	protected final String userId;

	/** 起始行数 */
	private int firstResult;

	/** 结果行数 */
	private int maxResults;

}
