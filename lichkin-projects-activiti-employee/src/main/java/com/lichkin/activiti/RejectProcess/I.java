package com.lichkin.activiti.RejectProcess;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 公司ID_员工ID */
	@NotNull
	private String userId;

	/** 流程类型 */
	@NotNull
	private String processType;

	/** 流程ID */
	@Pattern(regexp = "[1-9][0-9]+")
	@NotNull
	private String processInstanceId;

	/** 审批备注信息 */
	private String comment;

}
