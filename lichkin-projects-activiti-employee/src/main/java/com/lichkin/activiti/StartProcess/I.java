package com.lichkin.activiti.StartProcess;

import javax.validation.constraints.NotNull;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 流程配置ID（SysActivitiProcessConfigEntity.id） */
	@NotNull
	private String processConfigId;

	/** 表单ID */
	@NotNull
	private String formDataId;

}
