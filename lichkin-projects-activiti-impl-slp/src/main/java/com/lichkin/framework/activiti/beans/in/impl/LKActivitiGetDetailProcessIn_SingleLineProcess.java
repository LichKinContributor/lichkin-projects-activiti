package com.lichkin.framework.activiti.beans.in.impl;

import com.lichkin.activiti.beans.in.LKActivitiGetDetailProcessIn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取流程详情入参 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class LKActivitiGetDetailProcessIn_SingleLineProcess extends LKActivitiGetDetailProcessIn {

	public LKActivitiGetDetailProcessIn_SingleLineProcess(String processInstanceId) {
		super(processInstanceId);
	}

}
