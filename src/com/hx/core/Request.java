/**
 * file name : Request.java
 * created at : 5:52:26 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hx.bean.RequestLine;
import com.hx.util.Tools;

// 请求
public class Request {

	// 请求行, 请求头 
	private RequestLine requestLine;
	private Map<String, String> requestHeader;
	private String requestLineStr;
	
	// 常量
	public final static String headerSep = Tools.COLON.toString();
	public final static String GET = "GET";
	public final static String POST = "POST";
	public final static String PUT = "PUT";
	public final static String DELETE = "PUT";

	// 初始化
	public Request() {
		requestHeader = new HashMap<>();
	}
	
	// 初始化
	public void init() {
		
	}
	
	// setter & getter
	public RequestLine getRequestLine() {
		return requestLine;
	}	
	public String getRequestLineStr() {
		return requestLineStr;
	}		
	public String getMethod() {
		return requestLine.method;
	}
	public String getPath() {
		return requestLine.path;
	}
	public String getProtocol() {
		return requestLine.protocol;
	}
	public String getHeader(String key) {
		return requestHeader.get(key);
	}
	
	// 解析请求
	public static Request parse(Socket socket) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()) );
		Request req = new Request();

		String line = br.readLine();
		if(! Tools.isEmpty(line) ) {
			req.requestLineStr = line;
			req.requestLine = RequestLine.parse(line);
		}
		while(((line = br.readLine()) != null) && (! Tools.isEmpty(line)) ) {
			int sepIdx = line.indexOf(headerSep);
			if(sepIdx > 0) {
				req.requestHeader.put(line.substring(0, sepIdx).trim(), line.substring(sepIdx+1).trim() );
			}
		}
		
		return req;
	}
	
	// for debug ..
	public String toString() {
		JSONObject res = new JSONObject();
		Tools.addIfNotEmpty(res, "requestLine", requestLine);	
		Tools.addIfNotEmpty(res, "requestHeader", requestHeader);
		
		return res.toString();
	}
	
}
