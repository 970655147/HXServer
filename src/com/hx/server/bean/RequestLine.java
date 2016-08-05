/**
 * file name : RequestHeader.java
 * created at : 7:40:51 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.bean;

import java.util.HashMap;
import java.util.Map;

import com.hx.server.util.Tools;

import net.sf.json.JSONObject;

// 请求行
// GET /FileBrowser/fileBroseAction?path=C/Users HTTP/1.1
public class RequestLine {

	// 请求方法, 路径, 协议
	public String method;
	public String path;
	public String protocol;
	public Map<String, String> params;
	
	// 初始化
	public RequestLine() {
		params = new HashMap<>();
	}
	
	// 解析请求行
		// 以空格分割请求行, 新建一个RequestLine, 进行初始化, 解析查询字符串参数
	public static RequestLine parse(String requestLine) {
		if(Tools.isEmpty(requestLine) ) {
			return null;
		}
		
		String[] splits = requestLine.split("\\s+");
		if(splits.length != 3) {
			return null;
		}
		
		RequestLine rLine = new RequestLine();
		rLine.method = splits[0];
		rLine.path = getPath(splits[1] );
		rLine.protocol = splits[2];
		parseParams(splits[1], rLine.params, false);
		
		return rLine;
	}
	
	// 解析参数
	// 对于get参数    => /FileBrowser/fileBroseAction?path=C/Users
	// 对于post参数 => name=tyutyu&pwd=fghfghdfg
	public static void parseParams(String path, Map<String, String> params, boolean isPost) {
		int questionIdx = -1;
		if(! isPost) {
			questionIdx = path.indexOf(Tools.QUESTION);
			if(questionIdx < 0) {
				return ;
			}
		} else {
			// for makes first route success !
			questionIdx = -1;
		}
		
		int lastAndIdx = questionIdx;
		int andIdx = 1, equalIdx = 1;
		while(andIdx > 0) {
			andIdx = path.indexOf(Tools.AND, lastAndIdx+1);
			equalIdx = path.indexOf(Tools.EQUAL, lastAndIdx+1);
			if((equalIdx < 0) || (andIdx < 0) ) {
				break ;
			}
			
			if(equalIdx < andIdx) {
				params.put(path.substring(lastAndIdx+1, equalIdx).trim(), path.substring(equalIdx+1, andIdx) );
			}
			lastAndIdx = andIdx;
		}
		equalIdx = path.indexOf(Tools.EQUAL, lastAndIdx);
		if(equalIdx > 0) {
			params.put(path.substring(lastAndIdx+1, equalIdx).trim(), path.substring(equalIdx+1) );
		}
	}
	
	// 获取path
	// '/FileBrowser/fileBroseAction?path=C/Users' => '/FileBrowser/fileBroseAction'
	public static String getPath(String path) {
		int questionIdx = path.indexOf(Tools.QUESTION);
		if(questionIdx > 0) {
			return path.substring(0, questionIdx);
		}
		
		return path;
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
