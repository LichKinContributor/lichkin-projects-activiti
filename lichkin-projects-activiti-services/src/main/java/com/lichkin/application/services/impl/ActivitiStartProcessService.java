package com.lichkin.application.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.defines.enums.impl.ApproverTypeEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.utils.LKDateTimeUtils;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

@Service
public class ActivitiStartProcessService extends SysActivitiStartProcessService {

	/**
	 * 启动流程
	 * @param entity 业务表实体类对象
	 * @param compId 公司ID
	 * @param deptId 部门ID
	 * @param processCode 流程编码
	 * @param approverType 发起人类型
	 * @param approverLoginId 发起人登录ID（根据approverType存放不同表ID）
	 * @param approverUserName 发起人姓名
	 * @param dataJsonStep1 第一步表单数据
	 */
	private void start(ActivitiProcessEntity entity, String compId, String deptId, String processCode, ApproverTypeEnum approverType, String approverLoginId, String approverUserName, String dataJsonStep1) {
		SysActivitiProcessConfigEntity config = getAvailableActivitiProcessConfigId(compId, deptId, processCode);

		if (config == null) {
			// 没有配置审批流程，则直接结束审批。
			entity.setApprovalStatus(ApprovalStatusEnum.APPROVED);
			entity.setApprovalTime(LKDateTimeUtils.now());
		} else {
			// 有配置审批流程，则设置为审批中状态。
			entity.setApprovalStatus(ApprovalStatusEnum.HANDLING);
			entity.setApprovalTime(null);

			String configId = config.getId();

			// 保存表单
			String formDataId = saveFormStep1(compId, processCode, configId, approverType, approverLoginId, dataJsonStep1);

			// 发起流程
			startProcess(compId + "_" + approverLoginId, approverUserName, formDataId, config);
		}
	}


	private SysActivitiProcessConfigEntity getAvailableActivitiProcessConfigId(String compId, String deptId, String processCode) {
		QuerySQL sql = new QuerySQL(false, SysActivitiProcessConfigEntity.class);
		sql.eq(SysActivitiProcessConfigR.usingStatus, LKUsingStatusEnum.USING);
		sql.eq(SysActivitiProcessConfigR.available, Boolean.TRUE);
		sql.eq(SysActivitiProcessConfigR.compId, compId);
		sql.eq(SysActivitiProcessConfigR.deptId, StringUtils.trimToEmpty(deptId));
		sql.eq(SysActivitiProcessConfigR.processCode, processCode);
		return dao.getOne(sql, SysActivitiProcessConfigEntity.class);
	}


	/**
	 * 启动流程（管理员）
	 * @param entity 业务表实体类对象
	 * @param compId 公司ID
	 * @param processCode 流程编码
	 * @param approverLoginId 发起人登录ID（SysAdminLoginEntity.id）
	 * @param approverUserName 发起人姓名
	 * @param dataJsonStep1 第一步表单数据
	 */
	public void startByAdmin(ActivitiProcessEntity entity, String compId, String processCode, String approverLoginId, String approverUserName, String dataJsonStep1) {
		start(entity, compId, "", processCode, ApproverTypeEnum.SysAdminLogin, approverLoginId, approverUserName, dataJsonStep1);
	}


	/**
	 * 启动流程（员工）
	 * @param entity 业务表实体类对象
	 * @param compId 公司ID
	 * @param deptId 部门ID
	 * @param processCode 流程编码
	 * @param approverLoginId 发起人登录ID（SysEmployeeEntity.id）
	 * @param approverUserName 发起人姓名
	 * @param dataJsonStep1 第一步表单数据
	 */
	public void startByEmployee(ActivitiProcessEntity entity, String compId, String deptId, String processCode, String approverLoginId, String approverUserName, String dataJsonStep1) {
		start(entity, compId, deptId, processCode, ApproverTypeEnum.SysEmployee, approverLoginId, approverUserName, dataJsonStep1);
	}


	/**
	 * 启动流程（用户）
	 * @param entity 业务表实体类对象
	 * @param processCode 流程编码
	 * @param approverLoginId 发起人登录ID（SysUserLoginEntity.id）
	 * @param approverUserName 发起人姓名
	 * @param dataJsonStep1 第一步表单数据
	 */
	public void startByUser(ActivitiProcessEntity entity, String processCode, String approverLoginId, String approverUserName, String dataJsonStep1) {
		start(entity, LKFrameworkStatics.LichKin, "", processCode, ApproverTypeEnum.SysUserLogin, approverLoginId, approverUserName, dataJsonStep1);
	}

}
