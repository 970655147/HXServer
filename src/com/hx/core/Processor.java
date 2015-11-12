/**
 * file name : Processor.java
 * created at : 4:51:55 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.IOException;
import java.net.Socket;

import com.hx.ext.FileBroseServlet;
import com.hx.util.Log;
import com.hx.util.Tools;

// 处理浏览器发送来的请求[dispatch]
public class Processor implements Runnable {
	
	// 客户端发送过来的socket
	private Socket clientSocket;
	protected Request req;
	protected Response resp;
	
	// servlet 处理业务
	private Servlet servlet = new FileBroseServlet();
	
	// 初始化
	public Processor() {
		super();
	}
	public Processor(Socket clientSocket) {
		this();
		this.clientSocket = clientSocket;
		init();
	}

	// 初始化
	private void init() {
		try {
			req = Request.parse(clientSocket);
			resp = Response.parse(clientSocket);
			
			req.init();
			resp.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		process();
	}
	
	// 处理业务
	public void process() {
		processInternal();
		try {
			clientSocket.close();
			Log.horizon();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 具体的处理业务
	public void processInternal() {
		if(req.getRequestLine() == null ) {
			Tools.log(this, "give up request : " + req.toString() + " cause of requestLine is : ' " + req.getRequestLineStr() + " ' !");
			return ;
		}
		
		if(Tools.equalsIgnoreCase(req.getMethod(), Request.GET) ) {
			servlet.doGet(req, resp);
		} else if(Tools.equalsIgnoreCase(req.getMethod(), Request.POST) ) {
			servlet.doPost(req, resp);
		} else if(Tools.equalsIgnoreCase(req.getMethod(), Request.PUT) ) {
			servlet.doPut(req, resp);
		} else if(Tools.equalsIgnoreCase(req.getMethod(), Request.DELETE) ) {
			servlet.doDelete(req, resp);
 		} else {
 			throw new RuntimeException("have no this request method !");
 		}
	}

}
