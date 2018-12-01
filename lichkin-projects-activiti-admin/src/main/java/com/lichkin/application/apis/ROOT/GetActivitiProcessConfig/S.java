package com.lichkin.application.apis.ROOT.GetActivitiProcessConfig;

import org.springframework.stereotype.Service;

import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.services.LKApiBusGetOneService;

@Service(Statics.SERVICE_NAME)
public class S extends LKApiBusGetOneService<LKRequestIDBean, O, SysActivitiProcessConfigEntity> {

}
