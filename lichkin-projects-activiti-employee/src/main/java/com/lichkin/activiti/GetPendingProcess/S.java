package com.lichkin.activiti.GetPendingProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.in.impl.LKActivitiGetPendingProcessIn;
import com.lichkin.activiti.beans.out.impl.LKActivitiGetPendingProcessOut;
import com.lichkin.activiti.services.impl.LKActivitiGetPendingProcessService;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiFormDataR;
import com.lichkin.framework.defines.exceptions.LKException;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.framework.utils.LKListUtils;
import com.lichkin.springframework.controllers.ApiKeyValues;
import com.lichkin.springframework.entities.impl.SysActivitiFormDataEntity;
import com.lichkin.springframework.services.LKApiService;
import com.lichkin.springframework.services.LKDBService;

@Service(Statics.SERVICE_NAME)
public class S extends LKDBService implements LKApiService<I, List<O>> {

	@Override
	public List<O> handle(I sin, ApiKeyValues<I> params) throws LKException {
		List<O> list = getPendingProcess(sin, params.getCompId() + "_" + params.getUser().getId());
		if (CollectionUtils.isNotEmpty(list)) {
			QuerySQL sql = new QuerySQL(false, SysActivitiFormDataEntity.class);
			sql.in(SysActivitiFormDataR.processInstanceId, LKListUtils.convert(list, source -> source.getProcessInstanceId()));
			List<SysActivitiFormDataEntity> formDataList = dao.getList(sql, SysActivitiFormDataEntity.class);

			// 只展示发起成功的数据，异常数据不显示
			if (CollectionUtils.isNotEmpty(formDataList)) {
				List<O> outList = new ArrayList<>();
				first: for (O out : list) {
					for (O o : outList) {
						if (out.getProcessInstanceId().equals(o.getProcessInstanceId())) {
							continue first;
						}
					}
					for (SysActivitiFormDataEntity formData : formDataList) {
						if (out.getProcessInstanceId().equals(formData.getProcessInstanceId())) {
							out.setProcessCode(formData.getProcessCode());
							outList.add(out);
							break;
						}
					}
				}
				return outList;
			}
		}

		return Collections.emptyList();
	}


	@Autowired
	private LKActivitiGetPendingProcessService service;


	private List<O> getPendingProcess(I in, String userId) {
		// 初始化入参
		LKActivitiGetPendingProcessIn i = new LKActivitiGetPendingProcessIn(userId);

		// 调用服务类方法
		List<LKActivitiGetPendingProcessOut> taskList = service.getPendingProcess(i);

		// 返回结果
		return LKListUtils.convert(taskList, task -> LKBeanUtils.newInstance(false, task, O.class));
	}

}
