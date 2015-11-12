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

import com.hx.core.Request;

// ������
public class Tools {
	
	// ����
	public static String EMPTY_STR = "";
	public static String NULL = "null";
	public static final Character SLASH = '\\';
	public static final Character INV_SLASH = '/';
	public static final Character DOT = '.';
	public static final Character COMMON = ',';
	public static final Character COLON = ':';
	public static final Character SPACE = ' ';
	public static final Character TAB = '\t';
	public static final Character CR = '\r';
	public static final Character LF = '\n';
	public static final Character QUESTION = '?';
	public static final String CRLF = "\r\n";
	public static Random ran = new Random();
	public static String DEFAULT_CHARSET = "utf-8";
	
	// http��س���
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
	
	// ����ַ���Ϊһ���ַ���, ������Ϊ���ַ���
	static Set<String> emptyStrCondition = new HashSet<>();
	static {
		emptyStrCondition.add(EMPTY_STR);
	}
	
	// ���߷���
	// ȷ��val ��expected��ͬ, ���� �׳��쳣
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

	// ʹ��ǰ�߳�����sleepMillus
	public static void sleep(int sleepMillus) {
		try {
			Thread.sleep(sleepMillus);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// dateFormat
	public final static SimpleDateFormat createDateFormat = new SimpleDateFormat("yyyy-MM-dd HH : mm");
	
	// ��ȡ��ǰʱ��ĸ�ʽ���ַ���
	public static String getNow() {
		return createDateFormat.format(new Date() );
	}
	
	// ��ӡ��־
	public static void log(Object obj, Object content) {
		Log.log(obj.getClass().getName() + " -> " + Tools.getNow() + " : "  + content.toString() );
	}
	
	// �ж��ַ����Ƿ�Ϊ��[null, "", "null"]
	public static boolean isEmpty(String str) {
		return (str == null) || emptyStrCondition.contains(str.trim());
	}
	public static boolean isEmpty(JSONObject jsObj) {
		return (jsObj == null) || (jsObj.size() == 0);
	}	
	
	// ��ȡ�������������е��ַ�����
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
	
	// �ļ�·��
	// ӳ�����  webPath -> filePath
	// /c/programFiles  => c:/programFiles
		// ����Path
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
			}
		} else {
			StringBuilder sb = new StringBuilder(webPath.length() );
			sb.append(webPath.substring(1, diskNameIdx) );
			sb.append(Tools.COLON);
			sb.append(webPath.substring(diskNameIdx) );
			if(sb.charAt(sb.length()-1) != Tools.INV_SLASH) {
				sb.append(Tools.INV_SLASH);
			}
			path = sb.toString();
		}
		
		return path;
	}
	// ӳ�����  filePath -> webPath 
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
	
	// ��ȡ �� / ���� �ļ���·��
	public static String getChildFilePath(String parent, String childFile) {
		return getWebPathByFilePath(parent) + childFile;
//		return childFile;
	}
	public static String getParentFilePath(String parent) {
		String webPath = getWebPathByFilePath(parent);
		int endIdx = webPath.lastIndexOf(Tools.INV_SLASH, webPath.length()-2);
		if(endIdx > 0) {
			return webPath.substring(0, endIdx);
		} else {
			return "/";
		}
	}	
	
	// ��ȡ��Ӧͷ�ַ������ݸ�����Map
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
	
	// �ж������ַ����Ƿ�һ�� [���Դ�Сд]
	public static boolean equalsIgnoreCase(String str01, String str02) {
		return str01.toUpperCase().equals(str02.toUpperCase() );
	}
	
	// ��������Ķ���Ϊ��, ������������JSONObject��
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
	
	// ��ȡ�����ļ���ǰ׺
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
	
	
}
