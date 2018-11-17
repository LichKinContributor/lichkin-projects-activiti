package com.lichkin.activiti.SubmitForm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.application.services.impl.SysActivitiStartProcessService;
import com.lichkin.framework.defines.enums.impl.ApproverTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.services.LKApiService;

@Service(Statics.SERVICE_NAME)
public class S extends SysActivitiStartProcessService implements LKApiService<I, O> {

	@Override
	@Transactional
	public O handle(I sin, String locale, String compId, String loginId) throws LKException {
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

			return new O(formDataId);
		} else {
			return new O(saveFormStep1(compId, sin.getProcessCode(), sin.getProcessConfigId(), ApproverTypeEnum.SysEmployee, loginId, dataJson));
		}
	}

}
