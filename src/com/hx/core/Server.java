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

// 服务器
public final class Server extends LifeCycleBase {

	// 是否启动服务器, 是否关闭服务器
	private volatile boolean isStart;
	private volatile boolean isStop;
	
	// 服务器的listener
	private Connector connector;
	private Host host;
	
	// 常量
//	private static int defaultPort = 8888;
	
	// 初始化
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
	
	// 启动服务器, 处理业务
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
	// 关闭服务器
	protected void stopInternal() {
		isStart = false;
		isStop = true;
		Tools.visit(Tools.getShutdownUrl(getPort()) );
		connector.stop();
		host.stop();
	}
	// 重新加载资源
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
	
	// 获取当前对象的名字, 用于LogListener
	public String getContainerName() {
		return "server";
	}
	public String getServerName() {
		return Constants.SERVER_NAME;
	}
	
	// 输入会阻塞
	// 等待用户控制server结束的线程
	static class WaitThread implements Runnable {
		// 依赖的的服务器对象
		private Server server;
		
		// 初始化
		public WaitThread(Server server) {
			this.server = server;
		}
		
		// 等待用户输入shutdown, 关闭服务器
			// 更新标志位
			// 之所以需要发送一条请求, 是因为Connector的ServerSocket仍然阻塞等待着请求
				// 所以  发送一条请求, 让其进入循环判断, 以退出Connector的业务等待
			// 处理server的stop业务事件
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
