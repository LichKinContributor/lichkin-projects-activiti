package com.lichkin.springframework.entityA.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * 连接实体类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Getter
@Setter
@Entity
public class SysActivitiConnectorEntity {

	@Id
	@GenericGenerator(name = "lichkin", strategy = "com.lichkin.springframework.db.entities.LKIdentifierGenerator")
	@GeneratedValue(generator = "lichkin")
	@Column(length = 64)
	private String id;

}
