package com.lichkin.springframework.services;

import com.lichkin.framework.beans.impl.LKRequestIDBean;
import com.lichkin.springframework.entities.suppers.ActivitiProcessEntity;

/**
 * 发起流程服务类
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public abstract class LKApiBusStartProcessService<SI extends LKRequestIDBean, E extends ActivitiProcessEntity> extends LKApiBusUpdateWithoutCheckerService<SI, E> {

}
