package com.lichkin.activiti.SubmitForm;

import javax.validation.constraints.NotBlank;

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

	/** 流程编码（字典） */
	private String processCode;

	/** 流程配置ID（SysActivitiProcessConfigEntity.id） */
	private String processConfigId;

}
