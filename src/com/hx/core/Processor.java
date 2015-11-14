/**
 * file name : Processor.java
 * created at : 4:51:55 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.net.Socket;

import com.hx.util.Constants;
import com.hx.util.Log;
import com.hx.util.Tools;

// ���������������������[dispatch]
public class Processor implements Runnable {
	
	// �ͻ��˷��͹�����socket
	private Socket clientSocket;
	protected Request req;
	protected Response resp;
	private Host host;
	
	// ��ʼ��
	public Processor() {
		this(null, null);
	}
	public Processor(Socket clientSocket, Host host) {
		this.clientSocket = clientSocket;
		this.host = host;
		init();
	}

	// ��ʼ��
	private void init() {
		try {
			req = Request.parse(clientSocket);
			resp = Response.parse(clientSocket);
			
			req.init();
			resp.init();
		} catch (Exception e) {
			Tools.err(this, "error while init !");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		process();
	}
	
	// ����ҵ��
	public void process() {
		try {
			processInternal();
			clientSocket.close();
		} catch (Exception e) {
			Tools.err(this, "error while process request or close socket !");
			e.printStackTrace();
		}
	}
	
	// ����Ĵ���ҵ��
	public void processInternal() throws Exception {
		if(req.getRequestLine() == null ) {
			Tools.log(this, "give up request : " + req.toString() + " cause of requestLine is : ' " + req.getRequestLineStr() + " ' !");
			return ;
		}
		
		int contextSlashIdx = req.getPath().indexOf("/", 1);
		if(contextSlashIdx < 0) {
			Tools.log(this, "give up request : " + req.toString() + " cause of not specify webAppName !");
			return ;			
		}
		
		String contextName = req.getPath().substring(1,  contextSlashIdx);
		String path = req.getPath().substring(contextSlashIdx+1);
		String reqSourceSuffix = Tools.DOT + Tools.getFileName(Tools.getFileName(path, Tools.INV_SLASH), Tools.DOT).trim();
		req.setAttribute(Constants.CONTEXT, contextName);
		req.setAttribute(Constants.PATH, path);
		
		if(Constants.staticSourceSuffix.contains(reqSourceSuffix) ) {
			StaticResourceLoader.load(host, req, resp);
		} else {
			ServletResourceLoader.load(host, req, resp);
		}
		
		// д������
		resp.writeResponse();
	}

}
