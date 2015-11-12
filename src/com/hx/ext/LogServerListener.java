/**
 * file name : LogServerListener.java
 * created at : 5:32:00 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.ext;

import com.hx.interf.ServerListener;
import com.hx.util.Tools;

// 打印日志的ServerListener
public class LogServerListener implements ServerListener {

	@Override
	public void beforeStart() {
		Tools.log(this, "server starting !");
	}

	@Override
	public void postStop() {
		Tools.log(this, "server stopped !");
	}

}
