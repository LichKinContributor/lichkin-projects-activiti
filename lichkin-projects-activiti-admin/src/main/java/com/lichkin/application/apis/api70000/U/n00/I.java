package com.lichkin.application.apis.api70000.U.n00;

import javax.validation.constraints.Null;

import com.lichkin.framework.beans.impl.LKRequestIDBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

	private String processName;

	private Byte stepCount;

	/** 步骤是否变化 */
	@Null
	public Boolean stepCountChanged;

}
