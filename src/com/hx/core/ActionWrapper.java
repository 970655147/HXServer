/**
 * file name : ActionWrapper.java
 * created at : 11:02:06 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import com.hx.interf.ContainerBase;
import com.hx.util.Tools;

public class ActionWrapper extends ContainerBase {

	// servlet的path
	private String actionPath;
	private Servlet servlet;
	
	// 初始化
	public ActionWrapper() {
		
	}
	public ActionWrapper(String contextPath, String path) {
		actionPath = contextPath + Tools.INV_SLASH + path.replace(Tools.DOT, Tools.INV_SLASH);
		initServlet(contextPath, path);
	}
	
	// 初始化servlet
	public void initServlet(String contextPath, String path) {
        URLClassLoader loader = null; 
        try { 
            // create a URLClassLoader 
            URL[] urls = new URL[1]; 
            URLStreamHandler streamHandler = null; 
            String repository =(new URL("file", "", new File(contextPath).getCanonicalPath() + Tools.INV_SLASH) ).toString() ; 
            urls[0] = new URL(null, repository, streamHandler); 
            loader = new URLClassLoader(urls); 
        } catch (IOException e) { 
        	Tools.err(this, "error while init servlet !");
            e.printStackTrace();
        } 
        
        try {
			Class servletClass = loader.loadClass(path);
			servlet = (Servlet) servletClass.getConstructor().newInstance();
		} catch (Exception e) {
			Tools.err(this, "error while instance servlet !");
			e.printStackTrace();
		}
	}

	@Override
	public void startInternal() {
		
	}

	@Override
	public void stopInternal() {
		
	}
	
	@Override
	public String getContainerName() {
		return "actionWrapper : " + Tools.getFileName(actionPath, Tools.INV_SLASH);
	}
	
	public void service(Request req, Response resp) {
		servlet.service(req, resp);
	}

}
