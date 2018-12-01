package com.lichkin.application.apis.ROOT.GetActivitiProcessConfigPage;

import com.lichkin.framework.beans.impl.LKRequestPageBean;
import com.lichkin.framework.defines.enums.LKPlatform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean {

	private LKPlatform platformType;

	private String processName;

	/** 公司名称 */
	private String compName;

}
