package com.lichkin.springframework.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lichkin.application.services.WithoutActivitiCallbackService;
import com.lichkin.application.services.impl.ActivitiStartProcessService;
import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.framework.defines.LKConfigStatics;
import com.lichkin.framework.defines.enums.impl.ApprovalStatusEnum;
import com.lichkin.framework.json.LKJsonUtils;
import com.lichkin.springframework.configs.LKApplicationContext;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

/**
 * 发起流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKApiBusStartProcessService<SI extends LKRequestIDBean, E extends ActivitiProcessEntity> extends LKApiBusUpdateWithoutCheckerService<SI, E> {

	@Autowired
	private ActivitiStartProcessService activitiStartProcessService;


	/**
	 * 发起流程需要传递的参数
	 * @param sin 入参
	 * @param locale 国际化
	 * @param compId 公司ID
	 * @param loginId 登录ID
	 * @param entity 保存前的实体类对象
	 * @param datas 传递的参数（直接在此参数中设置）
	 */
	protected abstract void initFormData(SI sin, String locale, String compId, String loginId, E entity, Map<String, Object> datas);


	/**
	 * 获取流程编码
	 * @param sin 入参
	 * @param locale 国际化
	 * @param compId 公司ID
	 * @param loginId 登录ID
	 * @param entity 保存前的实体类对象
	 * @return 流程编码
	 */
	protected abstract String getProcessCode(SI sin, String locale, String compId, String loginId, E entity);


	@Deprecated
	@Override
	protected boolean busCheck(SI sin, String locale, String compId, String loginId, E entity, String id) {
		return true;
	}


	@Deprecated
	@Override
	protected void beforeCopyProperties(SI sin, String locale, String compId, String loginId, E entity) {
	}


	@Deprecated
	@Override
	protected void beforeSaveMain(SI sin, String locale, String compId, String loginId, E entity) {
		Map<String, Object> datas = new HashMap<>();
		// 必填
		datas.put("id", entity.getId());
		// 初始化表单数据
		initFormData(sin, locale, compId, loginId, entity, datas);
		// 发起流程
		switch (LKConfigStatics.PLATFORM) {
			case ADMIN:
				activitiStartProcessService.startByAdmin(entity, compId, getProcessCode(sin, locale, compId, loginId, entity), loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
			break;
			case EMPLOYEE:
				activitiStartProcessService.startByEmployee(entity, compId, sin.getDatas().getDeptId(), getProcessCode(sin, locale, compId, loginId, entity), loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
			break;
			case USER:
				activitiStartProcessService.startByUser(entity, getProcessCode(sin, locale, compId, loginId, entity), loginId, sin.getDatas().getUser().getUserName(), LKJsonUtils.toJson(datas));
			break;
		}
	}


	@SuppressWarnings("unchecked")
	@Deprecated
	@Override
	protected void afterSaveMain(SI sin, String locale, String compId, String loginId, E entity, String id) {
		if (ApprovalStatusEnum.APPROVED.equals(entity.getApprovalStatus())) {
			((WithoutActivitiCallbackService<E>) LKApplicationContext.getBean(getProcessCode(sin, locale, compId, loginId, entity))).directFinish(entity, compId, loginId);
		}
	}


	@Deprecated
	@Override
	protected void clearSubs(SI sin, String locale, String compId, String loginId, E entity, String id) {
	}


	@Deprecated
	@Override
	protected void addSubs(SI sin, String locale, String compId, String loginId, E entity, String id) {
	}

}
