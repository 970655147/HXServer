/**
 * file name : Main.java
 * created at : 5:14:32 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.startUp;

import com.hx.core.Server;
import com.hx.ext.LogLifeCycleListener;
import com.hx.util.Constants;

public class BootStrap {

	// Æô¶¯·þÎñÆ÷
	public static void main(String []args) {
		
		Server server = new Server(Constants.PORT);
		server.addLifeCycleListener(new LogLifeCycleListener(server.getContainerName()) );
		server.getConnector().addLifeCycleListener(new LogLifeCycleListener(server.getConnector().getContainerName()) );
		server.getHost().addLifeCycleListener(new LogLifeCycleListener(server.getHost().getContainerName()) );
		server.getHost().addLifeCycleListenerForChilds(new LogLifeCycleListener() );
		
		server.start();
		
	}
	
}
