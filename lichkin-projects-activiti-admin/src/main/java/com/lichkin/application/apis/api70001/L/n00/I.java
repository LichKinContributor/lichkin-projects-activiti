package com.lichkin.application.apis.api70001.L.n00;

import com.lichkin.framework.beans.impl.LKRequestBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class I extends LKRequestBean {

	private String configId;

	/** 是否排除第一步 */
	private boolean withoutFirst;

}
