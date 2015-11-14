/**
 * file name : FileServer.java
 * created at : 4:38:10 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.util.Scanner;

import com.hx.interf.LifeCycleBase;
import com.hx.util.Constants;
import com.hx.util.Log;
import com.hx.util.Tools;

// �ļ�������
public final class Server extends LifeCycleBase {

	// �Ƿ�����������, �Ƿ�رշ�����
	private boolean isStart;
	private boolean isStop;
	
	// ��������listener
	private Connector connector;
	private Host host;
	
	// ����
	private static int defaultPort = 8888;
	
	// ��ʼ��
	public Server() {
		this(defaultPort);
	}
	public Server(int listenPort) {
		Tools.assert0(listenPort > 0, true);
		
		connector = new Connector(listenPort, this);
		host = new Host(Constants.WEB_APPS);
		isStart = false;
		isStop = false;
	}
	
	@Override
	public String getContainerName() {
		return "server";
	}
	
	// ����������, ����ҵ��
	protected void startInternal() throws Exception {
		if(! isStart) {
			isStart = true;
			host.start();
			new Thread(new Runnable() {
				public void run() {
					connector.start();
				}
			}).start();;
			
			new Thread(new WaitThread(this)).start();
		}
	}

	// �رշ�����
	protected void stopInternal() {
		isStop = true;
		connector.stop();
		host.stop();
	}
	
	// setter & getter
	public boolean isStop() {
		return isStop;
	}
	public Host getHost() {
		return host;
	}
	public Connector getConnector() {
		return connector;
	}
	protected void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	
	// ���������
//	// �ȴ��û�����server�������߳�
	static class WaitThread implements Runnable {
		// �����ĵķ���������
		private Server server;
		
		// ��ʼ��
		public WaitThread(Server server) {
			this.server = server;
		}
		
		@Override
		public void run() {
			Scanner input = new Scanner(System.in);
			while(! server.isStop()) {
				String cmd = input.nextLine();
				if(Constants.SHUTDOWN.equals(cmd) ) {
					server.setStop(true);
				}
				Tools.sleep(Constants.SHUTDOWN_CHECK_INTERVAL);
			}
		}
	}
	
}
