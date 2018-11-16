package com.lichkin.activiti.beans.out;

import lombok.Data;

@Data
public class LKActivitiCompleteProcessOut {

	/** 流程是否结束 */
	private final boolean processIsEnd;

	/** 剩余节点步骤 */
	private final Byte surplusStep;

}
