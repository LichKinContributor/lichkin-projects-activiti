package com.lichkin.activiti.beans.in.impl;

import javax.validation.constraints.NotBlank;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 保存表单入参
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class I09808 extends LKRequestBean {

	/** 主表ID（SysActivitiFormData） */
	private String formDataId;

	/** 第几步。 */
	private int step;

	/** 表单数据json。 */
	@NotBlank
	private String dataJson;

	/** 表单类型（字典）。 */
	private String formTypeCode;

	/** 流程配置ID。 */
	private String processConfigId;

}
