package com.its.thread.config;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.its.thread.abstracts.AbstractThreadBase;
import com.its.thread.abstracts.process.ManagerServiceThread;
import com.its.thread.abstracts.process.MonitorServiceThread;
import com.its.thread.dic.ITSDictionary;
import com.its.thread.models.RequestModel;

@Configuration
public class ITSThreadConfiguration {

	@Value("${com.its.app.thread-config-file}")
	private String threadConfigFile = "";

	@Bean("threadConfig")
	public ITSDictionary threadConfig() throws Exception {
		ITSDictionary itsDictionary = new ITSDictionary(threadConfigFile);
		return itsDictionary;
	}

	@Bean("managerThreadPool")
	public ThreadPoolExecutor managerThreadPool() throws Exception {
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

		threadPoolExecutor.execute(monitorServiceThread(threadPoolExecutor));
		threadPoolExecutor.execute(managerServiceThread(threadPoolExecutor));
		return threadPoolExecutor;
	}

	@Bean("monitorServiceThread")
	public AbstractThreadBase monitorServiceThread(ThreadPoolExecutor threadPoolExecutor) {
		MonitorServiceThread monitorServiceThread = new MonitorServiceThread(threadPoolExecutor,
				"MonitorServiceThread");
		return monitorServiceThread;
	}

	@Bean("managerServiceThread")
	public AbstractThreadBase managerServiceThread(ThreadPoolExecutor threadPoolExecutor) {
		ManagerServiceThread managerServiceThread = new ManagerServiceThread(threadPoolExecutor,
				"ManagerServiceThread");
		return managerServiceThread;
	}

	@Bean("requestCommandQueue")
	public LinkedBlockingQueue<RequestModel> requestCommandQueue() {
		return new LinkedBlockingQueue<RequestModel>();
	}
}
