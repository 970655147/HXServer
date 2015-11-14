/**
 * file name : Context.java
 * created at : 11:01:36 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hx.interf.ContainerBase;
import com.hx.util.Constants;
import com.hx.util.Log;
import com.hx.util.Tools;

public class Context extends ContainerBase {

	// 当前应用下面的所有的servlet
	private String contextPath;
	
	// 初始化
	public Context() {
		
	}
	public Context(String contextPath) {
		this.contextPath = contextPath;
		this.childs = new HashMap<>();
		File webAppFolder = new File(contextPath);
		if(! webAppFolder.isDirectory() ) {
			Tools.err(this, webAppFolder + " must be a folder !");
		}
		
		try {
			JSONObject webJSON = JSONObject.fromObject(Tools.getContent(new File(webAppFolder, Constants.webJSON)) );
			JSONArray servletEntrys = webJSON.names();
			Iterator<String> it = servletEntrys.iterator();
			while(it.hasNext() ) {
				String urlPat = it.next();
				childs.put(urlPat, new ActionWrapper(contextPath, webJSON.getString(urlPat)) );
			}
		} catch (Exception e) {
			Tools.err(this, "error while parse " + Constants.webJSON + " !");
			e.printStackTrace();
		}
	}
	
	// 获取context的路径
	public String getContextPath() {
		return contextPath;
	}
	
	@Override
	public void startInternal() {
		for(Entry<String, ContainerBase> entry : childs.entrySet()) {
			entry.getValue().start();
		}
	}

	@Override
	public void stopInternal() {
		
	}
	
	@Override
	public String getContainerName() {
		return "context : " + Tools.getFileName(contextPath, Tools.SLASH);
	}

}
