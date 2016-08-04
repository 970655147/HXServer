/**
 * file name : FileServer.java
 * created at : 4:38:10 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.util.Scanner;

import com.hx.interf.LifeCycleBase;
import com.hx.startUp.BootStrap;
import com.hx.util.Constants;
import com.hx.util.Tools;

// ������
public final class Server extends LifeCycleBase {

	// �Ƿ�����������, �Ƿ�رշ�����
	private volatile boolean isStart;
	private volatile boolean isStop;
	
	// ��������listener
	private Connector connector;
	private Host host;
	
	// ����
//	private static int defaultPort = 8888;
	
	// ��ʼ��
	public Server() {
		this(Constants.PORT);
	}
	public Server(int listenPort) {
		Tools.assert0(listenPort > 0, true);
		
		connector = new Connector(listenPort, this);
		host = new Host(Constants.ROOT_CONTAINER, Constants.WEB_APPS);
		isStart = false;
		isStop = false;
	}
	
	// ����������, ����ҵ��
	protected void startInternal() throws Exception {
		if(! isStart) {
			isStart = true;
			isStop = false;
			host.start();
			new Thread(new Runnable() {
				public void run() {
					connector.start();
				}
			}).start();
			
			new Thread(new WaitThread(this)).start();
		}
	}
	// �رշ�����
	protected void stopInternal() {
		isStart = false;
		isStop = true;
		Tools.visit(Tools.getShutdownUrl(getPort()) );
		connector.stop();
		host.stop();
	}
	// ���¼�����Դ
	public void reload() {
		this.stop();
		checkUpdate();
		this.start();
	}
	private void checkUpdate() {
		Constants.loadConfig();
		connector = new Connector(Constants.PORT, this);
		host = new Host(Constants.ROOT_CONTAINER, Constants.WEB_APPS);
		BootStrap.initContainerLogListeners(this);
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
	public int getPort() {
		return connector.getPort();
	}
	
	// ��ȡ��ǰ���������, ����LogListener
	public String getContainerName() {
		return "server";
	}
	public String getServerName() {
		return Constants.SERVER_NAME;
	}
	
	// ���������
	// �ȴ��û�����server�������߳�
	static class WaitThread implements Runnable {
		// �����ĵķ���������
		private Server server;
		
		// ��ʼ��
		public WaitThread(Server server) {
			this.server = server;
		}
		
		// �ȴ��û�����shutdown, �رշ�����
			// ���±�־λ
			// ֮������Ҫ����һ������, ����ΪConnector��ServerSocket��Ȼ�����ȴ�������
				// ����  ����һ������, �������ѭ���ж�, ���˳�Connector��ҵ��ȴ�
			// ����server��stopҵ���¼�
		@Override
		public void run() {
			Scanner input = new Scanner(System.in);
			while(! server.isStop()) {
				String cmd = input.nextLine();
				if(Constants.SHUTDOWN.equals(cmd) ) {
					server.stop();
				} else if(Constants.RELOAD.equals(cmd) ) {
					server.reload();
				}
				
//				Tools.sleep(Constants.SHUTDOWN_CHECK_INTERVAL);
			}
		}
	}
	
}
