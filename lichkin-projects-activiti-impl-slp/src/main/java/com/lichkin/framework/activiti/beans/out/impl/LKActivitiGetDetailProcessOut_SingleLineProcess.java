package com.lichkin.framework.activiti.beans.out.impl;

import java.util.Date;

import com.lichkin.activiti.beans.out.LKActivitiGetDetailProcessOut;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取流程详情出参 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class LKActivitiGetDetailProcessOut_SingleLineProcess extends LKActivitiGetDetailProcessOut {

	public LKActivitiGetDetailProcessOut_SingleLineProcess(String processInstanceId) {
		super(processInstanceId);
	}


	/** 业务key */
	private String businessKey;

	/** 流程配置ID */
	private String processConfigId;

	/** 流程名称 */
	private String processName;

	/** 流程发起人 */
	private String processStartUserName;

	/** 流程发起时间 */
	private Date processStartTime;

	/** 流程是否结束 */
	private boolean processIsEnd = false;

	/** 流程结束时间 */
	private Date processEndTime;

	/** 节点Id */
	private String taskId;

	/** 节点名称 */
	private String taskName;

	/** 审批人 */
	private String approveUserName;

	/** 备注 */
	private String taskComment;

	/** 节点开始时间 */
	private Date taskStartTime;

	/** 节点结束时间 */
	private Date taskEndTime;

	/** 流程删除原因 */
	private String deleteReason;

}
