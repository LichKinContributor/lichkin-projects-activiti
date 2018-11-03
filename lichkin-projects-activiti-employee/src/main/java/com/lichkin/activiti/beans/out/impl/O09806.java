package com.lichkin.activiti.beans.out.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取流程节点表单
 * @author SuZhou LichKin Information Technology Co.; Ltd.
 */
@Getter
@Setter
@ToString
public class O09806 {

	/** 任务节点表单json */
	private String formJson;

	/** 公司ID_员工登录ID */
	private String userId;

	/** 用户姓名 */
	private String userName;
}
