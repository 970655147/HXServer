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

	// �����Ķ˿�, serverSocket, ����ҵ����̳߳�
	private int listenPort;
	private ServerSocket serverSocket;
	private Server server;
	private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.threadNums);	
	
	// ��ʼ��
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
			// �ر���Դ, ���� ͬһ���˿�reload JVM_Bind
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
	
	// �ر��̳߳�
	public void shutdownThreadPool() {
		threadPool.shutdown();
	}
	
	// ��ȡ��ǰ���������, ����LogListener
	public String getContainerName() {
		return "connector, listenning : [" + listenPort + "]";
	}
	
	// setter & getter
	public int getPort() {
		return listenPort;
	}
	
}
