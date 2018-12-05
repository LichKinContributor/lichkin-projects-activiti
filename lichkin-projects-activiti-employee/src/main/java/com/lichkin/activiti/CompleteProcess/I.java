package com.lichkin.activiti.CompleteProcess;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 表单数据表ID（SysActivitiFormDataEntity.id） */
	private String formDataId;

	/** 节点步骤 */
	private int step;

	/** 数据JSON */
	@NotBlank
	private String dataJson;

	/** 流程类型 */
	@NotNull
	private String processType;

	/** 流程ID */
	@Pattern(regexp = "[1-9][0-9]+")
	@NotNull
	private String processInstanceId;

}
