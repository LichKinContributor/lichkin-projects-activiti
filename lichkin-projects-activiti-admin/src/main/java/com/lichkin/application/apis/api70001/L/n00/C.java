package com.lichkin.application.apis.api70001.L.n00;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusGetListController;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiBusGetListService;

@RestController("SysActivitiProcessTaskConfigL00Controller")
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB + "/SysActivitiProcessTaskConfig/L")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C extends LKApiBusGetListController<I, O, SysActivitiProcessTaskConfigEntity> {

	@Autowired
	private S service;


	@Override
	protected LKApiBusGetListService<I, O, SysActivitiProcessTaskConfigEntity> getService(I cin) {
		return service;
	}

}
