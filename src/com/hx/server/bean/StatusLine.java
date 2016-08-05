/**
 * file name : StatusLine.java
 * created at : 9:48:35 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.bean;

import com.hx.server.util.Tools;

import net.sf.json.JSONObject;

// ״̬��
// HTTP/1.1 200 OK
public class StatusLine {

	// Э��, ��Ӧ��, ״̬
	public String protocol;
	public String code;
	public String status;
	
	// ��ʼ��
	public StatusLine() {
		super();
	}
	public StatusLine(String protocol, String code, String status) {
		super();
		this.protocol = protocol;
		this.code = code;
		this.status = status;
	}

	// ��ȡ״̬���ַ���
		// �Կո�ָ�protocol, code, status
	public String toStatusLineString() {
		StringBuilder sb = new StringBuilder();
		sb.append(protocol); sb.append(Tools.SPACE);
		sb.append(code); sb.append(Tools.SPACE);
		sb.append(status); sb.append(Tools.SPACE);
		
		return sb.toString();
	}
	
	// for debug ..
	public String toString() {
		JSONObject res = new JSONObject();
		res.element("protocol",protocol);
		res.element("code", code);
		res.element("status", status);
		
		return res.toString();
	}
	
}
