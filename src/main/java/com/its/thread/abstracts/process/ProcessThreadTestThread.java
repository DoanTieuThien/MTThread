package com.its.thread.abstracts.process;

import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.its.thread.abstracts.AbstractThreadBase;
import com.its.thread.models.ThreadInfoModel;

//thread test
public class ProcessThreadTestThread extends AbstractThreadBase{
	private static final Logger log = LoggerFactory.getLogger(ProcessThreadTestThread.class);

	public ProcessThreadTestThread(ThreadPoolExecutor threadPoolManager, String threadName,
			ThreadInfoModel threadInfoModel) {
		super(threadPoolManager, threadName, threadInfoModel);
	}

	@Override
	protected void init() throws Exception {
		
	}

	@Override
	protected void handle() throws Exception {
		log.info("Process thread test " + this.threadInfoModel.getThreadName());
	}

	@Override
	protected void end() throws Exception {
		
	}

}
