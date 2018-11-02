package com.lichkin.application.apis.api70000.US.n01;

import com.lichkin.framework.beans.impl.LKRequestIDsUsingStatusBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDsUsingStatusBean {

	/** 流程节点配置ID */
	private String taskId;

	/** 流程节点表单JSON */
	private String formJson;

}
