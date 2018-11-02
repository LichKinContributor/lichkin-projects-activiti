package com.lichkin.activiti.services;

import com.lichkin.activiti.beans.in.LKActivitiRejectProcessIn;
import com.lichkin.activiti.beans.out.LKActivitiRejectProcessOut;

/**
 * 驳回流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKActivitiRejectProcessService<RejectProcessIn extends LKActivitiRejectProcessIn, RejectProcessOut extends LKActivitiRejectProcessOut> {

	/**
	 * 办理流程
	 * @param in 入参
	 * @return 流程实例
	 */
	public RejectProcessOut RejectProcess(RejectProcessIn in);

}
