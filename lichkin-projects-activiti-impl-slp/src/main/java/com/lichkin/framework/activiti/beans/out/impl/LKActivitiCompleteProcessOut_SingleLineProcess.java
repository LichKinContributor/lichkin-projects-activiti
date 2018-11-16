package com.lichkin.framework.activiti.beans.out.impl;

import com.lichkin.activiti.beans.out.LKActivitiCompleteProcessOut;

/**
 * 办理流程出参 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class LKActivitiCompleteProcessOut_SingleLineProcess extends LKActivitiCompleteProcessOut {

	public LKActivitiCompleteProcessOut_SingleLineProcess(boolean processIsEnd, byte surplusStep) {
		super(processIsEnd, surplusStep);
	}

}
