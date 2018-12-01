package com.lichkin.application.apis.ROOT.ReconfigActivitiProcessConfig;

import com.lichkin.framework.beans.impl.LKRequestIDsBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDsBean {

	/** 流程节点表单JSON */
	private String formJson;

}
