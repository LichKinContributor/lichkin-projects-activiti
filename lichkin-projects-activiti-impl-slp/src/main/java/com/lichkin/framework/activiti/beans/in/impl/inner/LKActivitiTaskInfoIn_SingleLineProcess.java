package com.lichkin.framework.activiti.beans.in.impl.inner;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 节点信息（单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
public class LKActivitiTaskInfoIn_SingleLineProcess implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 0L;

	/** 公司ID_员工ID */
	private String userId;

	/** 节点操作人员Name */
	private String userName;

	/** 节点名称 */
	private String taskName;
}
