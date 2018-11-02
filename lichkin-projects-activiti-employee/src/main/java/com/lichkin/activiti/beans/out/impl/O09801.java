package com.lichkin.activiti.beans.out.impl;

import com.lichkin.framework.defines.annotations.DateToString;
import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取待办流程出参
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class O09801 {

	/** 节点名称 */
	private String taskName;

	/** 节点开始时间 */
	@DateToString(value = LKDateTimeTypeEnum.STANDARD)
	private String taskStartTime;

	/** 流程ID */
	private String processInstanceId;

	/** 流程名称 */
	private String processName;

	/** 流程类型 */
	private String processType;

	/** 流程发起人 */
	private String processStartUserName;

	/** 流程发起时间 */
	@DateToString(value = LKDateTimeTypeEnum.STANDARD)
	private String processStartTime;

}
