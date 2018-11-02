package com.lichkin.springframework.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.springframework.daoA.impl.SysActivitiConnectorDao;

@EnableScheduling
@Service
public class SysActivitiConnectorService {

	@Autowired
	private SysActivitiConnectorDao dao;


	@Scheduled(cron = "0 0/1 * * * ?")
	public void connect() {
		dao.findById(LKFrameworkStatics.LichKin);
	}

}
