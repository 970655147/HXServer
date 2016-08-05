/**
 * file name : FileBroseServlet.java
 * created at : 9:22:31 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

import com.hx.server.core.HttpServlet;
import com.hx.server.core.Request;
import com.hx.server.core.Response;
import com.hx.server.util.Log;
import com.hx.server.util.Tools;

// 浏览文件的servlet
public class FileBroseServlet extends HttpServlet {

	@Override
	public void doGet(Request req, Response resp) {
		String path = null;
		try {
			path = URLDecoder.decode(getFilePathByWebPath(req.getParameter("path")), "utf-8");
		} catch (Exception e) {
			Tools.err(this, "error while decode url !");
			e.printStackTrace();
		}
		StringBuilder respSb = new StringBuilder();
//		Log.log(path);
		
		if(Tools.INV_SLASH.equals(path)) {
			respSb.append("<span> permissionDenied ! </span>");
			respSb.append("<br />");
//			File[] childFiles = File.listRoots();
//			for(int i=0; i<childFiles.length; i++) {
//				respSb.append("<a target='_self' href='/FileBrowser/fileBroseAction?path=" + Tools.getChildFilePath(path, childFiles[i].getName() ) + "' >" + Tools.getFileName(childFiles[i]) + "</a>");
//				respSb.append("<br />");
//			}
		} else {
			File file = null;
			if(path != null) {
				file = new File(path);
			}
			if((path == null) || (! file.exists()) ) {
				respSb.append("file not exists **or** path not with right format !");
				respSb.append("<br />");
				respSb.append("eg : 'localhost:8888/FileBrowser/fileBroseAction?path=c/programFiles'  => 'c:/programFiles'");
				respSb.append("<br />");
			} else {
				if(file.isDirectory() ) {
					respSb.append("<a target='_self' href='/FileBrowser/fileBroseAction?path=" + getParentFilePath(path) + "' >..</a>");
					respSb.append("<br />");
					File[] childFiles = file.listFiles();
					for(int i=0; i<childFiles.length; i++) {
						respSb.append("<a target='_self' href='/FileBrowser/fileBroseAction?path=" + getChildFilePath(path, childFiles[i].getName() ) + "' >" + getFileName(childFiles[i]) + "</a>");
						respSb.append("<br />");
					}
		 		} else {
		 			if(handleble.contains(Tools.DOT + Tools.getFileName(file.getName(), Tools.DOT)) ) {
			 			try {
							respSb.append(Tools.getContent(file) );
						} catch (IOException e) {
							e.printStackTrace();
						}
		 			} else {
		 				respSb.append("this file is unHandleable !");
		 			}
		 		}
			}
		}
		
		respSb.append("<br />");
		respSb.append("<hr />");
		respSb.append("timeNow : " + Tools.getNow() );
		respSb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		respSb.append("created by 970655147 : 2015-11-12 22 : 15" );
		
		resp.addHeader("Content-Type", "text/html; charset=gbk");
		PrintWriter out = resp.getWriter();
		out.write(respSb.toString() );
		out.close();
		
	}
	
	// 可以打开的文件
	private static Set<String> handleble = new HashSet<>();

	static {
		handleble.add(Tools.TXT);
		handleble.add(Tools.HTML);
		handleble.add(Tools.JAVA);
		handleble.add(Tools.JS);
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
	
}
