package com.its.thread.abstracts.process;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.its.thread.abstracts.AbstractThreadBase;
import com.its.thread.dic.ITSDictionary;
import com.its.thread.models.RequestModel;
import com.its.thread.models.ThreadInfoModel;

public class MonitorServiceThread extends AbstractThreadBase {
	private static final Logger log = LoggerFactory.getLogger(MonitorServiceThread.class);

	@Autowired
	private ITSDictionary dictionary = null;
	@Autowired
	@Qualifier("requestCommandQueue")
	private LinkedBlockingQueue<RequestModel> requestCommandQueue = null;

	public MonitorServiceThread(ThreadPoolExecutor threadPoolManager, String threadName) {
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
				RequestModel request = requestCommandQueue.poll();

				if (request == null) {
					Thread.sleep(100);
					continue;
				}
				List<ThreadInfoModel> threadList = dictionary.loadAllThread();

				if (threadList == null || threadList.size() == 0) {
					Thread.sleep(100);
					continue;
				}

				String commandName = request.getCommandName();

				if ("START".equals(commandName)) {
					startThread(request.getThreadId(), threadList);
					log.info("Thread " + request.getThreadId() + " is start, please wait");
				} else if ("STOP".equals(commandName)) {
					stopThread(request.getThreadId(), threadList);
					log.info("Thread " + request.getThreadId() + " is stop, please wait");
				} else if ("CHANGE_PARAMETER".equals(commandName)) {

				} else {
					log.info("command " + commandName + " is not supported");
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

	// private
	private void startThread(String threadId, List<ThreadInfoModel> threadList) {
		for (ThreadInfoModel th : threadList) {
			if (th.getThread().threadId().equals(threadId)) {
				if (!"STARTED".equals(th.getThreadState()) && !"STARTING".equals(th.getPrepareState())
						&& !"STARTED".equals(th.getPrepareState())) {
					th.setStartupType("START");
					th.setThreadState("STOP");
					th.setPrepareState("STARTING");
					break;
				}
				break;
			}
		}
	}

	private void stopThread(String threadId, List<ThreadInfoModel> threadList) {
		for (ThreadInfoModel th : threadList) {
			String threadIdTemp = th.getThread().threadId();

			if (threadIdTemp.equals(threadId)) {
				if ("STARTED".equals(th.getThreadState())) {
					th.setStartupType("STOP");
					th.setThreadState("STOP");
					th.setPrepareState("STOPPED");
					th.getThread().unblockThread();
					break;
				}
				break;
			}
		}
	}
}
