package com.lichkin.activiti.beans.in.impl;

import javax.validation.constraints.NotBlank;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.enums.impl.PlatformTypeEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取流程列表
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString(callSuper = true)
public class I09800 extends LKRequestBean {

	/** 部门ID */
	@NotBlank
	private String deptId;

	/** 流程类型 */
	private PlatformTypeEnum platformType;

}
