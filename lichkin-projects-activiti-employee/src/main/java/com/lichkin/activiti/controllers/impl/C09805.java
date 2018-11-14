package com.lichkin.activiti.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09805;
import com.lichkin.activiti.beans.out.impl.O09805;
import com.lichkin.activiti.services.impl.S09805;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 获取单个表单控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/GetFormDataStep")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09805 extends LKApiYYController<I09805, List<O09805>, I09805, List<O09805>> {

	@Autowired
	private S09805 service;


	@Override
	protected LKApiService<I09805, List<O09805>> getService(I09805 cin) {
		return service;
	}


	@Override
	protected I09805 beforeInvokeService(I09805 cin) throws LKException {
		return cin;
	}


	@Override
	protected List<O09805> afterInvokeService(I09805 cin, I09805 sin, List<O09805> sout) throws LKException {
		return sout;
	}

}
