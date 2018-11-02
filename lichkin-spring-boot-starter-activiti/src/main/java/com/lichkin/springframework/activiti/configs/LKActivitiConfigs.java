package com.lichkin.springframework.activiti.configs;

import static com.lichkin.springframework.db.LKDBActivitiStatics.PLATFORM_TRANSACTION_MANAGER;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.lichkin.springframework.db.configs.LKDBActivitiConfigs;

/**
 * Activit配置
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
@Configuration
@EnableTransactionManagement
public class LKActivitiConfigs {

	@Bean
	@DependsOn(value = { PLATFORM_TRANSACTION_MANAGER })
	protected SpringProcessEngineConfiguration springProcessEngineConfiguration(LKDBActivitiConfigs configs) {
		SpringProcessEngineConfiguration conf = new SpringProcessEngineConfiguration();

		conf.setDataSource(configs.getDataSource());
		conf.setTransactionManager(configs.getPlatformTransactionManager());

		return conf;
	}


	@Bean
	public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration configuration) throws Exception {
		ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
		processEngineFactoryBean.setProcessEngineConfiguration(configuration);
		return processEngineFactoryBean;
	}


	@Bean
	@ConditionalOnMissingBean
	public RuntimeService runtimeServiceBean(ProcessEngine processEngine) {
		return processEngine.getRuntimeService();
	}


	@Bean
	@ConditionalOnMissingBean
	public RepositoryService repositoryServiceBean(ProcessEngine processEngine) {
		return processEngine.getRepositoryService();
	}


	@Bean
	@ConditionalOnMissingBean
	public TaskService taskServiceBean(ProcessEngine processEngine) {
		return processEngine.getTaskService();
	}


	@Bean
	@ConditionalOnMissingBean
	public HistoryService historyServiceBean(ProcessEngine processEngine) {
		return processEngine.getHistoryService();
	}


	@Bean
	@ConditionalOnMissingBean
	public ManagementService managementServiceBeanBean(ProcessEngine processEngine) {
		return processEngine.getManagementService();
	}


	@Bean
	@ConditionalOnMissingBean
	public FormService formServiceBean(ProcessEngine processEngine) {
		return processEngine.getFormService();
	}


	@Bean
	@ConditionalOnMissingBean
	public IdentityService identityServiceBean(ProcessEngine processEngine) {
		return processEngine.getIdentityService();
	}


	@Bean
	@ConditionalOnMissingBean
	public TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}

}
