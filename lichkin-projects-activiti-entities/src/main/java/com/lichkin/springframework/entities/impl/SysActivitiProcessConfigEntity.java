package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.framework.defines.enums.LKPlatform;
import com.lichkin.framework.defines.enums.impl.ProcessKeyEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;
import com.lichkin.springframework.entities.suppers.BaseCompEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 流程配置表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = { "SysActivitiProcessTaskConfigEntity" }

		, insertCheckType = InsertCheckType.CHECK_RESTORE

		, updateCheckType = UpdateCheckType.BUS_CHECK

		, pageResultColumns = { "String compName 公司名称 SysCompR", "String deptName 部门名称 SysDeptR" }

		, pageQueryConditions = { "String compName 公司名称 SysCompR" }

)
public class SysActivitiProcessConfigEntity extends BaseCompEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 70000L;

	/** 平台类型（枚举） */
	@Enumerated(EnumType.STRING)
	@FieldGenerator(resultColumn = true, updateable = false)
	@Column(nullable = false, length = 8)
	private LKPlatform platformType;

	/** 流程标识（枚举） */
	@Enumerated(EnumType.STRING)
	@FieldGenerator(resultColumn = true, updateable = false)
	@Column(nullable = false, length = 24)
	private ProcessKeyEnum processKey;

	/** 流程类型（枚举） */
	@Enumerated(EnumType.STRING)
	@FieldGenerator(resultColumn = true, updateable = false)
	@Column(nullable = false, length = 11)
	private ProcessTypeEnum processType;

	/** 流程编码（字典） */
	@FieldGenerator(resultColumn = true, dictionary = true, updateable = false, check = true)
	@Column(nullable = false, length = 64)
	private String processCode;

	/** 流程名称 */
	@FieldGenerator(resultColumn = true, queryCondition = true)
	@Column(nullable = false, length = 64)
	private String processName;

	/** 部门ID（SysDeptEntity.ID） */
	@FieldGenerator(updateable = false, check = true, insertType = InsertType.CHANGE_RETAIN)
	@Column(nullable = false, length = 64)
	private String deptId;

	/** 流程步骤数 */
	@FieldGenerator()
	@Column(nullable = false)
	private Byte stepCount;

	/** 是否可用 */
	@FieldGenerator(insertType = InsertType.HANDLE_RETAIN, updateable = false)
	@Column(nullable = false)
	private Boolean available;

}
