/**
 * file name : HelloWorldServlet.java
 * created at : 8:32:49 PM Nov 13, 2015
 * created by 970655147
 */

package com.hx.action;

import java.io.PrintWriter;

import com.hx.server.core.HttpServlet;
import com.hx.server.core.Request;
import com.hx.server.core.Response;
import com.hx.server.util.Log;

public class HelloWorldServlet extends HttpServlet {

	@Override
	public void doGet(Request req, Response resp) {
		
		PrintWriter out = resp.getWriter();
		String name = "info";
		
		out.write("<p> params as follow :</p>") ;
		out.write("<p>" + name + " -> " + req.getParameter(name) + "</p>");
		out.write("<p>Hello World !</p>");
		
	}

	@Override
	public void doPost(Request req, Response resp) {
		
		PrintWriter out = resp.getWriter();
		String name = "name";
		String pwd = "pwd";
		
		out.write("<p> params as follow :</p>") ;
		out.write("<p>" + name + " -> " + req.getParameter(name) + "</p>");
		out.write("<p>" + pwd + " -> " + req.getParameter(pwd) + "</p>");
		out.write("<p>Hello World By Post !</p>");
		
	}
	
}
