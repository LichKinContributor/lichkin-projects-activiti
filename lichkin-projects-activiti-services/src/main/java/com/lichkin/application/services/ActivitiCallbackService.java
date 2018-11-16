package com.lichkin.application.services;

import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;

/**
 * 流程处理回调方法接口定义
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface ActivitiCallbackService {

	/**
	 * Activit审批通过回调方法
	 * @param formDataEntity 流程数据表实体类对象
	 * @param step 第几步
	 */
	void approve(SysActivitiFormDataEntity formDataEntity, byte step);


	/**
	 * Activit审批结束回调方法
	 * @param formDataEntity 流程数据表实体类对象
	 */
	void finish(SysActivitiFormDataEntity formDataEntity);


	/**
	 * Activit审批驳回回调方法
	 * @param processEntity 业务主表实体类对象（Activit回调时为null，业务回调时设置值）
	 * @param formDataEntity 流程数据表实体类对象（Activit回调时设置值，业务回调时为null）
	 */
	void reject(SysActivitiFormDataEntity formDataEntity);

}
