package com.lichkin.framework.activiti.services.impl;

import static com.lichkin.springframework.db.LKDBActivitiStatics.PLATFORM_TRANSACTION_MANAGER;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.services.LKActivitiCommonService;
import com.lichkin.activiti.services.LKActivitiRejectProcessService;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiRejectProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiRejectProcessOut_SingleLineProcess;

/**
 * 驳回流程服务类 （单线流程）
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class LKActivitiRejectProcessService_SingleLineProcess extends LKActivitiCommonService implements LKActivitiRejectProcessService<LKActivitiRejectProcessIn_SingleLineProcess, LKActivitiRejectProcessOut_SingleLineProcess> {

	@Autowired
	private RuntimeService runtimeService;


	/**
	 * 驳回流程
	 * @param in 入参
	 * @return 流程实例
	 */
	@Override
	@Transactional(transactionManager = PLATFORM_TRANSACTION_MANAGER)
	public LKActivitiRejectProcessOut_SingleLineProcess RejectProcess(LKActivitiRejectProcessIn_SingleLineProcess in) {
		runtimeService.deleteProcessInstance(in.getProcessInstanceId(), in.getComment());
		return new LKActivitiRejectProcessOut_SingleLineProcess();
	}

}
