package com.lichkin.framework.activiti.beans.in.impl;

import com.lichkin.activiti.beans.in.LKActivitiRejectProcessIn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 驳回流程入参 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LKActivitiRejectProcessIn_SingleLineProcess extends LKActivitiRejectProcessIn {

	public LKActivitiRejectProcessIn_SingleLineProcess(String processInstanceId, String userId, String comment) {
		super(processInstanceId, userId, comment);
	}

}
