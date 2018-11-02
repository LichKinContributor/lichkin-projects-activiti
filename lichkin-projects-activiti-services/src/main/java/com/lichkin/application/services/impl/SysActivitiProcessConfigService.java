package com.lichkin.application.services.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.ApproverTypeEnum;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.ProcessKeyEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataStepEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;
import com.lichkin.springframework.services.LKDBService;

@Service
public class SysActivitiProcessConfigService extends LKDBService {

	/**
	 * 初始化流程信息
	 * @param entity 业务表实体类对象
	 * @param compId 公司ID
	 * @param deptId 部门ID
	 * @param processKey 流程标识
	 * @param processType 流程类型
	 * @param processCode 流程编码
	 * @param approverType 发起人类型
	 * @param approverLoginId 发起人登录ID（根据approverType存放不同表ID）
	 * @param dataJson 第一步表单数据
	 * @param busFields 业务字段（最多可用9个）
	 */
	private void initProcessInfo(ActivitiProcessEntity entity, String compId, String deptId, ProcessKeyEnum processKey, ProcessTypeEnum processType, String processCode, ApproverTypeEnum approverType, String approverLoginId, String dataJson, String... busFields) {
		String configId = getAvailableActivitiProcessConfigId(compId, deptId, processKey, processType, processCode);

		if (configId == null) {
			// 没有配置审批流程，则直接结束审批。
			entity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
			entity.setApprovalTime(LKDateTimeUtils.now());
		} else {
			// 有配置审批流程，则设置为审批中状态。
			entity.setApprovalStatus(ApprovalStatusEnum.HANDLING);
			entity.setApprovalTime(null);

			// TODO 发起工作流
			String processInstanceId = null;

			// 表单数据表
			SysActivitiFormDataEntity formData = new SysActivitiFormDataEntity();
			formData.setCompId(compId);
			formData.setApprovalStatus(ApprovalStatusEnum.HANDLING);
			formData.setApprovalTime(null);

			formData.setProcessConfigId(configId);
			formData.setProcessInstanceId(processInstanceId);
			formData.setApproverType(approverType);
			formData.setApproverLoginId(approverLoginId);

			formData.setProcessCode(processCode);

			try {
				for (int i = 0; i < busFields.length; i++) {
					Field field = SysActivitiFormDataEntity.class.getDeclaredField("field" + (i + 1));
					field.setAccessible(true);
					field.set(formData, busFields[i]);
				}
			} catch (Exception e) {
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR, e);
			}

			dao.persistOne(formData);

			// 表单数据步骤表
			List<SysActivitiProcessTaskConfigEntity> listTaskConfig = getListTaskConfig(configId);
			List<SysActivitiFormDataStepEntity> listFormDataStep = new ArrayList<>(listTaskConfig.size());
			for (int i = 0; i < listTaskConfig.size(); i++) {
				SysActivitiProcessTaskConfigEntity taskConfig = listTaskConfig.get(i);
				SysActivitiFormDataStepEntity formDataStep = new SysActivitiFormDataStepEntity();
				formDataStep.setFormDataId(formData.getId());
				formDataStep.setTaskName(taskConfig.getTaskName());
				formDataStep.setStep(taskConfig.getStep());
				formDataStep.setFormJson(taskConfig.getFormJson());
				formDataStep.setDataJson(taskConfig.getStep() == 1 ? dataJson : "{}");
				listFormDataStep.add(formDataStep);
			}
			dao.persistList(listFormDataStep);
		}
	}


	/**
	 * 初始化流程信息
	 * @param entity 业务表实体类对象
	 * @param compId 公司ID
	 * @param deptId 部门ID
	 * @param processCode 流程编码
	 * @param approverType 发起人类型
	 * @param approverLoginId 发起人登录ID（根据approverType存放不同表ID）
	 * @param dataJson 第一步表单数据
	 * @param busFields 业务字段（最多可用9个）
	 */
	public void initProcessInfo(ActivitiProcessEntity entity, String compId, String deptId, String processCode, ApproverTypeEnum approverType, String approverLoginId, String dataJson, String... busFields) {
		initProcessInfo(entity, compId, deptId, ProcessKeyEnum.GeneralSingleLineProcess, ProcessTypeEnum.SINGLE_LINE, processCode, approverType, approverLoginId, dataJson, busFields);
	}


	/**
	 * 初始化流程信息
	 * @param entity 业务表实体类对象
	 * @param compId 公司ID
	 * @param processCode 流程编码
	 * @param approverLoginId 发起人登录ID（SysAdminLoginEntity.id）
	 * @param dataJson 第一步表单数据
	 * @param busFields 业务字段（最多可用9个）
	 */
	public void initProcessInfoByAdmin(ActivitiProcessEntity entity, String compId, String processCode, String approverLoginId, String dataJson, String... busFields) {
		initProcessInfo(entity, compId, "", processCode, ApproverTypeEnum.SysAdminLogin, approverLoginId, dataJson, busFields);
	}


	/**
	 * 初始化流程信息
	 * @param entity 业务表实体类对象
	 * @param compId 公司ID
	 * @param deptId 部门ID
	 * @param processCode 流程编码
	 * @param approverLoginId 发起人登录ID（SysEmployeeLoginEntity.id）
	 * @param dataJson 第一步表单数据
	 * @param busFields 业务字段（最多可用9个）
	 */
	public void initProcessInfoByEmployee(ActivitiProcessEntity entity, String compId, String deptId, String processCode, String approverLoginId, String dataJson, String... busFields) {
		initProcessInfo(entity, compId, deptId, processCode, ApproverTypeEnum.SysEmployee, approverLoginId, dataJson, busFields);
	}


	private String getAvailableActivitiProcessConfigId(String compId, String deptId, ProcessKeyEnum processKey, ProcessTypeEnum processType, String processCode) {
		QuerySQL sql = new QuerySQL(false, SysActivitiProcessConfigEntity.class);
		sql.eq(SysActivitiProcessConfigR.processKey, processKey);
		sql.eq(SysActivitiProcessConfigR.processType, processType);
		sql.eq(SysActivitiProcessConfigR.processCode, processCode);
		sql.eq(SysActivitiProcessConfigR.compId, compId);
		sql.eq(SysActivitiProcessConfigR.deptId, StringUtils.trimToEmpty(deptId));
		sql.eq(SysActivitiProcessConfigR.available, Boolean.TRUE);
		SysActivitiProcessConfigEntity config = dao.getOne(sql, SysActivitiProcessConfigEntity.class);
		if (config == null) {
			return null;
		}
		return config.getId();
	}


	private List<SysActivitiProcessTaskConfigEntity> getListTaskConfig(String configId) {
		QuerySQL sql = new QuerySQL(false, SysActivitiProcessTaskConfigEntity.class);
		sql.eq(SysActivitiProcessTaskConfigR.configId, configId);
		sql.addOrders(new Order(SysActivitiProcessTaskConfigR.step));
		return dao.getList(sql, SysActivitiProcessTaskConfigEntity.class);
	}

}
