package com.lichkin.activiti.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.stereotype.Service;

import com.lichkin.activiti.beans.out.impl.LKActivitiGetFinishProcessOut;
import com.lichkin.activiti.services.LKActivitiCommonService;
import com.lichkin.framework.utils.LKBeanUtils;

/**
 * 获取已结束的流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Service
public class LKActivitiGetFinishProcessService extends LKActivitiCommonService {

	public List<LKActivitiGetFinishProcessOut> getFinishProcess(String dateTime) {
		List<HistoricProcessInstance> processList = historyService.createNativeHistoricProcessInstanceQuery().sql("SELECT * FROM " + managementService.getTableName(HistoricProcessInstance.class) + " T WHERE T.END_TIME_ >= '" + dateTime + "'").list();

		List<LKActivitiGetFinishProcessOut> outList = new ArrayList<>();
		for (HistoricProcessInstance processInfo : processList) {
			outList.add(LKBeanUtils.copyProperties(false, processInfo, new LKActivitiGetFinishProcessOut(processInfo.getSuperProcessInstanceId())));
		}
		return outList;
	}

}
