package com.lichkin.springframework.entities.suppers;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.lichkin.framework.defines.annotations.FieldGenerator;
import com.lichkin.framework.defines.annotations.InsertType;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 流程表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class ActivitiProcessEntity extends BaseCompEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 审批状态（枚举） */
	@Enumerated(EnumType.STRING)
	@FieldGenerator(resultColumn = true, insertType = InsertType.DEFAULT_DEFAULT, updateable = false)
	@Column(length = 9, nullable = false)
	private ApprovalStatusEnum approvalStatus;

	/** 审批通过时间（yyyyMMddHHmmssSSS） */
	@FieldGenerator(resultColumn = true, insertType = InsertType.DEFAULT_DEFAULT, updateable = false)
	@Column(length = 17)
	private String approvalTime;

}
