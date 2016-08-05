/**
 * file name : HttpServlet.java
 * created at : 9:23:08 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.core;

import com.hx.server.interf.Servlet;
import com.hx.server.util.Tools;

// Servlet 的基本实现
public class HttpServlet extends Servlet {

	@Override
	public void doGet(Request req, Response resp) {
		Tools.log(this, "doGet !");
	}

	@Override
	public void doPost(Request req, Response resp) {
		Tools.log(this, "doPost !");
	}

	@Override
	public void doPut(Request req, Response resp) {
		Tools.log(this, "doPut !");
	}

	@Override
	public void doDelete(Request req, Response resp) {
		Tools.log(this, "doDelete !");
	}

}
