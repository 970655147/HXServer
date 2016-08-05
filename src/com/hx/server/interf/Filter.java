/**
 * file name : Filter.java
 * created at : 11:39:31 AM Nov 14, 2015
 * created by 970655147
 */

package com.hx.server.interf;

import com.hx.server.core.FilterChain;
import com.hx.server.core.Request;
import com.hx.server.core.Response;

// 过滤器接口
public interface Filter {

	// 创建过滤器的时候调用, 业务, 销毁过滤器的时候调用
	public void onCreate();
	public void doFilter(Request req, Response resp, FilterChain fc);
	public void onDestroy();
	
}
