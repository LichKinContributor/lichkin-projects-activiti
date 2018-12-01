package com.lichkin.springframework.services;

import java.util.Map;

import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.framework.defines.entities.I_Login;
import com.lichkin.framework.defines.entities.I_User;
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
		I_Login login = params.getLogin();
		activitiStartProcessService.startByAdmin(entity, params.getCompId(), getProcessCode(cin, params, entity), login.getId(), ((I_User) login).getUserName(), LKJsonUtils.toJson(datas));
	}

}
