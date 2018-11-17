package com.lichkin.activiti.GetProcessTaskForm;

import javax.validation.constraints.NotBlank;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 流程配置ID */
	@NotBlank
	private String processConfigId;

	/** 流程节点所属步数序号（从1开始） */
	private Byte step;

}
