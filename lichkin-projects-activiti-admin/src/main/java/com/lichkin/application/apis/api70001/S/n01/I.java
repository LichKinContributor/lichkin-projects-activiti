package com.lichkin.application.apis.api70001.S.n01;

import com.lichkin.framework.beans.impl.LKRequestIDBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestIDBean {

	/** 流程节点ID */
	private String taskId;

	/** 流程节点名称 */
	private String taskName;

	/** 员工ID */
	private String employeeId;

	/** 员工姓名 */
	private String userName;

}
