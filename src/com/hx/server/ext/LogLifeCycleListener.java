/**
 * file name : LogServerListener.java
 * created at : 5:32:00 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.ext;

import com.hx.server.interf.LifeCycleListener;
import com.hx.server.util.Tools;

// 打印日志的ServerListener
public class LogLifeCycleListener implements LifeCycleListener {
	
	// 打印日志的对象
	public String object;
	
	// 初始化
	public LogLifeCycleListener() {
		this(Tools.NULL);
	}
	public LogLifeCycleListener(String object) {
		this.object = object;
	}

	// Override from LifeCycleListener
	@Override
	public void beforeStart() {
		Tools.log(this, "'" + object + "' starting !");
	}
	@Override
	public void postStop() {
		Tools.log(this, "'" + object + "' stopped !");
	}
	@Override
	public LifeCycleListener copy(String arg) {
		return new LogLifeCycleListener(arg);
	}

}
