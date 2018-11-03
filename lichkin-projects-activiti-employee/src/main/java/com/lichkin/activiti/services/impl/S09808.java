package com.lichkin.activiti.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09808;
import com.lichkin.activiti.beans.out.impl.O09808;
import com.lichkin.application.services.impl.SysActivitiStartProcessService;
import com.lichkin.framework.defines.enums.impl.ApproverTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.services.LKApiService;

/**
 * 保存表单服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09808 extends SysActivitiStartProcessService implements LKApiService<I09808, O09808> {

	@Override
	@Transactional
	public O09808 handle(I09808 sin, String locale, String compId, String loginId) throws LKException {
		String dataJson = sin.getDataJson();
		String formDataId = sin.getFormDataId();
		if (StringUtils.isNotBlank(formDataId)) {
			// 修改数据步骤表
			int step = sin.getStep();
			SysActivitiFormDataStepEntity formDataStep = getFormDataStep(formDataId, step);
			formDataStep.setDataJson(dataJson);
			dao.mergeOne(formDataStep);

			// 修改表单数据表
			if (step == 1) {// 只有第一步需要修改业务值
				changeBusFields(formDataId, formDataStep.getFormJson(), dataJson);
			}

			return new O09808(formDataId);
		} else {
			return new O09808(saveFormStep1(compId, sin.getProcessCode(), sin.getProcessConfigId(), ApproverTypeEnum.SysEmployee, loginId, dataJson));
		}
	}

}
