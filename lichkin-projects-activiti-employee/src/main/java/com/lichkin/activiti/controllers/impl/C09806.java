package com.lichkin.activiti.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09806;
import com.lichkin.activiti.beans.out.impl.O09806;
import com.lichkin.activiti.services.impl.S09806;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 获取流程节点表单
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/GetProcessTaskForm")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09806 extends LKApiYYController<I09806, O09806, I09806, O09806> {

	@Autowired
	private S09806 service;


	@Override
	protected LKApiService<I09806, O09806> getService(I09806 cin) {
		return service;
	}


	@Override
	protected I09806 beforeInvokeService(I09806 cin) throws LKException {
		return cin;
	}


	@Override
	protected O09806 afterInvokeService(I09806 cin, I09806 sin, O09806 sout) throws LKException {
		return sout;
	}

}
