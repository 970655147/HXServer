/**
 * file name : Host.java
 * created at : 10:55:39 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.hx.interf.ContainerBase;
import com.hx.util.Tools;

// 一台主机
public class Host extends ContainerBase {

	// 当前host下面的所有的应用
	private Set<String> webAppses;
	
	// 初始化
		// 初始化各个Context
	public Host(ContainerBase parent, Set<String> webAppses) {
		this.parent = parent;
		this.webAppses = webAppses;
		this.childs = new HashMap<>();
		for(String webApps : webAppses) {
			File webAppsFolder = new File(webApps);
			if(! webAppsFolder.isDirectory() ) {
				Tools.err(this, webAppsFolder.getAbsolutePath() + " must be a folder !");
				continue ;
			}
			
			for(File webApp : webAppsFolder.listFiles()) {
				childs.put(webApp.getName(), new Context(this, webApp.getAbsolutePath()) );
			}
		}
	}
	
	// Override form LifeCycleBase
	@Override
	protected void startInternal() {
		for(Entry<String, ContainerBase> entry : childs.entrySet()) {
			entry.getValue().start();
		}
	}
	@Override
	protected void stopInternal() {
		for(Entry<String, ContainerBase> entry : childs.entrySet()) {
			entry.getValue().stop();
		}
	}
	
	// Override form ContainerBase	
	@Override
	public String getContainerName() {
		return "host : " + webAppses.toString();
	}

}
