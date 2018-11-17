package com.lichkin.activiti.GetPendingProcess;

import javax.validation.constraints.NotNull;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 公司ID_员工ID */
	@NotNull
	private String userId;

}
