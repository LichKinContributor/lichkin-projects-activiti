package com.lichkin.framework.db.beans;

/**
 * 数据库资源初始化类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
class SysActivitiRInitializer implements LKRInitializer {

	/**
	 * 初始化数据库资源
	 */
	public static void init() {
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity", "T_SYS_ACTIVITI_PROCESS_CONFIG", "SysActivitiProcessConfigEntity");
		LKDBResource.addColumn("70000000", "SysActivitiProcessConfigEntity", "id");
		LKDBResource.addColumn("70000001", "SysActivitiProcessConfigEntity", "usingStatus");
		LKDBResource.addColumn("70000002", "SysActivitiProcessConfigEntity", "insertTime");
		LKDBResource.addColumn("70000003", "SysActivitiProcessConfigEntity", "compId");
		LKDBResource.addColumn("70000004", "SysActivitiProcessConfigEntity", "processKey");
		LKDBResource.addColumn("70000005", "SysActivitiProcessConfigEntity", "processType");
		LKDBResource.addColumn("70000006", "SysActivitiProcessConfigEntity", "processCode");
		LKDBResource.addColumn("70000007", "SysActivitiProcessConfigEntity", "processName");
		LKDBResource.addColumn("70000008", "SysActivitiProcessConfigEntity", "deptId");
		LKDBResource.addColumn("70000009", "SysActivitiProcessConfigEntity", "stepCount");
		LKDBResource.addColumn("70000010", "SysActivitiProcessConfigEntity", "available");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity", "T_SYS_ACTIVITI_PROCESS_TASK_CONFIG", "SysActivitiProcessTaskConfigEntity");
		LKDBResource.addColumn("70001000", "SysActivitiProcessTaskConfigEntity", "id");
		LKDBResource.addColumn("70001001", "SysActivitiProcessTaskConfigEntity", "configId");
		LKDBResource.addColumn("70001002", "SysActivitiProcessTaskConfigEntity", "taskName");
		LKDBResource.addColumn("70001003", "SysActivitiProcessTaskConfigEntity", "approver");
		LKDBResource.addColumn("70001004", "SysActivitiProcessTaskConfigEntity", "userName");
		LKDBResource.addColumn("70001005", "SysActivitiProcessTaskConfigEntity", "step");
		LKDBResource.addColumn("70001006", "SysActivitiProcessTaskConfigEntity", "formJson");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysActivitiApiRequestLogStartProcessEntity", "T_SYS_ACTIVITI_API_REQUEST_LOG_START_PROCESS", "SysActivitiApiRequestLogStartProcessEntity");
		LKDBResource.addColumn("70002000", "SysActivitiApiRequestLogStartProcessEntity", "id");
		LKDBResource.addColumn("70002001", "SysActivitiApiRequestLogStartProcessEntity", "usingStatus");
		LKDBResource.addColumn("70002002", "SysActivitiApiRequestLogStartProcessEntity", "insertTime");
		LKDBResource.addColumn("70002003", "SysActivitiApiRequestLogStartProcessEntity", "compId");
		LKDBResource.addColumn("70002004", "SysActivitiApiRequestLogStartProcessEntity", "userId");
		LKDBResource.addColumn("70002005", "SysActivitiApiRequestLogStartProcessEntity", "processConfigId");
		LKDBResource.addColumn("70002006", "SysActivitiApiRequestLogStartProcessEntity", "appKey");
		LKDBResource.addColumn("70002007", "SysActivitiApiRequestLogStartProcessEntity", "clientType");
		LKDBResource.addColumn("70002008", "SysActivitiApiRequestLogStartProcessEntity", "versionX");
		LKDBResource.addColumn("70002009", "SysActivitiApiRequestLogStartProcessEntity", "versionY");
		LKDBResource.addColumn("70002010", "SysActivitiApiRequestLogStartProcessEntity", "versionZ");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysActivitiApiRequestLogCompleteProcessEntity", "T_SYS_ACTIVITI_API_REQUEST_LOG_COMPLETE_PROCESS", "SysActivitiApiRequestLogCompleteProcessEntity");
		LKDBResource.addColumn("70003000", "SysActivitiApiRequestLogCompleteProcessEntity", "id");
		LKDBResource.addColumn("70003001", "SysActivitiApiRequestLogCompleteProcessEntity", "usingStatus");
		LKDBResource.addColumn("70003002", "SysActivitiApiRequestLogCompleteProcessEntity", "insertTime");
		LKDBResource.addColumn("70003003", "SysActivitiApiRequestLogCompleteProcessEntity", "compId");
		LKDBResource.addColumn("70003004", "SysActivitiApiRequestLogCompleteProcessEntity", "userId");
		LKDBResource.addColumn("70003005", "SysActivitiApiRequestLogCompleteProcessEntity", "processType");
		LKDBResource.addColumn("70003006", "SysActivitiApiRequestLogCompleteProcessEntity", "processInstanceId");
		LKDBResource.addColumn("70003007", "SysActivitiApiRequestLogCompleteProcessEntity", "appKey");
		LKDBResource.addColumn("70003008", "SysActivitiApiRequestLogCompleteProcessEntity", "clientType");
		LKDBResource.addColumn("70003009", "SysActivitiApiRequestLogCompleteProcessEntity", "versionX");
		LKDBResource.addColumn("70003010", "SysActivitiApiRequestLogCompleteProcessEntity", "versionY");
		LKDBResource.addColumn("70003011", "SysActivitiApiRequestLogCompleteProcessEntity", "versionZ");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity", "T_SYS_ACTIVITI_FORM_DATA", "SysActivitiFormDataEntity");
		LKDBResource.addColumn("70004000", "SysActivitiFormDataEntity", "id");
		LKDBResource.addColumn("70004001", "SysActivitiFormDataEntity", "usingStatus");
		LKDBResource.addColumn("70004002", "SysActivitiFormDataEntity", "insertTime");
		LKDBResource.addColumn("70004003", "SysActivitiFormDataEntity", "compId");
		LKDBResource.addColumn("70004004", "SysActivitiFormDataEntity", "approvalStatus");
		LKDBResource.addColumn("70004005", "SysActivitiFormDataEntity", "approvalTime");
		LKDBResource.addColumn("70004006", "SysActivitiFormDataEntity", "processConfigId");
		LKDBResource.addColumn("70004007", "SysActivitiFormDataEntity", "processInstanceId");
		LKDBResource.addColumn("70004008", "SysActivitiFormDataEntity", "approverType");
		LKDBResource.addColumn("70004009", "SysActivitiFormDataEntity", "approverLoginId");
		LKDBResource.addColumn("70004010", "SysActivitiFormDataEntity", "processCode");
		LKDBResource.addColumn("70004011", "SysActivitiFormDataEntity", "field1");
		LKDBResource.addColumn("70004012", "SysActivitiFormDataEntity", "field2");
		LKDBResource.addColumn("70004013", "SysActivitiFormDataEntity", "field3");
		LKDBResource.addColumn("70004014", "SysActivitiFormDataEntity", "field4");
		LKDBResource.addColumn("70004015", "SysActivitiFormDataEntity", "field5");
		LKDBResource.addColumn("70004016", "SysActivitiFormDataEntity", "field6");
		LKDBResource.addColumn("70004017", "SysActivitiFormDataEntity", "field7");
		LKDBResource.addColumn("70004018", "SysActivitiFormDataEntity", "field8");
		LKDBResource.addColumn("70004019", "SysActivitiFormDataEntity", "field9");
		LKDBResource.addTable("com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity", "T_SYS_ACTIVITI_FORM_DATA_STEP", "SysActivitiFormDataStepEntity");
		LKDBResource.addColumn("70005000", "SysActivitiFormDataStepEntity", "id");
		LKDBResource.addColumn("70005001", "SysActivitiFormDataStepEntity", "usingStatus");
		LKDBResource.addColumn("70005002", "SysActivitiFormDataStepEntity", "insertTime");
		LKDBResource.addColumn("70005003", "SysActivitiFormDataStepEntity", "formDataId");
		LKDBResource.addColumn("70005004", "SysActivitiFormDataStepEntity", "taskName");
		LKDBResource.addColumn("70005005", "SysActivitiFormDataStepEntity", "step");
		LKDBResource.addColumn("70005006", "SysActivitiFormDataStepEntity", "formJson");
		LKDBResource.addColumn("70005007", "SysActivitiFormDataStepEntity", "dataJson");
	}

}