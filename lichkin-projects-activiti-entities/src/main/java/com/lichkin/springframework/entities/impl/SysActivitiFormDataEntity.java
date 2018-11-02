package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.lichkin.framework.defines.enums.impl.ApproverTypeEnum;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单数据表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysActivitiFormDataEntity extends ActivitiProcessEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 70004L;

	/** 流程配置ID（SysActivitiProcessConfigEntity.id） */
	@Column(length = 64)
	private String processConfigId;

	/** 流程实例ID */
	@Column(length = 64)
	private String processInstanceId;

	/** 发起人类型（枚举） */
	@Enumerated(EnumType.STRING)
	@Column(length = 16, nullable = false)
	private ApproverTypeEnum approverType;

	/** 发起人登录ID（根据approverType存放不同表ID） */
	@Column(length = 64, nullable = false)
	private String approverLoginId;

	/** 流程编码（字典） */
	@Column(length = 64, nullable = false)
	private String processCode;

	/** 业务字段 */
	@Column(length = 64)
	private String field1;

	/** 业务字段 */
	@Column(length = 64)
	private String field2;

	/** 业务字段 */
	@Column(length = 64)
	private String field3;

	/** 业务字段 */
	@Column(length = 64)
	private String field4;

	/** 业务字段 */
	@Column(length = 64)
	private String field5;

	/** 业务字段 */
	@Column(length = 64)
	private String field6;

	/** 业务字段 */
	@Column(length = 64)
	private String field7;

	/** 业务字段 */
	@Column(length = 64)
	private String field8;

	/** 业务字段 */
	@Column(length = 64)
	private String field9;

}
