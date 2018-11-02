package com.lichkin.activiti.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09801;
import com.lichkin.activiti.beans.out.impl.O09801;
import com.lichkin.activiti.services.impl.S09801;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 获取待办流程节点控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/GetPendingProcess")
@LKApiType(apiType = ApiType.PERSONAL_BUSINESS)
public class C09801 extends LKApiYYController<I09801, List<O09801>, I09801, List<O09801>> {

	@Autowired
	private S09801 service;


	@Override
	protected LKApiService<I09801, List<O09801>> getService(I09801 cin) {
		return service;
	}


	@Override
	protected I09801 beforeInvokeService(I09801 cin) throws LKException {
		return cin;
	}


	@Override
	protected List<O09801> afterInvokeService(I09801 cin, I09801 sin, List<O09801> sout) throws LKException {
		return sout;
	}

}
