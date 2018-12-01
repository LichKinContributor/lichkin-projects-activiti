package com.lichkin.springframework.services;

import java.util.Map;

import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

/**
 * 发起流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKApiBusStartProcessService<CI extends LKRequestIDBean, E extends ActivitiProcessEntity> extends ApiBusStartProcessService<CI, E> {

	@Override
	void startProcess(CI cin, ApiKeyValues<CI> params, E entity, Map<String, Object> datas) {
		activitiStartProcessService.startByEmployee(entity, params.getCompId(), params.getDept().getId(), getProcessCode(cin, params, entity), params.getUser().getId(), params.getUser().getUserName(), LKJsonUtils.toJson(datas));
	}

}
