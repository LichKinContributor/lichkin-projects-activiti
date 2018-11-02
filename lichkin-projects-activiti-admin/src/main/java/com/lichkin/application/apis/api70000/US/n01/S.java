package com.lichkin.application.apis.api70000.US.n01;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lichkin.framework.db.beans.SysActivitiProcessConfigR;
import com.lichkin.framework.db.beans.SysActivitiProcessTaskConfigR;
import com.lichkin.framework.db.beans.UpdateSQL;
import com.lichkin.framework.defines.LKFrameworkStatics;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.enums.impl.LKUsingStatusEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.entities.impl.SysActivitiProcessConfigEntity;
import com.lichkin.springframework.entities.impl.SysActivitiProcessTaskConfigEntity;
import com.lichkin.springframework.services.LKApiBusUpdateUsingStatusService;

@Service("SysActivitiProcessConfigUS01Service")
public class S extends LKApiBusUpdateUsingStatusService<I, SysActivitiProcessConfigEntity> {

	@Override
	protected int getIdColumnResId() {
		return SysActivitiProcessConfigR.id;
	}


	@Override
	protected void beforeSaveMain(I sin, String locale, String compId, String loginId, SysActivitiProcessConfigEntity entity, String id) {
		switch (entity.getUsingStatus()) {
			case LOCKED:// 待配置表单
				entity.setUsingStatus(LKUsingStatusEnum.STAND_BY);
			case STAND_BY:// 待发布
				String[] taskIds = sin.getTaskId().split(LKFrameworkStatics.SPLITOR);
				String[] formJsons = sin.getFormJson().split(LKFrameworkStatics.SPLITOR);
				// 校验JSON
				for (int i = 0; i < formJsons.length; i++) {
					String formJson = formJsons[i];
					JsonNode jsonNode = LKJsonUtils.toJsonNode(formJson);
					if ((jsonNode == null) || !jsonNode.isArray()) {
						throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
					}
					formJsons[i] = jsonNode.toString();
				}
				// 修改表单配置JSON
				for (int i = 0; i < taskIds.length; i++) {
					UpdateSQL sql = new UpdateSQL(SysActivitiProcessTaskConfigEntity.class);
					sql.eq(SysActivitiProcessTaskConfigR.id, taskIds[i]);
					sql.set(SysActivitiProcessTaskConfigR.formJson, formJsons[i]);
					dao.update(sql);
				}
			break;
			default:
				throw new LKRuntimeException(LKErrorCodesEnum.PARAM_ERROR);
		}
	}

}
