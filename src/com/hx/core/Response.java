/**
 * file name : Response.java
 * created at : 5:52:33 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hx.bean.StatusLine;
import com.hx.util.Constants;
import com.hx.util.Tools;

// ��Ӧ
public class Response {

	// printWriter �򻺴���д����, ������Ӧ����, ״̬��
	// ��Ӧͷ, �ͻ��˵�socket
	private PrintWriter out;
	private ByteArrayOutputStream buff;
	private StatusLine statusLine;
	private Map<String, String> responseHeader;
	private Socket clientSocket;
	
	// ��ʼ��
	public Response() {
		statusLine = new StatusLine("HTTP/1.1", "200", "OK");
		responseHeader = new LinkedHashMap<>();
		responseHeader.put("Content-Type", "text/html; charset=utf-8");
		responseHeader.put("Server", Constants.SERVER_NAME);
		buff = new ByteArrayOutputStream(Constants.RESP_BUFF_CAP);
		out = new PrintWriter(buff);
	}
	
	// ��ʼ��
	public void init() {

	}
	
	// ��ͻ��˵�socketд������ [״̬��, ��Ӧͷ, ��Ӧ����]
	// ��ʹ���ֽ���, ����ý���ļ����䲻�˵�
	public void writeResponse() throws Exception {
		BufferedOutputStream br = new BufferedOutputStream(clientSocket.getOutputStream() );
		br.write(statusLine.toStatusLineString().getBytes() );
		br.write(Tools.CRLF.getBytes() );
		br.write(Tools.getHeaderString(responseHeader).getBytes() );
		br.write(Tools.CRLF.getBytes() );

		out.flush();
		out.close();
		br.write(buff.toByteArray() );
		br.flush();
		br.close();
		clientSocket.close();
	}
	
	// setter & getter
	public PrintWriter getWriter() {
		return out;
	}
	public void addHeader(String key, String val) {
		responseHeader.put(key, val);
	}
	public OutputStream getOutputStream() {
		return buff;
	}
	
	// ��ȡreponse
	public static Response parse(Socket socket) throws Exception {
		Response resp = new Response();
		resp.clientSocket = socket;
		
		return resp;
	}
	
	// for debug ..
	public String toString() {
		JSONObject res = new JSONObject();
		Tools.addIfNotEmpty(res, "requestLine", statusLine);	
		Tools.addIfNotEmpty(res, "requestHeader", responseHeader);
		
		return res.toString();
	}
	
}
