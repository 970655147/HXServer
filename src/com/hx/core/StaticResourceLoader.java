/**
 * file name : StaticResourceLoader.java
 * created at : 10:51:13 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.hx.util.Constants;
import com.hx.util.Tools;

public class StaticResourceLoader {

	// 加载请求的静态资源
	public static void load(Host host, Request req, Response resp) {
		Context context = (Context) host.getChild(req.getAttribute(Constants.CONTEXT) );
		if(context == null) {
			Tools.notFound404(host, req, resp);
			return ;
		}
		
		String contextPath = context.getContextPath();
		String path = req.getAttribute(Constants.PATH);
		File staticSource = new File(contextPath + Tools.INV_SLASH + path);
		if(! staticSource.exists()) {
			Tools.notFound404(host, req, resp);
			return ;
		}
		
		PrintWriter out = resp.getWriter();
		try {
			out.println(Tools.getContent(staticSource) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
