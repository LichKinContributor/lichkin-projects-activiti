package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.lichkin.framework.defines.annotations.ClassGenerator;
import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertCheckType;
import com.lichkin.framework.defines.annotations.UpdateCheckType;
import com.lichkin.springframework.entities.suppers.IDEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 流程节点配置表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
@ClassGenerator(

		afterSaveMain = false, IUSubTables = {}

		, insertCheckType = InsertCheckType.CHECK_RESTORE

		, updateCheckType = UpdateCheckType.CHECK

		, pageQueryConditions = { "boolean withoutFirst 是否排除第一步 sin" }

)
public class SysActivitiProcessTaskConfigEntity extends IDEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 70001L;

	/** 流程配置表ID（SysActivitiProcessConfigEntity.id） */
	@FieldGenerator(queryCondition = true, queryConditionLike = false)
	@Column(length = 64, nullable = false)
	private String configId;

	/** 节点名称 */
	@FieldGenerator(resultColumn = true)
	@Column(length = 32, nullable = false)
	private String taskName;

	/** 审批人（SysEmployeeEntity.id） */
	@FieldGenerator(resultColumn = true)
	@Column(length = 64, nullable = false)
	private String approver;

	/** 用户姓名 */
	@FieldGenerator(resultColumn = true)
	@Column(length = 64, nullable = false)
	private String userName;

	/** 节点步骤 */
	@FieldGenerator()
	@Column(nullable = false)
	private Byte step;

	/** 表单JSON */
	@Lob
	@FieldGenerator(resultColumn = true)
	@Column(nullable = false)
	private String formJson;

}
