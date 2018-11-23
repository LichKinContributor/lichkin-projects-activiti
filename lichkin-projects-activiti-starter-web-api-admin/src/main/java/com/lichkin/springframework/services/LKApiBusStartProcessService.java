package com.lichkin.springframework.services;

import java.util.Map;

import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.framework.defines.entities.I_User;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

/**
 * 发起流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKApiBusStartProcessService<SI extends LKRequestIDBean, E extends ActivitiProcessEntity> extends ApiBusStartProcessService<SI, E> {

	@Override
	void startProcess(SI sin, String locale, String compId, String loginId, E entity, Map<String, Object> datas) {
		activitiStartProcessService.startByAdmin(entity, compId, getProcessCode(sin, locale, compId, loginId, entity), loginId, ((I_User) sin.getDatas().getLogin()).getUserName(), LKJsonUtils.toJson(datas));
	}

}
