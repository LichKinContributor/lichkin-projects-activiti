package com.lichkin.activiti.beans.in.impl;

import javax.validation.constraints.NotNull;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

/**
 * 启动流程入参
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class I09807 extends LKRequestBean {

	/** 公司ID_员工ID */
	@NotNull
	private String userId;

	/** 发起人名称 */
	@NotNull
	private String userName;

	/** 流程配置ID（SysActivitiProcessConfigEntity.id） */
	@NotNull
	private String processConfigId;

	/** 表单ID */
	@NotNull
	private String formDataId;

}
