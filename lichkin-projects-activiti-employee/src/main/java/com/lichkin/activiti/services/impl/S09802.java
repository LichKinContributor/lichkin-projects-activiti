package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.I09802;
import com.lichkin.activiti.beans.in.impl.LKActivitiGetDoneProcessIn;
import com.lichkin.activiti.beans.out.impl.LKActivitiGetDoneProcessOut;
import com.lichkin.activiti.beans.out.impl.O09802;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

/**
 * 获取已办流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class S09802 extends LKDBService implements LKApiService<I09802, List<O09802>> {

	@Override
	@Transactional
	public List<O09802> handle(I09802 sin, String locale, String compId, String loginId) throws LKException {
		List<O09802> outList = new ArrayList<>();
		List<O09802> list = getDoneProcess(sin);
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> processInstanceIdList = new ArrayList<>();
			for (O09802 out : list) {
				processInstanceIdList.add(out.getProcessInstanceId());
			}
			QuerySQL sql = new QuerySQL(SysActivitiFormDataEntity.class);
			sql.in(SysActivitiFormDataR.processInstanceId, processInstanceIdList);
			List<SysActivitiFormDataEntity> formDataList = dao.getList(sql, SysActivitiFormDataEntity.class);
			// 只展示发起成功的数据，异常数据不显示
			if (CollectionUtils.isNotEmpty(formDataList)) {
				for (O09802 out : list) {
					for (SysActivitiFormDataEntity formData : formDataList) {
						if (out.getProcessInstanceId().equals(formData.getProcessInstanceId())) {
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
	private LKActivitiGetDoneProcessService service;


	/**
	 * 获取已办流程
	 * @param in 入参
	 * @return 已办流程列表
	 */
	private List<O09802> getDoneProcess(I09802 in) {
		// 初始化入参
		LKActivitiGetDoneProcessIn i = new LKActivitiGetDoneProcessIn(in.getUserId());
		if (in.getPageNumber() == null) {
			in.setPageNumber(0);
		}
		if (in.getPageSize() == null) {
			in.setPageSize(50);
		}
		i.setFirstResult(in.getPageNumber() * in.getPageSize());
		i.setMaxResults(in.getPageSize());

		// 调用服务类方法
		List<LKActivitiGetDoneProcessOut> taskList = service.getDoneProcess(i);

		// 初始化出参
		List<O09802> outList = new ArrayList<>();
		for (LKActivitiGetDoneProcessOut task : taskList) {
			outList.add(LKBeanUtils.newInstance(false, task, O09802.class));
		}
		// 返回结果
		return outList;
	}

}
