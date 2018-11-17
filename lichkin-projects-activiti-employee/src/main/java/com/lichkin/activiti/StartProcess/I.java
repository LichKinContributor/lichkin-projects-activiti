package com.lichkin.activiti.StartProcess;

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
