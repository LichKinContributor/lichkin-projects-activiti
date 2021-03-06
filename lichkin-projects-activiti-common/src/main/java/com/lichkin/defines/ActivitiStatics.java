package com.lichkin.defines;

/**
 * 常量定义
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface ActivitiStatics {

	/** 审批流地址 */
	public static final String ACTIVITI_CENTER_URL = "/activiti";

	/** 启动流程 */
	public static final String START_PROCESS = "StartProcess";

	/** 流程配置表主键 */
	public static final String KEY_PROCESS_CONFIG_ID = "processConfigId";

	/** 流程主键 */
	public static final String KEY_PROCESS_KEY = "processKey";

	/** 流程名称 */
	public static final String KEY_PROCESS_NAME = "processName";

	/** 流程类型 */
	public static final String KEY_PROCESS_TYPE = "processType";

	/** 流程步骤 */
	public static final String KEY_TASKINFOS = "taskInfos";

	/** 流程步骤JSON信息 */
	public static final String KEY_TASKINFOS_JSON = "taskInfosJson";

	/** 流程发起人 */
	public static final String KEY_START_USER_NAME = "startUserName";

}
