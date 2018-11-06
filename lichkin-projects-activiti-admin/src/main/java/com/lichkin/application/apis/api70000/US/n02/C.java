package com.lichkin.application.apis.api70000.US.n02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusUpdateUsingStatusController;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@RestController("SysActivitiProcessConfigUS02Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_ADMIN + "/SysActivitiProcessConfig/US02")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusUpdateUsingStatusController<I, SysActivitiProcessConfigEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusUpdateUsingStatusService<I, SysActivitiProcessConfigEntity> getService(I cin) {
		return service;
	}

}
