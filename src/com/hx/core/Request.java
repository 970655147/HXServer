/**
 * file name : Request.java
 * created at : 5:52:26 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hx.bean.RequestLine;
import com.hx.util.Tools;

// 请求
public class Request {

	// 请求行, 请求头 , 请求行的字符串表示, 当前request中的属性 [服务器内部传递数据]
	// 客户端输入流
	private RequestLine requestLine;
	private Map<String, String> requestHeader;
	private String requestLineStr;
	private Map<String, String> attri;
	private InputStream inputStream;
	
	// 常量
	public final static String headerSep = Tools.COLON.toString();
	public final static String GET = "GET";
	public final static String POST = "POST";
	public final static String PUT = "PUT";
	public final static String DELETE = "PUT";

	// 初始化
	public Request() {
		requestHeader = new HashMap<>();
		attri = new HashMap<>();
	}
	
	// 初始化
	public void init() {
		
	}
	
	// setter & getter
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
	public void setAttribute(String key, String val) {
		attri.put(key, val);
	}
	public String getAttribute(String key) {
		return attri.get(key);
	}
	public String getParameters() {
		return requestLine.params.toString();
	}
	public String getParameter(String key) {
		return requestLine.params.get(key);
	}
	public InputStream getInputStream() {
		return inputStream;
	}	
	
	// 解析请求
	// 解析请求行, 请求头
		// 如果是post请求话 解析请求数据
	public static Request parse(Socket socket) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()) );
		Request req = new Request();

		// ---------------- read post data----------------------
		// refer : http://bbs.csdn.net/topics/310107460
//		String line = null;
//		int bodyCnt = 0;
//		while(((line = br.readLine()) != null) && (! Tools.isEmpty(line)) )  {
//			Log.log(line) ;
//			if(line.startsWith("Content-Length") ) {
//				bodyCnt = Integer.parseInt(line.substring(line.indexOf(":")+1).trim() );
//			}
//		}
//		
//		Log.log(bodyCnt);
//		byte[] buff = new byte[bodyCnt];
//		for(int i=0; i<bodyCnt; i++) {
//			buff[i] = (byte) br.read();
//		}
//		Log.log(new String(buff, 0, bodyCnt) );
//		Log.horizon();
		
		// ---------------- business ----------------------
		String line = br.readLine();
		if(! Tools.isEmpty(line) ) {
			req.requestLineStr = line;
			req.requestLine = RequestLine.parse(line);
		}
		if(req.requestLine == null) {
			return null;
		}
		
		while(((line = br.readLine()) != null) && (! Tools.isEmpty(line)) ) {
			int sepIdx = line.indexOf(headerSep);
			if(sepIdx > 0) {
				req.requestHeader.put(line.substring(0, sepIdx).trim(), line.substring(sepIdx+1).trim() );
			}
		}	
		// parse posted data !
		if(Request.POST.equals(req.getMethod()) ) {
			int bodyCnt = 0;
			if(req.requestHeader.get(Tools.CONTENT_LENGTH) != null) {
				bodyCnt = Integer.parseInt(req.requestHeader.get(Tools.CONTENT_LENGTH) );
			}
			byte[] buff = new byte[bodyCnt];
			// doesn't work !
	//		socketIs.read(buff, 0, bodyCnt);
			// it works !
			for(int i=0; i<bodyCnt; i++) {
				buff[i] = (byte) br.read();
			}
			String postedData = new String(buff, 0, bodyCnt);
			req.requestLine.parseParams(postedData, req.requestLine.params, true);
		}
		
		req.inputStream = socket.getInputStream();
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
