package com.lichkin.activiti.GetDetailProcess;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.framework.activiti.beans.in.impl.LKActivitiGetDetailProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiGetDetailProcessOut_SingleLineProcess;
import com.lichkin.framework.activiti.services.impl.LKActivitiGetDetailProcessService_SingleLineProcess;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.framework.utils.LKEnumUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.services.LKApiService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service(Statics.SERVICE_NAME)
public class S implements LKApiService<I, List<O>> {

	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		process_type_config_error(30000),

		;

		private final Integer code;

	}


	@Override
	@Transactional
	public List<O> handle(I sin, ApiKeyValues<I> params) throws LKException {
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
	private List<O> getDetailProcess(I in) {
		// 初始化入参
		LKActivitiGetDetailProcessIn_SingleLineProcess i = new LKActivitiGetDetailProcessIn_SingleLineProcess(in.getProcessInstanceId());

		// 调用服务类方法
		List<LKActivitiGetDetailProcessOut_SingleLineProcess> taskList = slp.getDetailProcess(i);

		// 初始化出参
		List<O> outList = new ArrayList<>();
		for (LKActivitiGetDetailProcessOut_SingleLineProcess task : taskList) {
			O out = LKBeanUtils.newInstance(task, O.class);
			out.setFormDataId(task.getBusinessKey());
			outList.add(out);
		}
		// 返回结果
		return outList;
	}

}
