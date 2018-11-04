package com.lichkin.activiti.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09811;
import com.lichkin.activiti.beans.out.impl.O09811;
import com.lichkin.activiti.services.impl.S09811;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 驳回流程节点控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/RejectProcess")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09811 extends LKApiYYController<I09811, O09811, I09811, O09811> {

	@Autowired
	private S09811 service;


	@Override
	protected LKApiService<I09811, O09811> getService(I09811 cin) {
		return service;
	}


	@Override
	protected I09811 beforeInvokeService(I09811 cin) throws LKException {
		return cin;
	}


	@Override
	protected O09811 afterInvokeService(I09811 cin, I09811 sin, O09811 sout) throws LKException {
		return sout;
	}

}
