/**
 * file name : FileBroseServlet.java
 * created at : 9:22:31 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.ext;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.hx.core.HttpServlet;
import com.hx.core.Request;
import com.hx.core.Response;
import com.hx.util.Log;
import com.hx.util.Tools;

public class FileBroseServlet extends HttpServlet {

	@Override
	public void doGet(Request req, Response resp) {
		String path = Tools.getFilePathByWebPath(req.getPath() );
		Log.log(path);
		File file = null;
		if(path != null) {
			file = new File(path);
		}
		StringBuilder respSb = new StringBuilder();
		if((path == null) || (! file.exists()) ) {
			respSb.append("please input path with right format !");
			respSb.append("<br />");
			respSb.append("eg : 'localhost:8888/c/programFiles'  => 'c:/programFiles'");
			respSb.append("<br />");
		} else {
			if(file.isDirectory() ) {
				respSb.append("<a href='" + Tools.getParentFilePath(path) + "' >..</a>");
				respSb.append("<br />");
				File[] childFiles = file.listFiles();
				for(int i=0; i<childFiles.length; i++) {
					respSb.append("<a href='./" + Tools.getChildFilePath(path, childFiles[i].getName() ) + "' >" + Tools.getFileName(childFiles[i]) + "</a>");
					respSb.append("<br />");
				}
	 		} else {
	 			try {
					respSb.append(Tools.getContent(file) );
				} catch (IOException e) {
					e.printStackTrace();
				}
	 		}
		}
		
		respSb.append("<br />");
		respSb.append("<hr />");
		respSb.append("timeNow : " + Tools.getNow() );
		respSb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		respSb.append("created by 970655147 : 2015-11-12 22 : 15" );
		
		PrintWriter out = resp.getWriter();
		out.write(respSb.toString() );
		out.flush();
		out.close();
		
	}

}
