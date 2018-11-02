package com.lichkin.framework.activiti.beans.out.impl;

import com.lichkin.activiti.beans.out.LKActivitiStartProcessOut;

/**
 * 启动流程出参 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKActivitiStartProcessOut_SingleLineProcess extends LKActivitiStartProcessOut {

	/**
	 * 构造方法
	 * @param processInstanceId 流程ID
	 */
	public LKActivitiStartProcessOut_SingleLineProcess(String processInstanceId) {
		super(processInstanceId);
	}

}
