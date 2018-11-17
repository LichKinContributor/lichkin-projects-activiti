package com.lichkin.activiti.GetDoneProcess;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lichkin.activiti.beans.in.impl.LKActivitiGetDoneProcessIn;
import com.lichkin.activiti.beans.out.impl.LKActivitiGetDoneProcessOut;
import com.lichkin.activiti.services.impl.LKActivitiGetDoneProcessService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

@Service(Statics.SERVICE_NAME)
public class S extends LKDBService implements LKApiService<I, List<O>> {

	@Override
	@Transactional
	public List<O> handle(I sin, String locale, String compId, String loginId) throws LKException {
		// TODO 优化
		List<O> outList = new ArrayList<>();
		List<O> list = getDoneProcess(sin);
		if (CollectionUtils.isNotEmpty(list)) {
			List<String> processInstanceIdList = new ArrayList<>();
			for (O out : list) {
				processInstanceIdList.add(out.getProcessInstanceId());
			}
			QuerySQL sql = new QuerySQL(false, SysActivitiFormDataEntity.class);
			sql.in(SysActivitiFormDataR.processInstanceId, processInstanceIdList);
			List<SysActivitiFormDataEntity> formDataList = dao.getList(sql, SysActivitiFormDataEntity.class);
			// 只展示发起成功的数据，异常数据不显示
			if (CollectionUtils.isNotEmpty(formDataList)) {
				for (O out : list) {
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
	private LKActivitiGetDoneProcessService service;


	/**
	 * 获取已办流程
	 * @param in 入参
	 * @return 已办流程列表
	 */
	private List<O> getDoneProcess(I in) {
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
		List<O> outList = new ArrayList<>();
		for (LKActivitiGetDoneProcessOut task : taskList) {
			outList.add(LKBeanUtils.newInstance(false, task, O.class));
		}
		// 返回结果
		return outList;
	}

}
