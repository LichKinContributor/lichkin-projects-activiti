package com.lichkin.application.apis.ROOT.GetActivitiProcessConfig;

import com.lichkin.framework.defines.enums.LKPlatform;
import com.lichkin.framework.defines.enums.impl.ProcessKeyEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private String compId;

	private LKPlatform platformType;

	private ProcessKeyEnum processKey;

	private ProcessTypeEnum processType;

	private String processCode;

	private String processName;

	private String deptId;

	private Byte stepCount;

}
