/**
 * file name : Response.java
 * created at : 5:52:33 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hx.bean.StatusLine;
import com.hx.util.Constants;
import com.hx.util.Tools;

// 响应
public class Response {

	// printWriter
	private PrintWriter out;
	private ByteArrayOutputStream buff;
	private StatusLine statusLine;
	private Map<String, String> responseHeader;
	private Socket clientSocket;
	
	// 初始化
	public Response() {
		statusLine = new StatusLine("HTTP/1.1", "200", "OK");
		responseHeader = new LinkedHashMap<>();
		responseHeader.put("Content-Type", "text/html; charset=utf-8");
		buff = new ByteArrayOutputStream(Constants.RESP_BUFF_CAP);
		out = new PrintWriter(buff);
	}
	
	// 初始化
	public void init() {

	}
	
	public void writeResponse() throws Exception {
		PrintWriter respOut = new PrintWriter(clientSocket.getOutputStream() );
		respOut.println(statusLine.toStatusLineString() );
		respOut.println(Tools.getHeaderString(responseHeader) );

		out.flush();
		out.close();
		respOut.println(buff.toString() );
		respOut.flush();
		respOut.close();
		clientSocket.close();
	}
	
	// setter & getter
	public PrintWriter getWriter() {
		return out;
	}
	public void addHeader(String key, String val) {
		responseHeader.put(key, val);
	}
	
	// 获取reponse
	public static Response parse(Socket socket) throws Exception {
		Response resp = new Response();
		resp.clientSocket = socket;
		
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
