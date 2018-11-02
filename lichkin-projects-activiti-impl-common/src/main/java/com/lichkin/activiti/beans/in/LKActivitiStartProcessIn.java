package com.lichkin.activiti.beans.in;

import com.lichkin.framework.defines.enums.impl.ProcessKeyEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;

import lombok.Data;

@Data
public class LKActivitiStartProcessIn {

	/** 流程配置主键 */
	protected final String processConfigId;

	/** 流程主键(对应activiti中设置的key) */
	protected final ProcessKeyEnum processKey;

	/** 业务key */
	private final String businessKey;

	/** 流程名称 */
	protected final String processName;

	/** 流程类型 */
	protected final ProcessTypeEnum processType;

	/** 备注 */
	protected final String comment;

}
