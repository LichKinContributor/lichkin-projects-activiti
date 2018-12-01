package com.lichkin.springframework.controllers;

import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;
import com.lichkin.springframework.services.LKApiBusStartProcessService;

/**
 * 发起流程控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKApiBusStartProcessController<CI extends LKRequestIDBean, E extends ActivitiProcessEntity> extends LKApiBusUpdateController<CI, E> {

	@Override
	protected abstract LKApiBusStartProcessService<CI, E> getService(CI cin, ApiKeyValues<CI> params);


	@Deprecated
	@Override
	protected void beforeInvokeService(CI cin, ApiKeyValues<CI> params) throws LKException {
	}


	@Deprecated
	@Override
	protected void afterInvokeService(CI cin, ApiKeyValues<CI> params) throws LKException {
	}

}
