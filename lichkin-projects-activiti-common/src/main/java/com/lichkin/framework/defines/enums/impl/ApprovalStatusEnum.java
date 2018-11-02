package com.lichkin.framework.defines.enums.impl;

import com.lichkin.framework.defines.enums.LKEnum;

/**
 * 审批状态枚举
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public enum ApprovalStatusEnum implements LKEnum {

	/** 待审批 */
	PENDING,

	/** 审批中 */
	HANDLING,

	/** 审批通过 */
	APPROVED,

	/** 驳回 */
	REJECT,

	;

}
