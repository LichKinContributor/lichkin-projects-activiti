package com.lichkin.activiti.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09811;
import com.lichkin.activiti.beans.out.impl.O09811;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiRejectProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiRejectProcessOut_SingleLineProcess;
import com.lichkin.framework.activiti.services.impl.LKActivitiRejectProcessService_SingleLineProcess;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKEnumUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 驳回流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09811 extends LKDBService implements LKApiService<I09811, O09811> {

	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		complete_process_failed(140000),

		process_type_config_error(140000),

		;

		private final Integer code;

	}


	@Override
	@Transactional
	public O09811 handle(I09811 sin, String locale, String compId, String loginId) throws LKException {
		if (sin.getProcessType() != null) {
			// 根据流程类型执行
			ProcessTypeEnum processType = LKEnumUtils.getEnum(ProcessTypeEnum.class, sin.getProcessType());
			try {
				switch (processType) {
					case SINGLE_LINE:
						return RejectProcessTask(sin);
				}
			} catch (Exception e) {
				throw new LKException(ErrorCodes.complete_process_failed);
			}
		}

		throw new LKException(ErrorCodes.process_type_config_error);
	}


	@Autowired
	private LKActivitiRejectProcessService_SingleLineProcess slp;


	/**
	 * 驳回单线流程
	 * @param in 驳回流程入参
	 * @return 驳回流程结果
	 * @throws LKException
	 */
	private O09811 RejectProcessTask(I09811 in) throws LKException {
		// 初始化入参
		LKActivitiRejectProcessIn_SingleLineProcess i = new LKActivitiRejectProcessIn_SingleLineProcess(in.getProcessInstanceId(), in.getUserId(), in.getComment());
		// 调用服务类方法
		try {
			@SuppressWarnings("unused")
			LKActivitiRejectProcessOut_SingleLineProcess o = slp.RejectProcess(i);
		} catch (Exception e) {
			throw new LKException(ErrorCodes.complete_process_failed);
		}

		// 修改表单状态
		QuerySQL sql = new QuerySQL(SysActivitiFormDataEntity.class);
		sql.eq(SysActivitiFormDataR.processInstanceId, in.getProcessInstanceId());
		SysActivitiFormDataEntity formDataEntity = dao.getOne(sql, SysActivitiFormDataEntity.class);
		formDataEntity.setApprovalStatus(ApprovalStatusEnum.REJECT);
		dao.mergeOne(formDataEntity);

		// 初始化出参
		O09811 out = new O09811();

		// 返回结果
		return out;
	}

}
