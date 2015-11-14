/**
 * file name : CalcServlet.java
 * created at : 11:15:11 AM Nov 14, 2015
 * created by 970655147
 */

package com.hx.action;

import java.io.PrintWriter;
import java.net.URLDecoder;

import com.hx.core.HttpServlet;
import com.hx.core.Request;
import com.hx.core.Response;
import com.hx.util.Tools;

// ¼ÆËãÆ÷Servlet
public class CalcServlet extends HttpServlet {

	@Override
	public void doGet(Request req, Response resp) {
		Tools.log(this, "service !");
		StringBuilder sb = new StringBuilder();
		sb.append("res is : ");
		int ope01 = 0, ope02 = 0, res = -1;
		String optr = null;
		boolean isException = false;
		
		try {
			optr = URLDecoder.decode(req.getParameter("optr"), "utf-8");
			ope01 = Integer.parseInt(req.getParameter("ope01") );
			ope02 = Integer.parseInt(req.getParameter("ope02") );
		} catch (Exception e) {
			sb.append("operand isn't format, please input number or optrator is not encoded with 'utf-8' !");
			isException = true;
		}
		
		if(optr == null) {
			sb.append("please input operator !");
			isException = true;
		} 
		
		switch(optr) {
			case "+" :
				res = ope01 + ope02;
				break;
			case "-" :
				res = ope01 - ope02;
				break;
			case "*" :
				res = ope01 * ope02;
				break;
			case "/" :
				res = ope01 / ope02;
				break;
			default :
				isException = true;
				sb.append("operator isn't format, please input '+ - * /' !");
				break ;
		}
		
		if(! isException) {
			sb.append(res);
		}
		
		PrintWriter out = resp.getWriter();
		out.write(sb.toString() );
	}

	@Override
	public void doPost(Request req, Response resp) {
		doGet(req, resp);
	}
	
}
