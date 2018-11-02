package com.lichkin.activiti.services;

import com.lichkin.activiti.beans.in.LKActivitiCompleteProcessIn;
import com.lichkin.activiti.beans.out.LKActivitiCompleteProcessOut;

/**
 * 办理流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKActivitiCompleteProcessService<CompleteProcessIn extends LKActivitiCompleteProcessIn, CompleteProcessOut extends LKActivitiCompleteProcessOut> {

	/**
	 * 办理流程
	 * @param in 入参
	 * @return 流程实例
	 */
	public CompleteProcessOut completeProcess(CompleteProcessIn in);

}
