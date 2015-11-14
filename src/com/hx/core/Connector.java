/**
 * file name : Connector.java
 * created at : 8:47:02 PM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.hx.interf.LifeCycleBase;
import com.hx.util.Constants;

public final class Connector extends LifeCycleBase {

	// 监听的端口, serverSocket, 处理业务的线程池
	private int listenPort;
	private ServerSocket serverSocket;
	private Server server;
	private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.threadNums);	
	
	public Connector(int listenPort, Server server) {
		this.listenPort = listenPort;
		this.server = server;
		try {
			serverSocket = new ServerSocket(listenPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getContainerName() {
		return "connector, listenning : [" + listenPort + "]";
	}

	@Override
	protected void startInternal() throws Exception {
		while(! server.isStop()) {
			try {
				Socket clientSocket = serverSocket.accept();
				threadPool.execute(new Processor(clientSocket, server.getHost()) );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	protected void stopInternal() throws Exception {
		
	}
	
}
