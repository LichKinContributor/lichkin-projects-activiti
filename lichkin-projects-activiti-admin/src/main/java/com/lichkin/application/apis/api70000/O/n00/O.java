package com.lichkin.application.apis.api70000.O.n00;

import com.lichkin.framework.defines.enums.impl.PlatformTypeEnum;
import com.lichkin.framework.defines.enums.impl.ProcessKeyEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	// private LKUsingStatusEnum usingStatus;

	// private String insertTime;

	private String compId;

	private PlatformTypeEnum platformType;

	private ProcessKeyEnum processKey;

	private ProcessTypeEnum processType;

	private String processCode;

	private String processName;

	private String deptId;

	private Byte stepCount;

	// private Boolean available;

}
