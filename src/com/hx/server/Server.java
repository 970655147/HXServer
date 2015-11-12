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

// �ļ�������
public final class Server {

	// �����Ķ˿�, serverSocket, �Ƿ�����������, �Ƿ�رշ�����
	private int listenPort;
	private ServerSocket serverSocket;
	private boolean isStart;
	private boolean isStop;
	// ��������listener
	private List<ServerListener> serverListeners = new ArrayList<>();
	private ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.threadNums);

	// ����
	private static int defaultPort = 8888;
	
	// ��ʼ��
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
	
	// ע��listener
	public void addServerListener(ServerListener listener) {
		serverListeners.add(listener);
	}
	
	// ע��listener
	public void removeServerListener(ServerListener listener) {
		serverListeners.remove(listener);
	}
	
	// ������Ӧ���¼�
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
	
	// ����������, ����ҵ��
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

	// �رշ�����
	private void stopInternal() {
		isStop = true;
	}
	
	
}
