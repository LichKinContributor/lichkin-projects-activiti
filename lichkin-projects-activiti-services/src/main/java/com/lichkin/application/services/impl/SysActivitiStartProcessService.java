package com.lichkin.application.services.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.lichkin.framework.activiti.beans.in.impl.LKActivitiStartProcessIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.in.impl.inner.LKActivitiTaskInfoIn_SingleLineProcess;
import com.lichkin.framework.activiti.beans.out.impl.LKActivitiStartProcessOut_SingleLineProcess;
import com.lichkin.framework.activiti.services.impl.LKActivitiStartProcessService_SingleLineProcess;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataStepR;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.enums.LKCodeEnum;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.ApproverTypeEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKDBService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public abstract class SysActivitiStartProcessService extends LKDBService {

	@Getter
	@RequiredArgsConstructor
	enum ErrorCodes implements LKCodeEnum {

		start_process_failed(140000),

		process_type_config_error(140000),

		;

		private final Integer code;

	}


	/**
	 * 启动工作流
	 * @param runtime true:抛运行时异常;false:抛编译时异常;
	 * @param userId 公司ID_发起人ID
	 * @param userName 发起人姓名
	 * @param formDataId 表单数据表ID
	 * @param config 配置信息
	 * @return 流程实例ID
	 * @throws LKException 启动流程失败，流程类型有误时，可能抛出的异常
	 */
	protected String startProcess(boolean runtime, String userId, String userName, String formDataId, SysActivitiProcessConfigEntity config) throws LKException {
		if (config != null) {
			// 根据流程类型执行
			ProcessTypeEnum processType = config.getProcessType();
			try {
				switch (processType) {
					case SINGLE_LINE:
						return startSingleLineProcess(runtime, userId, userName, formDataId, config);
				}
			} catch (Exception e) {
				if (runtime) {
					throw new LKRuntimeException(ErrorCodes.start_process_failed);
				} else {
					throw new LKException(ErrorCodes.start_process_failed);
				}
			}
		}

		if (runtime) {
			throw new LKRuntimeException(ErrorCodes.process_type_config_error);
		} else {
			throw new LKException(ErrorCodes.process_type_config_error);
		}
	}


	@Autowired
	private LKActivitiStartProcessService_SingleLineProcess slp;


	private String startSingleLineProcess(boolean runtime, String userId, String userName, String formDataId, SysActivitiProcessConfigEntity config) throws LKException {
		// 初始化入参
		LKActivitiStartProcessIn_SingleLineProcess i = new LKActivitiStartProcessIn_SingleLineProcess(config.getId(), config.getProcessKey(), formDataId, config.getProcessName(), config.getProcessType());

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
				taskIn.setUserId(userId);
				taskIn.setUserName(userName);
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
			if (runtime) {
				throw new LKRuntimeException(ErrorCodes.start_process_failed);
			} else {
				throw new LKException(ErrorCodes.start_process_failed);
			}
		}

		// 修改表单的状态
		SysActivitiFormDataEntity formDataEntity = dao.findOneById(SysActivitiFormDataEntity.class, formDataId);
		formDataEntity.setProcessInstanceId(o.getProcessInstanceId());
		formDataEntity.setApprovalStatus(ApprovalStatusEnum.HANDLING);
		formDataEntity.setUsingStatus(LKUsingStatusEnum.USING);
		dao.mergeOne(formDataEntity);

		// 返回结果
		return o.getProcessInstanceId();
	}


	/**
	 * 保存表单
	 * @param compId 公司ID
	 * @param processCode 流程编码
	 * @param processConfigId 流程配置ID
	 * @param approverType 发起人类型
	 * @param approverLoginId 发起人登录ID（根据approverType存放不同表ID）
	 * @param approverUserName 发起人姓名
	 * @param dataJsonStep1 第一步表单数据
	 */
	protected String saveFormStep1(String compId, String processCode, String processConfigId, ApproverTypeEnum approverType, String approverLoginId, String dataJsonStep1) {
		// 取节点配置信息
		List<SysActivitiProcessTaskConfigEntity> listTaskConfig = getListTaskConfig(processConfigId);

		// 表单数据表
		SysActivitiFormDataEntity formData = new SysActivitiFormDataEntity();
		formData.setUsingStatus(LKUsingStatusEnum.STAND_BY);
		formData.setCompId(compId);
		formData.setApprovalStatus(ApprovalStatusEnum.PENDING);
		formData.setApprovalTime(null);

		formData.setProcessConfigId(processConfigId);
		formData.setProcessInstanceId(null);
		formData.setApproverType(approverType);
		formData.setApproverLoginId(approverLoginId);

		formData.setProcessCode(processCode);

		setFormDataBusFields(formData, listTaskConfig.get(0).getFormJson(), dataJsonStep1);// 取第一步表单配置，即发起表单配置。

		dao.persistOne(formData);

		// 表单数据步骤表
		saveFormDataSteps(dataJsonStep1, formData, listTaskConfig);

		return formData.getId();
	}


	private List<SysActivitiProcessTaskConfigEntity> getListTaskConfig(String configId) {
		QuerySQL sql = new QuerySQL(false, SysActivitiProcessTaskConfigEntity.class);
		sql.eq(SysActivitiProcessTaskConfigR.configId, configId);
		sql.addOrders(new Order(SysActivitiProcessTaskConfigR.step));
		return dao.getList(sql, SysActivitiProcessTaskConfigEntity.class);
	}


	/**
	 * 修改业务字段值
	 * @param formDataId 表单数据表ID
	 * @param formJson 表单JSON
	 * @param dataJson 数据JSON
	 */
	protected void changeBusFields(String formDataId, String formJson, String dataJson) {
		SysActivitiFormDataEntity entity = dao.findOneById(SysActivitiFormDataEntity.class, formDataId);
		setFormDataBusFields(entity, formJson, dataJson);// 取第一步表单配置，即发起表单配置。
		dao.mergeOne(entity);
	}


	private void setFormDataBusFields(SysActivitiFormDataEntity formData, String formJson, String dataJson) {
		JsonNode formJsonNode = LKJsonUtils.toJsonNode(formJson);
		JsonNode dataJsonNode = LKJsonUtils.toJsonNode(dataJson);
		for (int i = 0; i < formJsonNode.size(); i++) {
			JsonNode jsonNodeI = formJsonNode.get(i);
			JsonNode businessField = jsonNodeI.get("businessField");
			if ((businessField != null) && businessField.asBoolean(false)) {// 需要存业务字段的配置项
				try {
					Field field = SysActivitiFormDataEntity.class.getDeclaredField("field" + (i + 1));
					field.setAccessible(true);
					field.set(formData, dataJsonNode.get(jsonNodeI.get("options").get("name").asText()).asText());
				} catch (Exception e) {
					throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR, e);
				}
			}
		}
	}


	private void saveFormDataSteps(String dataJson, SysActivitiFormDataEntity formData, List<SysActivitiProcessTaskConfigEntity> listTaskConfig) {
		List<SysActivitiFormDataStepEntity> listFormDataStep = new ArrayList<>(listTaskConfig.size());
		for (int i = 0; i < listTaskConfig.size(); i++) {
			SysActivitiProcessTaskConfigEntity taskConfig = listTaskConfig.get(i);
			SysActivitiFormDataStepEntity formDataStep = new SysActivitiFormDataStepEntity();
			formData.setUsingStatus(LKUsingStatusEnum.USING);
			formDataStep.setFormDataId(formData.getId());
			formDataStep.setTaskName(taskConfig.getTaskName());
			formDataStep.setStep(taskConfig.getStep());
			formDataStep.setFormJson(taskConfig.getFormJson());
			formDataStep.setDataJson(taskConfig.getStep() == 1 ? dataJson : "{}");
			listFormDataStep.add(formDataStep);
		}
		dao.persistList(listFormDataStep);
	}


	/**
	 * 获取表单步骤
	 * @param formDataId 表单数据表ID
	 * @param step 第几步
	 * @return 表单步骤
	 */
	protected SysActivitiFormDataStepEntity getFormDataStep(String formDataId, int step) {
		QuerySQL sql = new QuerySQL(SysActivitiFormDataStepEntity.class);
		sql.eq(SysActivitiFormDataStepR.formDataId, formDataId);
		sql.eq(SysActivitiFormDataStepR.step, step);
		return dao.getOne(sql, SysActivitiFormDataStepEntity.class);
	}

}
