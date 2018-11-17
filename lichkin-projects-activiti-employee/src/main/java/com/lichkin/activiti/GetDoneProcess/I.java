package com.lichkin.activiti.GetDoneProcess;

import javax.validation.constraints.NotNull;

import com.lichkin.framework.beans.impl.LKRequestPageBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestPageBean {

	/** 公司ID_员工ID */
	@NotNull
	private String userId;

}
