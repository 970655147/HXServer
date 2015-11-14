/**
 * file name : HelloWorldServlet.java
 * created at : 8:32:49 PM Nov 13, 2015
 * created by 970655147
 */

package com.hx.action;

import java.io.PrintWriter;

import com.hx.core.HttpServlet;
import com.hx.core.Request;
import com.hx.core.Response;
import com.hx.util.Log;
import com.hx.util.Log;

public class HelloWorldServlet extends HttpServlet {

	@Override
	public void doGet(Request req, Response resp) {
		
		PrintWriter out = resp.getWriter();
		out.write("<p>Hello World !</p>");
		
	}

	
	
}
