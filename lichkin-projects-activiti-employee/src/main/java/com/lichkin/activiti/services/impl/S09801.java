package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.in.impl.I09801;
import com.lichkin.activiti.beans.in.impl.LKActivitiGetPendingProcessIn;
import com.lichkin.activiti.beans.out.impl.LKActivitiGetPendingProcessOut;
import com.lichkin.activiti.beans.out.impl.O09801;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

/**
 * 获取待办流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09801 extends LKDBService implements LKApiService<I09801, List<O09801>> {

	@Override
	public List<O09801> handle(I09801 sin, String locale, String compId, String loginId) throws LKException {
		// TODO 优化
		List<O09801> outList = new ArrayList<>();
		List<O09801> list = getPendingProcess(sin);
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> processInstanceIdList = new ArrayList<>();
			for (O09801 out : list) {
				processInstanceIdList.add(out.getProcessInstanceId());
			}
			QuerySQL sql = new QuerySQL(false, SysActivitiFormDataEntity.class);
			sql.in(SysActivitiFormDataR.processInstanceId, processInstanceIdList);
			List<SysActivitiFormDataEntity> formDataList = dao.getList(sql, SysActivitiFormDataEntity.class);
			// 只展示发起成功的数据，异常数据不显示
			if (CollectionUtils.isNotEmpty(formDataList)) {
				for (O09801 out : list) {
					for (SysActivitiFormDataEntity formData : formDataList) {
						if (out.getProcessInstanceId().equals(formData.getProcessInstanceId())) {
							out.setProcessCode(formData.getProcessCode());
							outList.add(out);
							break;
						}
					}
				}
			}
		}

		return outList;
	}


	@Autowired
	private LKActivitiGetPendingProcessService service;


	/**
	 * 获取待办流程
	 * @param in 入参
	 * @return 待办流程列表
	 */
	private List<O09801> getPendingProcess(I09801 in) {
		// 初始化入参
		LKActivitiGetPendingProcessIn i = new LKActivitiGetPendingProcessIn(in.getUserId());

		// 调用服务类方法
		List<LKActivitiGetPendingProcessOut> taskList = service.getPendingProcess(i);

		// 初始化出参
		List<O09801> outList = new ArrayList<>();
		for (LKActivitiGetPendingProcessOut out : taskList) {
			outList.add(LKBeanUtils.newInstance(false, out, O09801.class));
		}
		// 返回结果
		return outList;
	}

}
