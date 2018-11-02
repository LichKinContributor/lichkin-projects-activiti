package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09807;
import com.lichkin.activiti.beans.out.impl.O09807;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiStartProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.in.impl.inner.LKActivitiTaskInfoIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiStartProcessOut_SingleLineProcess;
import com.lichkin.framework.activiti.services.impl.LKActivitiStartProcessService_SingleLineProcess;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiApiRequestLogStartProcessEntity;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 启动流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09807 extends LKDBService implements LKApiService<I09807, O09807> {

	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		start_process_failed(140000),

		process_type_config_error(140000),

		;

		private final Integer code;

	}


	@Override
	@Transactional
	public O09807 handle(I09807 sin, String locale, String compId, String loginId) throws LKException {
		// 记录请求日志
		SysActivitiApiRequestLogStartProcessEntity log = LKBeanUtils.newInstance(false, sin, SysActivitiApiRequestLogStartProcessEntity.class);
		dao.mergeOne(log);

		// 查询流程配置信息
		SysActivitiProcessConfigEntity config = dao.findOneById(SysActivitiProcessConfigEntity.class, sin.getProcessConfigId());
		if (config != null) {
			// 根据流程类型执行
			ProcessTypeEnum processType = config.getProcessType();
			try {
				switch (processType) {
					case SINGLE_LINE:
						return startSingleLineProcess(sin, config);
				}
			} catch (Exception e) {
				throw new LKException(ErrorCodes.start_process_failed);
			}
		}

		throw new LKException(ErrorCodes.process_type_config_error);
	}


	@Autowired
	private LKActivitiStartProcessService_SingleLineProcess slp;


	/**
	 * 启动单线流程
	 * @param sin 入参
	 * @param config 流程配置信息
	 * @return 流程实例
	 * @throws LKException
	 */
	private O09807 startSingleLineProcess(I09807 sin, SysActivitiProcessConfigEntity config) throws LKException {
		// 初始化入参
		LKActivitiStartProcessIn_SingleLineProcess i = new LKActivitiStartProcessIn_SingleLineProcess(config.getId(), config.getProcessKey(), sin.getBusinessKey(), config.getProcessName(), config.getProcessType(), sin.getComment());

		// 查询流程对应的节点信息
		QuerySQL sql = new QuerySQL(false, SysActivitiProcessTaskConfigEntity.class);
		sql.eq(SysActivitiProcessTaskConfigR.configId, config.getId());
		sql.addOrders(new Order(SysActivitiProcessTaskConfigR.step));
		List<SysActivitiProcessTaskConfigEntity> taskConfigList = dao.getList(sql, SysActivitiProcessTaskConfigEntity.class);
		List<LKActivitiTaskInfoIn_SingleLineProcess> taskList = new ArrayList<>();
		for (int j = 0; j < taskConfigList.size(); j++) {
			LKActivitiTaskInfoIn_SingleLineProcess taskIn = LKBeanUtils.newInstance(taskConfigList.get(j), LKActivitiTaskInfoIn_SingleLineProcess.class);
			// 第一步为发起人,特别处理
			if (j == 0) {
				taskIn.setUserId(sin.getUserId());
				taskIn.setUserName(sin.getUserName());
			} else {
				// 员工登录ID&公司ID（同一个员工在多个公司只有一个登录ID）
				taskIn.setUserId(taskIn.getUserId() + "_" + config.getCompId());
			}
			taskList.add(taskIn);
		}
		i.setTaskList(taskList);

		// 调用服务类方法
		LKActivitiStartProcessOut_SingleLineProcess o;
		try {
			o = slp.startProcess(i);
		} catch (Exception e) {
			throw new LKException(ErrorCodes.start_process_failed);
		}

		// 修改表单的状态
		SysActivitiFormDataEntity formDataEntity = dao.findOneById(SysActivitiFormDataEntity.class, sin.getBusinessKey());
		formDataEntity.setProcessInstanceId(o.getProcessInstanceId());
		formDataEntity.setApprovalStatus(ApprovalStatusEnum.HANDLING);
		formDataEntity.setUsingStatus(LKUsingStatusEnum.USING);
		dao.mergeOne(formDataEntity);

		// 初始化出参
		O09807 out = new O09807(o.getProcessInstanceId(), config.getProcessType());

		// 返回结果
		return out;
	}

}
