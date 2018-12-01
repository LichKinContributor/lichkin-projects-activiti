package com.lichkin.springframework.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.springframework.configs.LKApplicationContext;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

/**
 * 发起流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
abstract class ApiBusStartProcessService<CI extends LKRequestIDBean, E extends ActivitiProcessEntity> extends LKApiBusUpdateWithoutCheckerService<CI, E> {

	@Autowired
	ActivitiStartProcessService activitiStartProcessService;


	/**
	 * 发起流程需要传递的参数
	 * @param cin 控制器类入参
	 * @param params 解析值参数
	 * @param entity 保存前的实体类对象
	 * @param datas 传递的参数（直接在此参数中设置）
	 */
	protected abstract void initFormData(CI cin, ApiKeyValues<CI> params, E entity, Map<String, Object> datas);


	/**
	 * 获取流程编码
	 * @param cin 控制器类入参
	 * @param params 解析值参数
	 * @param entity 保存前的实体类对象
	 * @return 流程编码
	 */
	protected abstract String getProcessCode(CI cin, ApiKeyValues<CI> params, E entity);


	@Deprecated
	@Override
	protected boolean busCheck(CI cin, ApiKeyValues<CI> params, E entity, String id) {
		return true;
	}


	@Deprecated
	@Override
	protected void beforeCopyProperties(CI cin, ApiKeyValues<CI> params, E entity) {
	}


	@Deprecated
	@Override
	protected void beforeSaveMain(CI cin, ApiKeyValues<CI> params, E entity) {
		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
		// 初始化表单数据
		initFormData(cin, params, entity, datas);
		// 发起流程
		startProcess(cin, params, entity, datas);
	}


	abstract void startProcess(CI cin, ApiKeyValues<CI> params, E entity, Map<String, Object> datas);


	@SuppressWarnings("unchecked")
	@Deprecated
	@Override
	protected void afterSaveMain(CI cin, ApiKeyValues<CI> params, E entity, String id) {
		if (ApprovalStatusEnum.APPROVED.equals(entity.getApprovalStatus())) {
			((WithoutActivitiCallbackService<E>) LKApplicationContext.getBean(getProcessCode(cin, params, entity))).directFinish(entity, params.getCompId(), params.getLoginId());
		}
	}


	@Deprecated
	@Override
	protected void clearSubs(CI cin, ApiKeyValues<CI> params, E entity, String id) {
	}


	@Deprecated
	@Override
	protected void addSubs(CI cin, ApiKeyValues<CI> params, E entity, String id) {
	}

}
