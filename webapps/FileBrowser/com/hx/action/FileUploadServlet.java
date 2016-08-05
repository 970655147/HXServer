/**
 * file name : FileUploadServlet.java
 * created at : 7:37:41 PM Nov 14, 2015
 * created by 970655147
 */

package com.hx.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.hx.server.core.HttpServlet;
import com.hx.server.core.Request;
import com.hx.server.core.Response;
import com.hx.server.util.Log;
import com.hx.server.util.Tools;

// 上传文件的servlet
// 失败了! [有空再做做吧]		2015.10.14
// 2015.10.15  nice got it !
public class FileUploadServlet extends HttpServlet {

	@Override 
	public void doPost(Request req, Response resp) {
		
		Tools.log(this, "in doPost !");
		FileOutputStream fos = null;
		boolean isSucc = true;
		try {
			fos = new FileOutputStream("F:/1.txt");
			copy(req.getInputStream(), fos, req.getBodyCnt(), false);
		} catch (Exception e) {
			isSucc = false;
			e.printStackTrace();
		}  finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(isSucc) {
			resp.getWriter().print("<h1>Hello upload file success !</h1>");
		} else {
			resp.getWriter().print("<h1>Hello upload file failed !</h1>");
		}
		
		Tools.log(this, "copy file end !");
	}
	
	// 复制req.getInputStream中的数据
	public static void copy(InputStream is, OutputStream os, long len, boolean closeStream) {
		int buffSize = 2048;
		int batchTimes = (int) (len / buffSize);
		int lastBatch = (int) (len - batchTimes * buffSize);
		
		byte[] buff = new byte[buffSize];
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		try {
			for(int i=0; i<batchTimes; i++) {
				int readedCnt = bis.read(buff);
				os.write(buff, 0, readedCnt);
//				Log.log(i, readedCnt);
			}
			bis.read(buff, 0, lastBatch);
			os.write(buff, 0, lastBatch);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(closeStream) {
				try {
					if(bis != null) {
						bis.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(bos != null) {
						bos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
