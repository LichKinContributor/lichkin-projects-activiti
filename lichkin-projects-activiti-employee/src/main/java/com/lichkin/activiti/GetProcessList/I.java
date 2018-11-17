package com.lichkin.activiti.GetProcessList;

import javax.validation.constraints.NotBlank;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.enums.LKPlatform;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	/** 部门ID */
	@NotBlank
	private String deptId;

	/** 流程类型 */
	private LKPlatform platformType;

}
