/**
 * file name : ServerListener.java
 * created at : 4:55:52 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.interf;

import com.hx.util.Tools;

// 服务器的listener
public interface LifeCycleListener {

	// 事件的类型
	public static int BEFORE_START = 0;
	public static int POST_STOP = 1;
	
	// 相应的事件
	public abstract void beforeStart();
	public abstract void postStop();
	// 适用于ContainerBase.addLifeCycleListenerForChilds
	public abstract LifeCycleListener copy(String arg);
	
}
