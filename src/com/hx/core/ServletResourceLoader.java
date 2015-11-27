/**
 * file name : ServletResourceLoader.java
 * created at : 2:40:44 PM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import com.hx.interf.ContainerBase;
import com.hx.util.Constants;
import com.hx.util.Tools;

// 处理servlet请求
public class ServletResourceLoader {

	// 加载对应的servlet, 并处理业务
	public static void load(Host host, Request req, Response resp) {
		ContainerBase context = host.getChild(req.getAttribute(Constants.CONTEXT) );
		if(context == null) {
			Tools.notFound404(host, req, resp);
			return ;
		}
		
		ActionWrapper action = (ActionWrapper) context.getChild(req.getAttribute(Constants.PATH) );
		if(action == null) {
			Tools.notFound404(host, req, resp);
			return ;
		}
		
		action.service(req, resp);
	}
	
}
