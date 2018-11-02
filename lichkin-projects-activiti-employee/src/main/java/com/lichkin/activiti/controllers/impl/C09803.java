package com.lichkin.activiti.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.beans.in.impl.I09803;
import com.lichkin.activiti.beans.out.impl.O09803;
import com.lichkin.activiti.services.impl.S09803;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiYYController;
import com.lichkin.springframework.services.LKApiService;

/**
 * 获取流程详情控制器类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/GetDetailProcess")
@LKApiType(apiType = ApiType.PERSONAL_BUSINESS)
public class C09803 extends LKApiYYController<I09803, List<O09803>, I09803, List<O09803>> {

	@Autowired
	private S09803 service;


	@Override
	protected LKApiService<I09803, List<O09803>> getService(I09803 cin) {
		return service;
	}


	@Override
	protected I09803 beforeInvokeService(I09803 cin) throws LKException {
		return cin;
	}


	@Override
	protected List<O09803> afterInvokeService(I09803 cin, I09803 sin, List<O09803> sout) throws LKException {
		return sout;
	}

}
