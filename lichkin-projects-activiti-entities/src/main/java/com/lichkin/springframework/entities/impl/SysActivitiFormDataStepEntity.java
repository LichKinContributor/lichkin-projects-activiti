package com.lichkin.springframework.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.lichkin.springframework.entities.suppers.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 表单数据步骤表实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysActivitiFormDataStepEntity extends BaseEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 70005L;

	/** 表单数据表ID（SysActivitiFormDataEntity.id） */
	@Column(nullable = false, length = 64)
	private String formDataId;

	/** 节点名称 */
	@Column(nullable = false, length = 64)
	private String taskName;

	/** 节点步骤 */
	@Column(nullable = false)
	private Byte step;

	/** 表单JSON */
	@Lob
	@Column(nullable = false)
	private String formJson;

	/** 数据JSON */
	@Lob
	@Column(nullable = false)
	private String dataJson;

}
