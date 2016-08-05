/**
 * file name : ContainerBase.java
 * created at : 10:55:52 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.server.interf;

import java.util.ArrayList;
import java.util.List;

import com.hx.server.util.Tools;

// LifeCycleBase
public abstract class LifeCycleBase {
	
	// listeners
	private List<LifeCycleListener> listeners = new ArrayList<>();
	
	// start, stop �����¼�  �����ҵ������������
	public void start() {
		for(LifeCycleListener listener : listeners) {
			fireEvent(listener, LifeCycleListener.BEFORE_START);
		}
		
		try {
			startInternal();
		} catch (Exception e) {
			Tools.err(this, "error while starting !");
			e.printStackTrace();
		}
	}
	public void stop() {
		try {
			stopInternal();
		} catch (Exception e) {
			Tools.err(this, "error while stopping !");
			e.printStackTrace();
		}
		
		for(LifeCycleListener listener : listeners) {
			fireEvent(listener, LifeCycleListener.POST_STOP);
		}
	}

	// ע��listener
	public void addLifeCycleListener(LifeCycleListener listener) {
		listeners.add(listener);
	}
	// ע��listener
	public void removeLifeCycleListener(LifeCycleListener listener) {
		listeners.remove(listener);
	}
	
	// ������Ӧ���¼�
	private void fireEvent(LifeCycleListener listenser, int eventType) {
		switch(eventType) {
			case LifeCycleListener.BEFORE_START :
				listenser.beforeStart();
				break ;
			case LifeCycleListener.POST_STOP :
				listenser.postStop();
				break ;
			default :
				throw new RuntimeException("have no this type in ServerListener !");
		}
	}
	
	// start, stop ��ҵ��
	protected abstract void startInternal() throws Exception;
	protected abstract void stopInternal() throws Exception;
	
}
