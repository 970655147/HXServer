/**
 * file name : RequestHeader.java
 * created at : 7:40:51 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.bean;

import net.sf.json.JSONObject;

import com.hx.util.Tools;

// 请求行
public class RequestLine {

	// 请求方法, 路径, 协议
	public String method;
	public String path;
	public String protocol;
	
	// 解析请求行
	public static RequestLine parse(String requestLine) {
		Tools.log(RequestLine.class, requestLine );
		if(Tools.isEmpty(requestLine) ) {
			return null;
		}
		
		String[] splits = requestLine.split("\\s+");
		if(splits.length != 3) {
			return null;
		}
		
		RequestLine rLine = new RequestLine();
		rLine.method = splits[0];
		rLine.path = splits[1];
		rLine.protocol = splits[2];
		
		return rLine;
	}
	
	// for debug ..
	public String toString() {
		JSONObject res = new JSONObject();
		res.element("method", method);
		res.element("path", path);
		res.element("protocol", protocol);
		
		return res.toString();
	}
	
}
