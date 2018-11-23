package com.lichkin.activiti.GetProcessList;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.enums.LKPlatform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 流程类型 */
	private LKPlatform platformType;

}
