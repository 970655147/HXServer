/**
 * file name : Servlet.java
 * created at : 9:20:35 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.interf;

import com.hx.server.core.Request;
import com.hx.server.core.Response;
import com.hx.server.util.Tools;

// servlet 接口
public abstract class Servlet {
	
	// service  控制路由
	public void service(Request req, Response resp) {
		if(Tools.equalsIgnoreCase(req.getMethod(), Request.GET) ) {
			doGet(req, resp);
		} else if(Tools.equalsIgnoreCase(req.getMethod(), Request.POST) ) {
			doPost(req, resp);
		} else if(Tools.equalsIgnoreCase(req.getMethod(), Request.PUT) ) {
			doPut(req, resp);
		} else if(Tools.equalsIgnoreCase(req.getMethod(), Request.DELETE) ) {
			doDelete(req, resp);
 		} else {
 			throw new RuntimeException("have no this request method !");
 		}
	}
	
	// servlet 的接口
	public abstract void doGet(Request req, Response resp);
	public abstract void doPost(Request req, Response resp);
	public abstract void doPut(Request req, Response resp);
	public abstract void doDelete(Request req, Response resp);
	
}
