package com.lichkin.activiti.services.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.lichkin.activiti.beans.in.impl.I09808;
import com.lichkin.activiti.beans.out.impl.O09808;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataStepR;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

/**
 * 保存表单服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09808 extends LKDBService implements LKApiService<I09808, O09808> {

	@Override
	@Transactional
	public O09808 handle(I09808 sin, String locale, String compId, String loginId) throws LKException {
		if (StringUtils.isNotBlank(sin.getFormDataId())) {
			// 修改表单
			QuerySQL sqlObj = new QuerySQL(SysActivitiFormDataStepEntity.class);
			sqlObj.eq(SysActivitiFormDataStepR.formDataId, sin.getFormDataId());
			sqlObj.eq(SysActivitiFormDataStepR.step, sin.getStep());
			SysActivitiFormDataStepEntity formDataStep = dao.getOne(sqlObj, SysActivitiFormDataStepEntity.class);
			formDataStep.setDataJson(sin.getDataJson());
			dao.mergeOne(formDataStep);

			if (sin.getStep() == 1) {
				SysActivitiFormDataEntity entity = dao.findOneById(SysActivitiFormDataEntity.class, sin.getFormDataId());
				saveFieldValue(entity, formDataStep.getFormJson(), sin.getDataJson());
				dao.mergeOne(entity);
			}
		} else {
			QuerySQL sqlObj = new QuerySQL(SysActivitiProcessTaskConfigEntity.class);
			sqlObj.eq(SysActivitiProcessTaskConfigR.configId, sin.getProcessConfigId());
			sqlObj.addOrders(new Order(SysActivitiProcessTaskConfigR.step));
			List<SysActivitiProcessTaskConfigEntity> taskConfigList = dao.getList(sqlObj, SysActivitiProcessTaskConfigEntity.class);

			// 保存表单主表
			SysActivitiFormDataEntity entity = LKBeanUtils.newInstance(true, sin, SysActivitiFormDataEntity.class);
			saveFieldValue(entity, taskConfigList.get(0).getFormJson(), sin.getDataJson());
			entity.setApprovalStatus(ApprovalStatusEnum.PENDING);
			entity.setUsingStatus(LKUsingStatusEnum.STAND_BY);
			dao.persistOne(entity);
			sin.setFormDataId(entity.getId());

			// 保存表单子表
			List<SysActivitiFormDataStepEntity> stepList = new ArrayList<>();
			int i = 0;
			for (SysActivitiProcessTaskConfigEntity taskConfig : taskConfigList) {
				SysActivitiFormDataStepEntity step = new SysActivitiFormDataStepEntity();
				step.setFormDataId(entity.getId());
				step.setStep(taskConfig.getStep());
				step.setTaskName(taskConfig.getTaskName());
				step.setFormJson(taskConfig.getFormJson());
				if (i == 0) {
					step.setDataJson(sin.getDataJson());
				} else {
					step.setDataJson("{}");
				}
				entity.setUsingStatus(LKUsingStatusEnum.USING);
				stepList.add(step);
				i++;
			}
			dao.persistList(stepList);
		}
		return new O09808(sin.getFormDataId());
	}


	private void saveFieldValue(SysActivitiFormDataEntity entity, String formJson, String dataJson) {
		if (StringUtils.isNotBlank(formJson)) {
			JsonNode formJsonNode = LKJsonUtils.toJsonNode(formJson);
			JsonNode dataJsonNode = LKJsonUtils.toJsonNode(dataJson);
			int fieldIndex = 1;
			for (int i = 0; i < formJsonNode.size(); i++) {
				JsonNode jsonNodeI = formJsonNode.get(i);
				JsonNode businessField = jsonNodeI.get("businessField");
				if ((businessField != null) && businessField.asBoolean(false)) {
					try {
						Field field = entity.getClass().getDeclaredField("field" + fieldIndex++);
						field.setAccessible(true);
						field.set(entity, dataJsonNode.get(jsonNodeI.get("options").get("name").asText()).asText());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
