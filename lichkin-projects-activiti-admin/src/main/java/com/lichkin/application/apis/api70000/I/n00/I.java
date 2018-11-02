package com.lichkin.application.apis.api70000.I.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;
import com.lichkin.framework.defines.enums.impl.ProcessKeyEnum;
import com.lichkin.framework.defines.enums.impl.ProcessTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String compId;

	private ProcessKeyEnum processKey;

	private ProcessTypeEnum processType;

	private String processCode;

	private String processName;

	private String deptId;

	private Byte stepCount;

}
