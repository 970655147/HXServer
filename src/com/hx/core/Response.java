/**
 * file name : Response.java
 * created at : 5:52:33 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hx.bean.StatusLine;
import com.hx.util.Tools;

// 响应
public class Response {

	// printWriter
	private PrintWriter out;
	private StatusLine statusLine;
	private Map<String, String> responseHeader;
	
	// 初始化
	public Response() {
		statusLine = new StatusLine("HTTP/1.1", "200", "OK");
		responseHeader = new LinkedHashMap<>();
		responseHeader.put("Content-Type", "text/html; charset=UTF-8");
	}
	
	// 初始化
	public void init() {
		PrintWriter out = getWriter();
		out.println(statusLine.toStatusLineString() );
		out.println(Tools.getHeaderString(responseHeader) );
	}
	
	// setter & getter
	public PrintWriter getWriter() {
		return out;
	}
	
	// 获取reponse
	public static Response parse(Socket socket) throws Exception {
		Response resp = new Response();
		resp.out = new PrintWriter(socket.getOutputStream() );
		
		return resp;
	}
	
	// for debug ..
	public String toString() {
		JSONObject res = new JSONObject();
		Tools.addIfNotEmpty(res, "requestLine", statusLine);	
		Tools.addIfNotEmpty(res, "requestHeader", responseHeader);
		
		return res.toString();
	}
	
}
