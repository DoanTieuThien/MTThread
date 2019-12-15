package com.its.thread.abstracts.process;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.its.thread.abstracts.AbstractThreadBase;
import com.its.thread.dic.ITSDictionary;
import com.its.thread.models.ThreadInfoModel;

public class ManagerServiceThread extends AbstractThreadBase {
	private static final Logger log = LoggerFactory.getLogger(ManagerServiceThread.class);

	@Autowired
	private ITSDictionary dictionary = null;

	public ManagerServiceThread(ThreadPoolExecutor threadPoolManager, String threadName) {
		super(threadPoolManager, threadName);
	}

	@Override
	protected void init() throws Exception {
		if (this.dictionary == null) {
			throw new Exception("dictionary is null");
		}
	}

	@Override
	protected void handle() throws Exception {
		while (isThreadAlive()) {
			try {
				List<ThreadInfoModel> threadInfoList = dictionary.loadAllThread();

				if (threadInfoList == null || threadInfoList.size() == 0) {
					log.info("No any thread is found");
					Thread.sleep(100);
					continue;
				}

				for (ThreadInfoModel threadInfoModel : threadInfoList) {
					try {
						String threadState = Optional.ofNullable(threadInfoModel.getThreadState()).orElse("").trim();
						if ("STARTED".equals(threadState)) {
							continue;
						}
						String startupType = Optional.ofNullable(threadInfoModel.getStartupType()).orElse("START")
								.trim();
						if (!"START".equals(startupType)) {
							continue;
						}

						String className = threadInfoModel.getThreadClass();
						threadInfoModel.setThreadState("PENDING");

						AbstractThreadBase threadBase = threadInfoModel.getThread();

						if (threadBase != null) {
							threadPoolExecutor.execute(threadBase);
						} else {
							if ("".equals(className)) {
								threadInfoModel.setThreadState("ERROR");
								throw new Exception("Class name " + className + " is not found for thread "
										+ threadInfoModel.getThreadName());
							}
							Class c = Class.forName(className);
							AbstractThreadBase thread = (AbstractThreadBase) c
									.getConstructor(ThreadPoolExecutor.class, String.class, ThreadInfoModel.class)
									.newInstance(this.threadPoolExecutor, threadInfoModel.getThreadName(),
											threadInfoModel);
							threadInfoModel.setThread(thread);
							threadPoolExecutor.execute(thread);
						}
						threadInfoModel.setThreadState("STARTED");
						threadInfoModel.setPrepareState("STARTED");
						log.info("Thread " + threadInfoModel.getThreadName() + " is started");
					} catch (Exception exp) {
						threadInfoModel.setMessage(exp.getMessage());
						log.error("error start thread " + threadInfoModel.getThreadName(), exp);
					}
				}
			} catch (Exception exp) {
				log.error("error process thread " + this.getThreadName(), exp);
			}
			Thread.sleep(100);
		}
	}

	@Override
	protected void end() throws Exception {

	}

}
