package com.lichkin.application.utils;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.defines.LKFrameworkStatics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 字典工具类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LKDictUtils4Activiti extends LKDictUtils {

	/**
	 * 连接字典表（流程标识）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void processKey(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "processKey", LKFrameworkStatics.LichKin, "ACTIVITI_PROCESS_KEY", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（流程类型）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void processType(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "processType", LKFrameworkStatics.LichKin, "ACTIVITI_PROCESS_TYPE", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（流程编码）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void processCode(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "processCode", LKFrameworkStatics.LichKin, "ACTIVITI_PROCESS_CODE", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（审批状态）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void approvalStatus(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "approvalStatus", LKFrameworkStatics.LichKin, "ACTIVITI_APPROVAL_STATUS", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（发起人类型）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void approverType(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "approverType", LKFrameworkStatics.LichKin, "ACTIVITI_APPROVER_TYPE", columnResId, tableIdx);
	}


	/**
	 * 连接字典表（在用状态）
	 * @param sql SQL语句对象
	 * @param columnResId 列资源ID
	 * @param tableIdx 字典表序号（从0开始）
	 */
	public static void usingStatus4activiti(QuerySQL sql, int columnResId, int tableIdx) {
		leftJoinDictionary(sql, "usingStatus", LKFrameworkStatics.LichKin, "ACTIVITI_USING_STATUS", columnResId, tableIdx);
	}

}
