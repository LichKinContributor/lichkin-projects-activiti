package com.lichkin.activiti.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09800;
import com.lichkin.activiti.beans.out.impl.O09800;
import com.lichkin.activiti.services.impl.S09800;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 获取流程列表
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/GetProcessList")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09800 extends LKApiYYController<I09800, List<O09800>, I09800, List<O09800>> {

	@Autowired
	private S09800 service;


	@Override
	protected LKApiService<I09800, List<O09800>> getService(I09800 cin) {
		return service;
	}


	@Override
	protected I09800 beforeInvokeService(I09800 cin) throws LKException {
		return cin;
	}


	@Override
	protected List<O09800> afterInvokeService(I09800 cin, I09800 sin, List<O09800> sout) throws LKException {
		return sout;
	}

}
