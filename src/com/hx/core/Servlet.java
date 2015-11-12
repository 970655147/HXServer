/**
 * file name : Servlet.java
 * created at : 9:20:35 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

public abstract class Servlet {
	
	// servlet µÄ½Ó¿Ú
	public abstract void doGet(Request req, Response resp);
	public abstract void doPost(Request req, Response resp);
	public abstract void doPut(Request req, Response resp);
	public abstract void doDelete(Request req, Response resp);
	
}
