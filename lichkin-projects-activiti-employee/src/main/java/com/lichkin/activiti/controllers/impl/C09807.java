package com.lichkin.activiti.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09807;
import com.lichkin.activiti.beans.out.impl.O09807;
import com.lichkin.activiti.services.impl.S09807;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 启动流程控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/StartProcess")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09807 extends LKApiYYController<I09807, O09807, I09807, O09807> {

	@Autowired
	private S09807 service;


	@Override
	protected LKApiService<I09807, O09807> getService(I09807 cin) {
		return service;
	}


	@Override
	protected I09807 beforeInvokeService(I09807 cin) throws LKException {
		return cin;
	}


	@Override
	protected O09807 afterInvokeService(I09807 cin, I09807 sin, O09807 sout) throws LKException {
		return sout;
	}

}
