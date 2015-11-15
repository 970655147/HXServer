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
import com.hx.util.Tools;

public final class Connector extends LifeCycleBase {

	// 监听的端口, serverSocket, 处理业务的线程池
	private int listenPort;
	private ServerSocket serverSocket;
	private Server server;
	private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.threadNums);	
	
	// 初始化
	public Connector(int listenPort, Server server) {
		this.listenPort = listenPort;
		this.server = server;
		try {
			serverSocket = new ServerSocket(listenPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Override form LifeCycleBase
	@Override
	protected void startInternal() throws Exception {
		try {
			while(! server.isStop()) {
					Socket clientSocket = serverSocket.accept();
					threadPool.execute(new Processor(clientSocket, server.getHost()) );
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源, 否则 同一个端口reload JVM_Bind
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	protected void stopInternal() throws Exception {
		Tools.awaitShutdown(threadPool);
	}
	
	// 关闭线程池
	public void shutdownThreadPool() {
		threadPool.shutdown();
	}
	
	// 获取当前对象的名字, 用于LogListener
	public String getContainerName() {
		return "connector, listenning : [" + listenPort + "]";
	}
	
	// setter & getter
	public int getPort() {
		return listenPort;
	}
	
}
