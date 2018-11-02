package com.lichkin.activiti.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09810;
import com.lichkin.activiti.beans.out.impl.O09810;
import com.lichkin.activiti.services.impl.S09810;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 办理流程节点控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/CompleteProcess")
@LKApiType(apiType = ApiType.PERSONAL_BUSINESS)
public class C09810 extends LKApiYYController<I09810, O09810, I09810, O09810> {

	@Autowired
	private S09810 service;


	@Override
	protected LKApiService<I09810, O09810> getService(I09810 cin) {
		return service;
	}


	@Override
	protected I09810 beforeInvokeService(I09810 cin) throws LKException {
		return cin;
	}


	@Override
	protected O09810 afterInvokeService(I09810 cin, I09810 sin, O09810 sout) throws LKException {
		return sout;
	}

}
