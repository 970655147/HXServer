/**
 * file name : ContainerBase.java
 * created at : 10:55:52 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.interf;

import java.util.Map;
import java.util.Map.Entry;

// 容器类对象的基类
public abstract class ContainerBase extends LifeCycleBase {
	
	// 父容器, 子容器
	protected ContainerBase parent = null;
	protected Map<String, ContainerBase> childs = null;
	
	// 获取给定的名称的子容器
	public ContainerBase getChild(String name) {
		if(childs == null) {
			return null;
		}
		
		return childs.get(name);
	}
	
	// 为每一个子容器添加listener
	public void addLifeCycleListenerForChilds(LifeCycleListener listener) {
		if(childs == null) {
			return ;
		}
		
		for(Entry<String, ContainerBase> entry : childs.entrySet()) {
			entry.getValue().addLifeCycleListener(listener.copy(entry.getValue().getContainerName()) );
			entry.getValue().addLifeCycleListenerForChilds(listener);
		}
	}
	
	// 获取容器名, 用于LifeCycleListener.copy(args)
	public abstract String getContainerName() ;
	
}
