package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.lichkin.framework.defines.enums.impl.LKClientTypeEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.springframework.entities.suppers.BaseCompEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 审批流程接口请求日志表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysActivitiApiRequestLogCompleteProcessEntity extends BaseCompEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 70003L;

	/** 公司ID_员工ID */
	@Column(nullable = false, length = 129)
	private String userId;

	/** 流程类型（枚举） */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 11)
	private ProcessTypeEnum processType;

	/** 流程实例ID */
	@Column(nullable = false, length = 64)
	private String processInstanceId;

	/** 客户端唯一标识（字典） */
	@Column(length = 64)
	private String appKey;

	/** 客户端类型（枚举） */
	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private LKClientTypeEnum clientType;

	/** 客户端版本号（大版本号） */
	@Column(nullable = false)
	private Byte versionX;

	/** 客户端版本号（中版本号） */
	@Column(nullable = false)
	private Byte versionY;

	/** 客户端版本号（小版本号） */
	@Column(nullable = false)
	private Short versionZ;

}
