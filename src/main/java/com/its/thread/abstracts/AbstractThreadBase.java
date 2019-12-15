package com.its.thread.abstracts;

import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.its.thread.models.ThreadInfoModel;

public abstract class AbstractThreadBase implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(AbstractThreadBase.class);
	private AtomicBoolean isBlocked = new AtomicBoolean(true);
	private String threadName = "ITS-";
	private String threadId = "";

	protected ThreadPoolExecutor threadPoolExecutor = null;
	protected ThreadInfoModel threadInfoModel = null;

	public AbstractThreadBase(ThreadPoolExecutor threadPoolManager, String threadName,
			ThreadInfoModel threadInfoModel) {
		this.threadPoolExecutor = threadPoolManager;
		this.threadName += threadName;
		this.threadInfoModel = threadInfoModel;
		threadId = UUID.randomUUID().toString();
	}

	public AbstractThreadBase(ThreadPoolExecutor threadPoolManager, String threadName) {
		this.threadPoolExecutor = threadPoolManager;
		this.threadName += threadName;
		this.threadInfoModel = threadInfoModel;
		threadId = UUID.randomUUID().toString();
	}

	public String getThreadName() {
		return this.threadName;
	}

	public boolean isThreadAlive() {
		return this.isBlocked.get() && !this.threadPoolExecutor.isShutdown();
	}

	public String threadId() {
		return this.threadId;
	}

	@Override
	public void run() {
		try {
			isBlocked.set(true);
			log.info("Start thread name " + this.threadName);
			while (isThreadAlive()) {
				try {
					init();
					handle();
				} catch (Exception exp) {
					log.error("error process thread", exp);
				} finally {
					try {
						end();
					} catch (Exception exp) {
						log.error("error end thread", exp);
					}
				}
				Thread.sleep(100);
			}
		} catch (Exception exp) {
			log.error("error thread", exp);
		}
		this.isBlocked.set(false);
		log.info("Thread name " + this.threadName + " is stopped");
	}

	public void unblockThread() {
		this.isBlocked.set(false);
	}

	// abstract
	protected abstract void init() throws Exception;

	protected abstract void handle() throws Exception;

	protected abstract void end() throws Exception;
}
