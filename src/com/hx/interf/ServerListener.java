/**
 * file name : ServerListener.java
 * created at : 4:55:52 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.interf;

// ��������listener
public interface ServerListener {

	// �¼�������
	public static int BEFORE_START = 0;
	public static int POST_STOP = 1;
	
	// ��Ӧ���¼�
	public abstract void beforeStart();
	public abstract void postStop();
	
}
