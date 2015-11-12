/**
 * file name : FileServer.java
 * created at : 4:38:10 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.hx.core.Processor;
import com.hx.interf.ServerListener;
import com.hx.util.Constants;
import com.hx.util.Log;
import com.hx.util.Tools;

// 文件服务器
public final class Server {

	// 监听的端口, serverSocket, 是否启动服务器, 是否关闭服务器
	private int listenPort;
	private ServerSocket serverSocket;
	private boolean isStart;
	private boolean isStop;
	// 服务器的listener
	private List<ServerListener> serverListeners = new ArrayList<>();
	private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.threadNums);

	// 常量
	private static int defaultPort = 8888;
	
	// 初始化
	public Server() {
		this(defaultPort);
	}
	public Server(int listenPort) {
		Tools.assert0(listenPort > 0, true);
		this.listenPort = listenPort;
		try {
			serverSocket = new ServerSocket(listenPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		isStart = false;
		isStop = false;
	}
	
	// start, stop
	public void start() {
		for(ServerListener listener : serverListeners) {
			fireEvent(listener, ServerListener.BEFORE_START);
		}
		
		try {
			startInternal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		stopInternal();
		
		for(ServerListener listener : serverListeners) {
			fireEvent(listener, ServerListener.POST_STOP);
		}
	}
	
	// 注册listener
	public void addServerListener(ServerListener listener) {
		serverListeners.add(listener);
	}
	
	// 注销listener
	public void removeServerListener(ServerListener listener) {
		serverListeners.remove(listener);
	}
	
	// 触发相应的事件
	private void fireEvent(ServerListener listenser, int eventType) {
		switch(eventType) {
			case ServerListener.BEFORE_START :
				listenser.beforeStart();
				break ;
			case ServerListener.POST_STOP :
				listenser.postStop();
				break ;
			default :
				throw new RuntimeException("have no this type in ServerListener !");
		}
	}
	
	// 启动服务器, 处理业务
	private void startInternal() throws Exception {
		if(! isStart) {
			isStart = true;
			
			while(! isStop) {
				try {
					Socket clientSocket = serverSocket.accept();
					threadPool.execute(new Processor(clientSocket) );
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 关闭服务器
	private void stopInternal() {
		isStop = true;
	}
	
	
}
