package com.lichkin.application.apis.ROOT.GetActivitiProcessConfigPage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class O {

	private String id;

	private String usingStatus;

	private String usingStatusDictCode;// for usingStatus

	private String insertTime;

	private String processKey;

	private String processKeyDictCode;// for processKey

	private String processType;

	private String processTypeDictCode;// for processType

	private String processCode;

	private String processName;

	private String platformType;

	private String platformTypeDictCode;// for processType

	/** 公司名称 */
	private String compName;

	/** 部门名称 */
	private String deptName;

}
