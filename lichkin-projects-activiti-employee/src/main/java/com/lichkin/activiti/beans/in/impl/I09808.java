package com.lichkin.activiti.beans.in.impl;

import javax.validation.constraints.NotBlank;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

/**
 * 保存表单入参
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class I09808 extends LKRequestBean {

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
