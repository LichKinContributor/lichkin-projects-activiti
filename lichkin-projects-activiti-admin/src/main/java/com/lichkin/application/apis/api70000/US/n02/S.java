package com.lichkin.application.apis.api70000.US.n02;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lichkin.framework.db.beans.Order;
import com.lichkin.framework.db.beans.QuerySQL;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.framework.utils.LKBeanUtils;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysActivitiProcessConfigUS02Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysActivitiProcessConfigEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysActivitiProcessConfigR.id;
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		switch (entity.getUsingStatus()) {
			case USING:// 已发布
				// 校验JSON
				String[] formJsons = sin.getFormJson().split(LKFrameworkStatics.SPLITOR);
				for (int i = 0; i < formJsons.length; i++) {
					String formJson = formJsons[i];
					JsonNode jsonNode = LKJsonUtils.toJsonNode(formJson);
					if ((jsonNode == null) || !jsonNode.isArray()) {
						throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
					}
					formJsons[i] = jsonNode.toString();
				}

				// 创建新的配置
				SysActivitiProcessConfigEntity newEntity = LKBeanUtils.newInstance(entity, SysActivitiProcessConfigEntity.class, "id");
				newEntity.setUsingStatus(LKUsingStatusEnum.USING);
				dao.persistOne(newEntity);
				String newId = newEntity.getId();

				// 创建新的子配置
				QuerySQL sql = new QuerySQL(false, SysActivitiProcessTaskConfigEntity.class);
				sql.eq(SysActivitiProcessTaskConfigR.configId, id);
				sql.addOrders(new Order(SysActivitiProcessTaskConfigR.step));
				List<SysActivitiProcessTaskConfigEntity> listTask = dao.getList(sql, SysActivitiProcessTaskConfigEntity.class);
				List<SysActivitiProcessTaskConfigEntity> newListTask = new ArrayList<>(listTask.size());
				for (int i = 0; i < listTask.size(); i++) {
					SysActivitiProcessTaskConfigEntity newTask = LKBeanUtils.newInstance(listTask.get(i), SysActivitiProcessTaskConfigEntity.class, "id");
					newTask.setConfigId(newId);
					newTask.setFormJson(formJsons[i]);
					newListTask.add(newTask);
				}
				dao.persistList(newListTask);
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
