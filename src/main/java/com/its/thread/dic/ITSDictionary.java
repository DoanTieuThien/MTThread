package com.its.thread.dic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.its.thread.models.ThreadInfoModel;
import com.its.thread.utils.ITSException;
import com.its.thread.utils.ITSXMLDictionaryTagName;

public class ITSDictionary {
	private List<ThreadInfoModel> threadList = null;

	public ITSDictionary(String fileConfig) throws Exception {
		try {
			this.threadList = new ArrayList<ThreadInfoModel>();
			Document document = null;
			File fXmlFile = new File(fileConfig);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(fXmlFile);
			NodeList nodeList = document.getChildNodes();

			if (nodeList == null || nodeList.getLength() == 0) {
				throw new ITSException("ITS-0001", "File config " + fileConfig + " is not found any elements");
			}
			if (nodeList.getLength() != 1) {
				throw new ITSException("ITS-0002",
						"File config " + fileConfig + " is not true format, file config only has one element <xml>");
			}
			Node node = nodeList.item(0);
			String nodeName = Optional.ofNullable(node.getNodeName()).orElse("").trim();

			if (nodeName.equals("") || !nodeName.equals(ITSXMLDictionaryTagName.XML_TAGNAME)) {
				throw new ITSException("ITS-0002",
						"File config " + fileConfig + " is not true format, file config only has one element <xml>");
			}
			NodeList nodeListThreadManager = node.getOwnerDocument()
					.getElementsByTagName(ITSXMLDictionaryTagName.THREAD_MANAGER_TAGNAME);

			if (nodeListThreadManager == null || nodeListThreadManager.getLength() == 0) {
				throw new ITSException("ITS-0003", "File config " + fileConfig + " is not found element "
						+ ITSXMLDictionaryTagName.THREAD_MANAGER_TAGNAME);
			}
			if (nodeListThreadManager.getLength() != 1) {
				throw new ITSException("ITS-0004",
						"File config " + fileConfig + " is not true format, file config only has one element "
								+ ITSXMLDictionaryTagName.THREAD_MANAGER_TAGNAME);
			}
			Node nodeThreadManager = nodeListThreadManager.item(0);
			String nodeNameThreadManager = Optional.ofNullable(nodeThreadManager.getNodeName()).orElse("").trim();

			if (nodeNameThreadManager.equals("")
					|| !nodeNameThreadManager.equals(ITSXMLDictionaryTagName.THREAD_MANAGER_TAGNAME)) {
				throw new ITSException("ITS-0004",
						"File config " + fileConfig + " is not true format, file config only has one element "
								+ ITSXMLDictionaryTagName.THREAD_MANAGER_TAGNAME);
			}
			readAllThread(nodeThreadManager.getChildNodes());
		} catch (Exception exp) {
			throw exp;
		}
	}

	private void readAllThread(NodeList nodeList) {
		if (nodeList == null || nodeList.getLength() == 0) {
			return;
		}
		int length = nodeList.getLength();
		for (int i = 0; i < length; i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			String nodeName = Optional.ofNullable(node.getNodeName()).orElse("").trim();
			if (!ITSXMLDictionaryTagName.THREAD_TAGNAME.equals(nodeName)) {
				continue;
			}
			ThreadInfoModel threadInfoModel = new ThreadInfoModel();
			Document doc = node.getOwnerDocument();
			NodeList nodeThreadClasses = doc.getElementsByTagName(ITSXMLDictionaryTagName.THREAD_CLASS_TAGNAME);

			if (nodeThreadClasses.getLength() != 1) {
				continue;
			}
			Node nodeClass = nodeThreadClasses.item(0);
			String threadClass = Optional.ofNullable(nodeClass.getTextContent()).orElse("").trim();
			NamedNodeMap namedNodeMapThreadClass = node.getAttributes();

			if (namedNodeMapThreadClass != null) {
				threadInfoModel.setThreadName(namedNodeMapThreadClass.getNamedItem("name") != null ? Optional
						.ofNullable(namedNodeMapThreadClass.getNamedItem("name").getTextContent()).orElse("").trim()
						: "");
			}
			threadInfoModel.setThreadClass(threadClass);
			NodeList nodeStartupType = doc.getElementsByTagName(ITSXMLDictionaryTagName.THREAD_CLASS_TAGNAME);
			if (nodeThreadClasses.getLength() != 1) {
				continue;
			}
			Node nStartupType = nodeStartupType.item(0);
			String startupType = Optional.ofNullable(nStartupType.getNodeValue()).orElse("START").trim();
			threadInfoModel.setStartupType(startupType);

			NodeList nodeListParameters = doc.getElementsByTagName(ITSXMLDictionaryTagName.PARAMETERS_TAGNAME);
			HashMap parameters = new HashMap();
			int parametersSize = nodeListParameters.getLength();

			if (parametersSize == 1) {
				NodeList nodeParameters = nodeListParameters.item(0).getChildNodes();
				int paramSize = nodeParameters.getLength();
				
				for (int index = 0; index < paramSize; index++) {
					Node nParam = nodeParameters.item(index);
					if (nParam.getNodeType() != Node.ELEMENT_NODE) {
						continue;
					}
					NamedNodeMap namedNodeMap = nParam.getAttributes();

					if (namedNodeMap == null || namedNodeMap.getLength() < 1) {
						continue;
					}
					Node nName = namedNodeMap.getNamedItem("name");
					if (nName == null) {
						continue;
					}
					String paramName = Optional.ofNullable(nName.getNodeValue()).orElse("").trim();
					parameters.put(paramName, Optional.ofNullable(nParam.getTextContent()).orElse("").trim());
				}
			}
			threadInfoModel.setParameters(parameters);
			this.threadList.add(threadInfoModel);
		}
	}

	public List<ThreadInfoModel> loadAllThread() {
		return this.threadList;
	}
}
