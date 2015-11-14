/**
 * file name : ContainerBase.java
 * created at : 10:55:52 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.interf;

import java.util.Map;
import java.util.Map.Entry;

// ���������Ļ���
public abstract class ContainerBase extends LifeCycleBase {
	
	// ������, ������
	protected ContainerBase parent = null;
	protected Map<String, ContainerBase> childs = null;
	
	// ��ȡ���������Ƶ�������
	public ContainerBase getChild(String name) {
		if(childs == null) {
			return null;
		}
		
		return childs.get(name);
	}
	
	// Ϊÿһ�����������listener
	public void addLifeCycleListenerForChilds(LifeCycleListener listener) {
		if(childs == null) {
			return ;
		}
		
		for(Entry<String, ContainerBase> entry : childs.entrySet()) {
			entry.getValue().addLifeCycleListener(listener.copy(entry.getValue().getContainerName()) );
			entry.getValue().addLifeCycleListenerForChilds(listener);
		}
	}
	
	// ��ȡ������, ����LifeCycleListener.copy(args)
	public abstract String getContainerName() ;
	
}
