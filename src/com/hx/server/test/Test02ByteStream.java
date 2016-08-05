/**
 * file name : Test02ByteStream.java
 * created at : 11:32:36 AM Nov 15, 2015
 * created by 970655147
 */

package com.hx.server.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.hx.server.util.Log;

public class Test02ByteStream {

	// 测试获取字节流的一行数据
	public static void main(String []args) throws IOException {
		
		String path = "F:/1.txt";
		FileInputStream fis = new FileInputStream(path);
		byte[] buff = new byte[2048];
		
//		fis.read(buff);
//		
//		Log.log(buff);
//		
//		Log.log(new String(buff));
		
		try {
			String line = readLine(fis);
			Log.log(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 读取一行数据
	public static String readLine(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder();
		char b = 1;
		do {
			b = (char) is.read();
			sb.append(b);
		} while (b != 10);
		
		return sb.toString();
	}
	
}
