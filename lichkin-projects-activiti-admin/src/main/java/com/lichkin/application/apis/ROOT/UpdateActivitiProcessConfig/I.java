package com.lichkin.application.apis.ROOT.UpdateActivitiProcessConfig;

import com.lichkin.framework.beans.impl.LKRequestIDBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

	private String processName;

	private Byte stepCount;

}
