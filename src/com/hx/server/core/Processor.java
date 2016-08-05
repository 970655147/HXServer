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

// 处理浏览器发送来的请求[dispatch]
public class Processor implements Runnable {
	
	// 客户端发送过来的socket, 客户端的req, 需要发送个客户端的resp
	// 本地服务器的容器
	private Socket clientSocket;
	protected Request req;
	protected Response resp;
	private Host host;
	
	// 初始化
	public Processor() {
		this(null, null);
	}
	public Processor(Socket clientSocket, Host host) {
		this.clientSocket = clientSocket;
		this.host = host;
	}

	// 初始化
		// 解析request, response 并初始化
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
	// 2015.10.15  将init放置到run方法中, 避免阻塞维护Connector的线程 [而导致不能接受其他request]
	@Override
	public void run() {
		init();
		process();
	}
	
	// 处理业务
		// finally 写出数据, 并关闭clientSocket
	public void process() {
		try {
			processInternal();
		} catch (Exception e) {
			Tools.err(this, "error while process request or close socket !");
			e.printStackTrace();
		} finally {
			// 写出数据
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
	
	// 具体的处理业务
		// 打印请求日志
		// 安全检查, 例如 "不允许出现.."
		// 判断当前请求是否存在某个webapp中  
			// 如果没有, 并且为请求 /favicon.ico, 定位favicon为StaticSource下面的favicon.ico StaticResourceLoader处理该请求
				// 否则   放弃该请求
		// 根据path的后缀判断是否为静态资源
			// 如果是  使用StaticResourceLoader找到该静态资源并发送回客户端
			// 否则   加载相应的servlet处理该请求
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
		
		// "method":"GET","path":"/favicon.ico","protocol":"HTTP/1.1" 为请求浏览器窗口的小图标, 不请求
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
