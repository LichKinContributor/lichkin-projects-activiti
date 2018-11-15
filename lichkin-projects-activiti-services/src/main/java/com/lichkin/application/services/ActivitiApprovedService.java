package com.lichkin.application.services;

import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;

public interface ActivitiApprovedService {

	void handleBusiness(SysActivitiFormDataEntity formDataEntity);
}
