package br.com.ifood.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {

	private static final String TASK_EXECUTOR_NAME_PREFIX_CONTROLLER = "controllerTaskExecutor-";
	private static final String TASK_EXECUTOR_NAME_PREFIX_SERVICE = "serviceTaskExecutor-";

	public static final String TASK_EXECUTOR_SERVICE = "serviceTaskExecutor";
	public static final String TASK_EXECUTOR_CONTROLLER = "controllerTaskExecutor";

	@Bean(name = TASK_EXECUTOR_SERVICE)
	public Executor getServiceAsyncExecutor() {
		return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_SERVICE);
	}

	@Bean(name = TASK_EXECUTOR_CONTROLLER)
	public Executor getControllerAsyncExecutor() {
		return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_CONTROLLER);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	private Executor newTaskExecutor(final String taskExecutorNamePrefix) {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(50);
		executor.setQueueCapacity(10000);
		executor.setThreadNamePrefix(taskExecutorNamePrefix);
		return executor;
	}
}