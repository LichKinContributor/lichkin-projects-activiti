package com.lichkin.activiti.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09804;
import com.lichkin.activiti.beans.out.impl.O09804;
import com.lichkin.activiti.services.impl.S09804;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 获取表单列表控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/GetFormPage")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09804 extends LKApiYYController<I09804, Page<O09804>, I09804, Page<O09804>> {

	@Autowired
	private S09804 service;


	@Override
	protected LKApiService<I09804, Page<O09804>> getService(I09804 cin) {
		return service;
	}


	@Override
	protected I09804 beforeInvokeService(I09804 cin) throws LKException {
		return cin;
	}


	@Override
	protected Page<O09804> afterInvokeService(I09804 cin, I09804 sin, Page<O09804> sout) throws LKException {
		return sout;
	}

}
