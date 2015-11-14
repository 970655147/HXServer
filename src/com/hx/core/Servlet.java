/**
 * file name : Servlet.java
 * created at : 9:20:35 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import com.hx.util.Tools;

public abstract class Servlet {
	
	// service
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
	
	// servlet µÄ½Ó¿Ú
	public abstract void doGet(Request req, Response resp);
	public abstract void doPost(Request req, Response resp);
	public abstract void doPut(Request req, Response resp);
	public abstract void doDelete(Request req, Response resp);
	
}
