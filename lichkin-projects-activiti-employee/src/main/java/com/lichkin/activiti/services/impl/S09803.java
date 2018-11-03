package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09803;
import com.lichkin.activiti.beans.out.impl.O09803;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiGetDetailProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiGetDetailProcessOut_SingleLineProcess;
import com.lichkin.framework.activiti.services.impl.LKActivitiGetDetailProcessService_SingleLineProcess;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.framework.utils.LKEnumUtils;
import com.lichkin.springframework.services.LKApiService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 获取流程详情服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09803 implements LKApiService<I09803, List<O09803>> {

	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		process_type_config_error(140000),

		;

		private final Integer code;

	}


	@Override
	@Transactional
	public List<O09803> handle(I09803 sin, String locale, String compId, String loginId) throws LKException {
		if (sin.getProcessType() != null) {
			// 根据流程类型执行
			ProcessTypeEnum processType = LKEnumUtils.getEnum(ProcessTypeEnum.class, sin.getProcessType());
			try {
				switch (processType) {
					case SINGLE_LINE:
						return getDetailProcess(sin);
				}
			} catch (Exception e) {
				throw new LKException(ErrorCodes.process_type_config_error);
			}
		}

		throw new LKException(ErrorCodes.process_type_config_error);
	}


	@Autowired
	private LKActivitiGetDetailProcessService_SingleLineProcess slp;


	/**
	 * 获取流程详情
	 * @param in 入参
	 * @return 流程详情列表信息
	 */
	private List<O09803> getDetailProcess(I09803 in) {
		// 初始化入参
		LKActivitiGetDetailProcessIn_SingleLineProcess i = new LKActivitiGetDetailProcessIn_SingleLineProcess(in.getProcessInstanceId());

		// 调用服务类方法
		List<LKActivitiGetDetailProcessOut_SingleLineProcess> taskList = slp.getDetailProcess(i);

		// 初始化出参
		List<O09803> outList = new ArrayList<>();
		for (LKActivitiGetDetailProcessOut_SingleLineProcess task : taskList) {
			O09803 out = LKBeanUtils.newInstance(task, O09803.class);
			out.setFormDataId(task.getBusinessKey());
			outList.add(out);
		}
		// 返回结果
		return outList;
	}

}
