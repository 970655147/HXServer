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

import com.hx.core.HttpServlet;
import com.hx.core.Request;
import com.hx.core.Response;
import com.hx.util.Log;
import com.hx.util.Tools;

// 浏览文件的servlet
public class FileBroseServlet extends HttpServlet {

	@Override
	public void doGet(Request req, Response resp) {
		String path = null;
		try {
			path = URLDecoder.decode(Tools.getFilePathByWebPath(req.getParameter("path")), "utf-8");
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
					respSb.append("<a target='_self' href='/FileBrowser/fileBroseAction?path=" + Tools.getParentFilePath(path) + "' >..</a>");
					respSb.append("<br />");
					File[] childFiles = file.listFiles();
					for(int i=0; i<childFiles.length; i++) {
						respSb.append("<a target='_self' href='/FileBrowser/fileBroseAction?path=" + Tools.getChildFilePath(path, childFiles[i].getName() ) + "' >" + Tools.getFileName(childFiles[i]) + "</a>");
						respSb.append("<br />");
					}
		 		} else {
		 			if(handleble.contains(Tools.getFileName(file.getName(), Tools.DOT)) ) {
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
	
}
