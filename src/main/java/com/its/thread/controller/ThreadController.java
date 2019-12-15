package com.its.thread.controller;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.its.thread.dic.ITSDictionary;
import com.its.thread.models.RequestModel;
import com.its.thread.models.RequestThreadModel;
import com.its.thread.models.ResponseModel;
import com.its.thread.utils.ITSMessageCode;

@CrossOrigin("*")
@RestController
@RequestMapping("/thread")
public class ThreadController {
	private static final Logger log = LoggerFactory.getLogger(ThreadController.class);
	@Autowired
	@Qualifier("requestCommandQueue")
	private LinkedBlockingQueue<RequestModel> requestCommandQueue = null;
	@Autowired
	private ITSDictionary dictionary = null;

	@PostMapping("/start")
	public ResponseModel startThread(@RequestBody RequestThreadModel request) {
		ResponseModel res = new ResponseModel();
		log.info("Have request start thread");
		try {
			RequestModel requestModel = new RequestModel();

			requestModel.setCommandName("START");
			requestModel.setEventDate(new Date());
			requestModel.setThreadId(request.getThreadId());
			requestCommandQueue.put(requestModel);
			res.setCode(ITSMessageCode.SUCCESSED_MESSAGE);
			res.setMessage("SUCCESSED");
		} catch (Exception exp) {
			res.setCode(ITSMessageCode.EXCEPTION_MESSAGE);
			res.setMessage("error - " + exp.getMessage());
			log.error("error start thread", exp);
		}
		log.info("Finished request start thread");
		return res;
	}

	@PostMapping("/stop")
	public ResponseModel stopThread(@RequestBody RequestThreadModel request) {
		ResponseModel res = new ResponseModel();
		log.info("Have request stop thread");
		try {
			RequestModel requestModel = new RequestModel();

			requestModel.setCommandName("STOP");
			requestModel.setEventDate(new Date());
			requestModel.setThreadId(request.getThreadId());
			requestCommandQueue.put(requestModel);
			res.setCode(ITSMessageCode.SUCCESSED_MESSAGE);
			res.setMessage("SUCCESSED");
		} catch (Exception exp) {
			res.setCode(ITSMessageCode.EXCEPTION_MESSAGE);
			res.setMessage("error - " + exp.getMessage());
			log.error("error stop thread", exp);
		}
		log.info("Finished request stop thread");
		return res;
	}

	@GetMapping("/load-all-thread")
	public ResponseModel loadAllThread() {
		ResponseModel res = new ResponseModel();
		log.info("Have request load all thread");
		try {
			res.setPayload(dictionary.loadAllThread());
			res.setCode(ITSMessageCode.SUCCESSED_MESSAGE);
			res.setMessage("SUCCESSED");
		} catch (Exception exp) {
			res.setCode(ITSMessageCode.EXCEPTION_MESSAGE);
			res.setMessage("error - " + exp.getMessage());
			log.error("error load all thread", exp);
		}
		log.info("Finished request load all thread");
		return res;
	}
}
