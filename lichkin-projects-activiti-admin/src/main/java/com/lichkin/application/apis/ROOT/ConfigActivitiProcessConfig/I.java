package com.lichkin.application.apis.ROOT.ConfigActivitiProcessConfig;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDsBean {

	/** 流程节点配置ID */
	private String taskId;

	/** 流程节点表单JSON */
	private String formJson;

}
