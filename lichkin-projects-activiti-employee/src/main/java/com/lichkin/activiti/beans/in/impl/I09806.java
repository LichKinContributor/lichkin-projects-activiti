package com.lichkin.activiti.beans.in.impl;

import javax.validation.constraints.NotBlank;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取流程节点表单
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class I09806 extends LKRequestBean {

	/** 流程配置ID */
	@NotBlank
	private String processConfigId;

	/** 流程节点所属步数序号（从1开始） */
	private Byte step;

}
