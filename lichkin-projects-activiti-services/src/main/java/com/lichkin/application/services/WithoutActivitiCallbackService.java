package com.lichkin.application.services;

import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

/**
 * 流程处理回调方法接口定义（当没有对应流程配置时，审批直接结束。）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface WithoutActivitiCallbackService<E extends ActivitiProcessEntity> {

	/**
	 * 业务直接审批结束回调方法
	 * @param processEntity 流程表实体类对象
	 * @param compId 公司ID
	 * @param loginId 登录ID
	 */
	void directFinish(E processEntity, String compId, String loginId);

}
