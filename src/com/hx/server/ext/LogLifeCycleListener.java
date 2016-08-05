/**
 * file name : LogServerListener.java
 * created at : 5:32:00 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.ext;

import com.hx.server.interf.LifeCycleListener;
import com.hx.server.util.Tools;

// ��ӡ��־��ServerListener
public class LogLifeCycleListener implements LifeCycleListener {
	
	// ��ӡ��־�Ķ���
	public String object;
	
	// ��ʼ��
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
