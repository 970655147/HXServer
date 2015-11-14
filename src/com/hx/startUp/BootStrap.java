/**
 * file name : Main.java
 * created at : 5:14:32 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.startUp;

import com.hx.core.Server;
import com.hx.ext.LogLifeCycleListener;
import com.hx.util.Constants;

// BootStrap
public class BootStrap {

	// 启动服务器
		// 创建Server实例
			// 为server, connector, host, 以及host的所有子容器
		// 启动server
	public static void main(String []args) {
		
		Server server = new Server(Constants.PORT);
		server.addLifeCycleListener(new LogLifeCycleListener(server.getContainerName()) );
		initContainerLogListeners(server);
		
		server.start();
		
	}
	
	// 配置server的listener
	// 这里为了简单, 就提取了一个addLifeCycleListenerForChilds, 以及当前的这个方法
	// 更scalable的方式是持久化文件
	public static void initContainerLogListeners(Server server) {
		server.getConnector().addLifeCycleListener(new LogLifeCycleListener(server.getConnector().getContainerName()) );
		server.getHost().addLifeCycleListener(new LogLifeCycleListener(server.getHost().getContainerName()) );
		server.getHost().addLifeCycleListenerForChilds(new LogLifeCycleListener() );
	}
	
}
