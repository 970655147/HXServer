/**
 * file name : Processor.java
 * created at : 4:51:55 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.core;

import java.io.IOException;
import java.net.Socket;

import com.hx.server.util.Constants;
import com.hx.server.util.Log;
import com.hx.server.util.Tools;

// ���������������������[dispatch]
public class Processor implements Runnable {
	
	// �ͻ��˷��͹�����socket, �ͻ��˵�req, ��Ҫ���͸��ͻ��˵�resp
	// ���ط�����������
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
	}

	// ��ʼ��
		// ����request, response ����ʼ��
	private void init() {
		try {
			req = Request.parse(clientSocket);
			resp = Response.parse(clientSocket);
			
			if(req != null) {
				req.init();
			}
			if(resp != null) {
				resp.init();
			}
		} catch (Exception e) {
			Tools.err(this, "error while init !");
			e.printStackTrace();
		}
	}
	
	// Override form Runnable	
	// 2015.10.15  ��init���õ�run������, ��������ά��Connector���߳� [�����²��ܽ�������request]
	@Override
	public void run() {
		init();
		process();
	}
	
	// ����ҵ��
		// finally д������, ���ر�clientSocket
	public void process() {
		try {
			processInternal();
		} catch (Exception e) {
			Tools.err(this, "error while process request or close socket !");
			e.printStackTrace();
		} finally {
			// д������
			try {
				resp.writeResponse();
			} catch (Exception e) {
				Tools.err(this, "error while write response's content !");
				e.printStackTrace();
			}
			try {
				clientSocket.close();
			} catch (IOException e) {
				Tools.err(this, "error while close the clientSocket !");
				e.printStackTrace();
			}
		}
	}
	
	// ����Ĵ���ҵ��
		// ��ӡ������־
		// ��ȫ���, ���� "���������.."
		// �жϵ�ǰ�����Ƿ����ĳ��webapp��  
			// ���û��, ����Ϊ���� /favicon.ico, ��λfaviconΪStaticSource�����favicon.ico StaticResourceLoader���������
				// ����   ����������
		// ����path�ĺ�׺�ж��Ƿ�Ϊ��̬��Դ
			// �����  ʹ��StaticResourceLoader�ҵ��þ�̬��Դ�����ͻؿͻ���
			// ����   ������Ӧ��servlet���������
	public void processInternal() throws Exception {
		if(req == null ) {
			Tools.log(this, "give up request : cause of not good request format !");
			return ;
		}
		
		Tools.log(this, "received request : " + req.getRequestLineStr() );
		if(! Tools.validateRequest(req) ) {
			Tools.notFound404(host, req, resp);
			return ;
		}
		
		// "method":"GET","path":"/favicon.ico","protocol":"HTTP/1.1" Ϊ������������ڵ�Сͼ��, ������
		int contextSlashIdx = req.getPath().indexOf("/", 1);
		if(contextSlashIdx < 0) {
			if(Constants.faviconPath.equals(req.getPath()) ) {
				req.setAttribute(Constants.CONTEXT, Constants.staticSource);
				req.setAttribute(Constants.PATH, Constants.favicon);
				StaticResourceLoader.load(host, req, resp);
			} else {
				Tools.log(this, "give up request : " + req.toString() + " cause of not specify webAppName !");
			}
			
			return ;			
		}
		
		// common business 
		String contextName = req.getPath().substring(1,  contextSlashIdx);
		String path = req.getPath().substring(contextSlashIdx+1);
		int questionIdx = req.getPath().indexOf("?");
		String reqSourceSuffix = null, realActionPath = path;
		if(questionIdx > 0) {
			realActionPath = path.substring(0, questionIdx);
		}
		reqSourceSuffix = Tools.DOT + Tools.getFileName(Tools.getFileName(path, Tools.INV_SLASH), Tools.DOT).trim();
		req.setAttribute(Constants.CONTEXT, contextName);
		req.setAttribute(Constants.PATH, path);
		
		if(Constants.staticSourceSuffix.contains(reqSourceSuffix) ) {
			StaticResourceLoader.load(host, req, resp);
		} else {
			ServletResourceLoader.load(host, req, resp);
		}
		
	}

}
