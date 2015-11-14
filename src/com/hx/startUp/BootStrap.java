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

	// ����������
		// ����Serverʵ��
			// Ϊserver, connector, host, �Լ�host������������
		// ����server
	public static void main(String []args) {
		
		Server server = new Server(Constants.PORT);
		server.addLifeCycleListener(new LogLifeCycleListener(server.getContainerName()) );
		initContainerLogListeners(server);
		
		server.start();
		
	}
	
	// ����server��listener
	// ����Ϊ�˼�, ����ȡ��һ��addLifeCycleListenerForChilds, �Լ���ǰ���������
	// ��scalable�ķ�ʽ�ǳ־û��ļ�
	public static void initContainerLogListeners(Server server) {
		server.getConnector().addLifeCycleListener(new LogLifeCycleListener(server.getConnector().getContainerName()) );
		server.getHost().addLifeCycleListener(new LogLifeCycleListener(server.getHost().getContainerName()) );
		server.getHost().addLifeCycleListenerForChilds(new LogLifeCycleListener() );
	}
	
}
