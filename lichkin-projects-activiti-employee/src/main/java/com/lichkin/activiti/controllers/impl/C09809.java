package com.lichkin.activiti.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lichkin.activiti.services.impl.S09809;
import com.lichkin.framework.beans.impl.LKRequestIDsUsingStatusBean;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.web.annotations.LKApiType;
import com.lichkin.framework.web.enums.ApiType;
import com.lichkin.springframework.controllers.LKApiBusUpdateUsingStatusController;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

/**
 * 修改表单状态
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@RestController
@RequestMapping(value = LKFrameworkStatics.WEB_MAPPING_API_WEB_EMPLOYEE + "/Activiti/UpdateForm")
@LKApiType(apiType = ApiType.COMPANY_BUSINESS)
public class C09809 extends LKApiBusUpdateUsingStatusController<LKRequestIDsUsingStatusBean, SysActivitiFormDataEntity> {

	@Autowired
	private S09809 service;


	@Override
	protected LKApiBusUpdateUsingStatusService<LKRequestIDsUsingStatusBean, SysActivitiFormDataEntity> getService(LKRequestIDsUsingStatusBean cin) {
		return service;
	}

}
