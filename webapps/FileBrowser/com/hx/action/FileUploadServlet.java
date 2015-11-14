/**
 * file name : FileUploadServlet.java
 * created at : 7:37:41 PM Nov 14, 2015
 * created by 970655147
 */

package com.hx.action;

import java.io.FileOutputStream;
import java.io.IOException;

import com.hx.core.HttpServlet;
import com.hx.core.Request;
import com.hx.core.Response;
import com.hx.util.Tools;

// 上传文件的servlet
// 失败了! [有空再做做吧]
public class FileUploadServlet extends HttpServlet {

	@Override 
	public void doPost(Request req, Response resp) {
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("1.txt");
			Tools.copy(req.getInputStream(), fos, false);
		} catch (Exception e) {
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
		
	}
	
}
