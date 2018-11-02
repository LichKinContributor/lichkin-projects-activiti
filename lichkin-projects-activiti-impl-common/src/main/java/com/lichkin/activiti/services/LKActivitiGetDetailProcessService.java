package com.lichkin.activiti.services;

import java.util.List;

import com.lichkin.activiti.beans.in.LKActivitiGetDetailProcessIn;
import com.lichkin.activiti.beans.out.LKActivitiGetDetailProcessOut;

/**
 * 获取流程详情服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public interface LKActivitiGetDetailProcessService<GetDetailProcessIn extends LKActivitiGetDetailProcessIn, GetDetailProcessOut extends LKActivitiGetDetailProcessOut> {

	/**
	 * 获取流程详情
	 * @param in 入参
	 * @return 流程实例
	 */
	public List<GetDetailProcessOut> getDetailProcess(GetDetailProcessIn in);

}
