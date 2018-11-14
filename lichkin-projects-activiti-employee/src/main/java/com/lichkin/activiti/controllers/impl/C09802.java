package com.lichkin.activiti.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09802;
import com.lichkin.activiti.beans.out.impl.O09802;
import com.lichkin.activiti.services.impl.S09802;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 获取已办流程节点控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/GetDoneProcess")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09802 extends LKApiYYController<I09802, List<O09802>, I09802, List<O09802>> {

	@Autowired
	private S09802 service;


	@Override
	protected LKApiService<I09802, List<O09802>> getService(I09802 cin) {
		return service;
	}


	@Override
	protected I09802 beforeInvokeService(I09802 cin) throws LKException {
		return cin;
	}


	@Override
	protected List<O09802> afterInvokeService(I09802 cin, I09802 sin, List<O09802> sout) throws LKException {
		return sout;
	}

}
