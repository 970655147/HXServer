/**
 * file name : LogFilter.java
 * created at : 2:04:36 PM Nov 14, 2015
 * created by 970655147
 */

package com.hx.ext;

import com.hx.core.FilterChain;
import com.hx.core.Request;
import com.hx.core.Response;
import com.hx.interf.Filter;
import com.hx.util.Tools;

// ����filter
public class LogFilter implements Filter {

	// Override from Filter
	@Override
	public void onCreate() {
		
	}
	@Override
	public void doFilter(Request req, Response resp, FilterChain fc) {
		Tools.log(this, "before service !");
		fc.doFilter(req, resp);
		Tools.log(this, "after service !");
	}
	@Override
	public void onDestroy() {
		
	}

}
