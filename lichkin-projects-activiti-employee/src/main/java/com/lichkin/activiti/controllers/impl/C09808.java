package com.lichkin.activiti.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09808;
import com.lichkin.activiti.beans.out.impl.O09808;
import com.lichkin.activiti.services.impl.S09808;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 保存表单控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/SubmitForm")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09808 extends LKApiYYController<I09808, O09808, I09808, O09808> {

	@Autowired
	private S09808 service;


	@Override
	protected LKApiService<I09808, O09808> getService(I09808 cin) {
		return service;
	}


	@Override
	protected I09808 beforeInvokeService(I09808 cin) throws LKException {
		return cin;
	}


	@Override
	protected O09808 afterInvokeService(I09808 cin, I09808 sin, O09808 sout) throws LKException {
		return sout;
	}

}
