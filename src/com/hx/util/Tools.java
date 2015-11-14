/**
 * file name : Tools.java
 * created at : 4:45:41 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import net.sf.json.JSONObject;

import com.hx.core.Host;
import com.hx.core.Request;
import com.hx.core.Response;
import com.hx.core.StaticResourceLoader;

// 工具类
public class Tools {
	
	// 常量
	public static String EMPTY_STR = "";
	public static String NULL = "null";
	public static final Character SLASH = '\\';
	public static final Character INV_SLASH = '/';
	public static final Character DOT = '.';
	public static final Character COMMON = ',';
	public static final Character COLON = ':';
	public static final Character SPACE = ' ';
	public static final Character AND = '&';
	public static final Character EQUAL = '=';
	public static final Character TAB = '\t';
	public static final Character CR = '\r';
	public static final Character LF = '\n';
	public static final Character QUESTION = '?';
	public static final String CRLF = "\r\n";
	public static Random ran = new Random();
	public static String DEFAULT_CHARSET = "utf-8";
	
	// http相关常量
	public static final String COOKIE_STR = "Cookie";
	public static final String RESP_COOKIE_STR = "Set-Cookie";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String CONNECTION = "Connection";
	public static final String HOST = "Host";
	public static final String REFERER = "Referer";
	public static final String USER_AGENT = "User-Agent";
	public static final String DATE = "Date";
	public static final String SERVER = "Server";
	public static final String TRANSFER_ENCODING = "Transfer-Encoding";
	public static final String LAST_MODIFIED = "Last-Modified";
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
	
	// 后缀相关
	public static String HTML = ".html";
	public static String JAVA = ".java";
	public static String TXT = ".txt";
	public static String PNG = ".png";
	public static String JPEG = ".jpeg";
	public static String JS = ".js";
	public static String MAP = ".map";
	
	// 如果字符串为一下字符串, 将其视为空字符串
	static Set<String> emptyStrCondition = new HashSet<>();
	static {
		emptyStrCondition.add(EMPTY_STR);
	}
	
	// 工具方法
	// 确保val 和expected相同, 否则 抛出异常
	public static void assert0(int val, int expect) {
		assert0(val, expect, true);
	}
	public static void assert0(int val, int expect, boolean isEquals) {
		if(isEquals ^ (val == expect)) {
			String symbol = null;
			if(isEquals) {
				symbol = "!=";
			} else {
				symbol = "==";
			}
			throw new RuntimeException("assert0Exception : " + val + " " + symbol + " " + expect);
		}
	}
	public static <T> void assert0(T val, T expect) {
		assert0(val, expect, true);
	}
	public static <T> void assert0(T val, T expect, boolean isEquals) {
		if(isEquals ^ (val == expect)) {
			throw new RuntimeException("assert0Exception : " + val + " != " + expect);
		}
	}

	// 使当前线程休眠sleepMillus
	public static void sleep(int sleepMillus) {
		try {
			Thread.sleep(sleepMillus);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// dateFormat
	public final static SimpleDateFormat createDateFormat = new SimpleDateFormat("yyyy-MM-dd HH : mm");
	
	// 获取当前时间的格式化字符串
	public static String getNow() {
		return createDateFormat.format(new Date() );
	}
	
	// 打印日志
	public static void log(Object obj, Object content) {
		Log.log(obj.getClass().getName() + " -> " + Tools.getNow() + " : "  + content.toString() );
	}
	public static void err(Object obj, Object content) {
		Log.err(obj.getClass().getName() + " -> " + Tools.getNow() + " : "  + content.toString() );
	}
	
	// 判断字符串是否为空[null, "", "null"]
	public static boolean isEmpty(String str) {
		return (str == null) || emptyStrCondition.contains(str.trim());
	}
	public static boolean isEmpty(JSONObject jsObj) {
		return (jsObj == null) || (jsObj.size() == 0);
	}	
	
	// 获取给定的输入流中的字符内容
	public static String getContent(InputStream is, String charset) throws IOException {
		StringBuilder sb = new StringBuilder(is.available() );
		BufferedReader br = new BufferedReader(new InputStreamReader(is, charset) );

		String line = null;
		while((line = br.readLine()) != null) {
			sb.append(line );
		}
		br.close();
		
		return sb.toString();
	}
	public static String getContent(InputStream is) throws IOException {
		return getContent(is, DEFAULT_CHARSET);
	}
	public static String getContent(String path, String charset) throws IOException {
		return getContent(new File(path), charset);
	}
	public static String getContent(File file, String charset) throws IOException {
		return getContent(new FileInputStream(file), charset);
	}
	public static String getContent(String path) throws IOException {
		return getContent(new File(path), DEFAULT_CHARSET);
	}
	public static String getContent(File file) throws IOException {
		return getContent(file, DEFAULT_CHARSET);
	}
	
	// 文件路径
	// 映射规则  webPath -> filePath
	// /c/programFiles  => c:/programFiles
		// 解析Path
	public static String getFilePathByWebPath(String webPath) {
		String path = null;
		int idx = 1;
		int diskNameIdx = webPath.indexOf(Tools.INV_SLASH, idx);
		
		if(diskNameIdx < 0) {
			if(webPath.length() == 2) {
				StringBuilder sb = new StringBuilder(webPath.length() );
				sb.append(webPath.charAt(1) );
				sb.append(Tools.COLON);				
				sb.append(Tools.INV_SLASH);
				path = sb.toString();
			} else {
				path = "/";
			}
		} else {
			StringBuilder sb = new StringBuilder(webPath.length() );
			sb.append(webPath.substring(0, diskNameIdx) );
			sb.append(Tools.COLON);
			sb.append(webPath.substring(diskNameIdx) );
			if(sb.charAt(sb.length()-1) != Tools.INV_SLASH) {
				sb.append(Tools.INV_SLASH);
			}
			path = sb.toString();
		}
		
		return path;
	}
	// 映射规则  filePath -> webPath 
	public static String getWebPathByFilePath(String path) {
		int colonIdx = path.indexOf(Tools.COLON);
		if(colonIdx > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(path.substring(0, colonIdx) );
			sb.append(path.substring(colonIdx + 1) );
			return sb.toString();
		} 
		
		return path;
	}
	
	// 获取 父 / 孩子 文件的路径
	public static String getChildFilePath(String parent, String childFile) {
		return getWebPathByFilePath(parent) + childFile;
//		return childFile;
	}
	public static String getParentFilePath(String parent) {
		String webPath = getWebPathByFilePath(parent);
		int endIdx = webPath.lastIndexOf(Tools.INV_SLASH, webPath.length()-2);
		if(endIdx > 0) {
			return webPath.substring(0, endIdx+1);
		} else {
			return parent.charAt(0) + "/";
		}
	}	
	
	// 获取响应头字符串根据给定的Map
	public static String getHeaderString(Map<String, String> headers) {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String> > it = headers.entrySet().iterator();
		while(it.hasNext() ) {
			Entry<String, String> entry = it.next();
			sb.append(entry.getKey() );	sb.append(Request.headerSep);
			sb.append(entry.getValue() );	sb.append(Tools.CRLF);
		}
		
		return sb.toString();
	}
	
	// 判断两个字符串是否一样 [忽略大小写]
	public static boolean equalsIgnoreCase(String str01, String str02) {
		return str01.toUpperCase().equals(str02.toUpperCase() );
	}
	
	// 如果给定的对象不为空, 则将其加入给定的JSONObject中
	public static void addIfNotEmpty(JSONObject obj, String key, String val) {
		if(! isEmpty(val)) {
			obj.put(key, val);
		}
	}
	public static void addIfNotEmpty(JSONObject obj, String key, JSONObject jsonObj) {
		if(! isEmpty(jsonObj)) {
			obj.put(key, jsonObj.toString());
		}
	}	
	public static void addIfNotEmpty(JSONObject obj, String key, Object valObj) {
		if(valObj != null) {
			obj.put(key, valObj.toString());
		}
	}	
	
	// 获取给定文件的前缀
	public static String getFileName(File file) {
		StringBuilder sb = new StringBuilder();
		if(file.isDirectory() ) {
			sb.append("[dir]");
		} else {
			sb.append("[file]");
		}
		
		sb.append(Tools.SPACE);
		sb.append(file.getName());
		return sb.toString();
	}
	
	// 获取指定路径的文件的文件, 通过sep分割的文件名     获取文件名
	// 解析? 的位置, 是为了防止一下情况
	public static String getFileName(String path, char sep) {
		int start = path.lastIndexOf(sep) + 1;
		
//		http://webd.home.news.cn/1.gif?z=1&_wdxid=01002005057000300000000001110
		int end = getSymAfterFileName(path, start+1);
		if(end != -1) {
			return path.substring(start, end);
		} else {
			return path.substring(start);
		}
	}
	// 文件名后面可能出现的其他符号
	static Set<Character> mayBeFileNameSeps = new HashSet<>();
	static {
		mayBeFileNameSeps.add(QUESTION);
	}
	// 获取文件名后面的可能出现的符合的最近的索引
	private static int getSymAfterFileName(String path, int start) {
		int min = -1;
		for(int i=start; i<path.length(); i++) {
			if(mayBeFileNameSeps.contains(path.charAt(i)) ) {
				min = i;
				break ;
			}
		}
		
		return min;
	}
	
	// 404
	public static void notFound404(Host host, Request req, Response resp) {
		req.setAttribute(Constants.CONTEXT, Constants.staticSource);
		req.setAttribute(Constants.PATH, Constants.source404);
		StaticResourceLoader.load(host, req, resp);
	}
	
	
	
}

