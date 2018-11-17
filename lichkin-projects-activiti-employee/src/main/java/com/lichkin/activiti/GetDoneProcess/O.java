package com.lichkin.activiti.GetDoneProcess;

import com.lichkin.framework.defines.annotations.DateToString;
import com.lichkin.framework.defines.enums.impl.LKDateTimeTypeEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class O {

	/** 节点名称 */
	private String taskName;

	/** 节点备注 */
	private String taskComment;

	/** 节点开始时间 */
	@DateToString(value = LKDateTimeTypeEnum.STANDARD)
	private String taskStartTime;

	/** 节点结束时间 */
	@DateToString(value = LKDateTimeTypeEnum.STANDARD)
	private String taskEndTime;

	/** 流程ID */
	private String processInstanceId;

	/** 流程名称 */
	private String processName;

	/** 流程类型 */
	private String processType;

	/** 流程编码 */
	private String processCode;

	/** 流程发起人 */
	private String processStartUserName;

	/** 流程发起时间 */
	@DateToString(value = LKDateTimeTypeEnum.STANDARD)
	private String processStartTime;

	/** 流程是否结束 */
	private boolean processIsEnd;

	/** 流程结束时间 */
	@DateToString(value = LKDateTimeTypeEnum.STANDARD)
	private String processEndTime;

	/** 当前流程活跃节点名称 */
	private String activeTaskName;

	/** 删除流程原因 */
	private String delReason;

}
