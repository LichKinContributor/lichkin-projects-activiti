package com.lichkin.activiti.beans.in.impl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取流程详情入参
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class I09803 extends LKRequestBean {

	/** 流程类型 */
	@NotNull
	private String processType;

	/** 流程ID */
	@Pattern(regexp = "[1-9][0-9]+")
	@NotNull
	private String processInstanceId;

}
