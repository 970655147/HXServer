/**
 * file name : FilterChain.java
 * created at : 11:40:55 AM Nov 14, 2015
 * created by 970655147
 */

package com.hx.server.core;

import java.util.ArrayList;
import java.util.List;

import com.hx.server.interf.Filter;
import com.hx.server.interf.Servlet;

// FilterChain
public class FilterChain {

	// 过滤器们, servlet
	private List<Filter> filters;
	private Servlet servlet;
	private int listenerIdx;
	
	// 初始化
	public FilterChain(Servlet servlet) {
		filters = new ArrayList<>();
		this.servlet = servlet;
		listenerIdx = 0;
	}
	
	// 增加移除filter
	public void addFilter(Filter filter){
		filters.add(filter);
	}
	public void removeFilter(Filter filter){
		filters.remove(filter);
	}	
	
	// 核心业务
	public void doFilter(Request req, Response resp) {
		if(listenerIdx < filters.size() ) {
			Filter fitler = filters.get(listenerIdx ++);
			fitler.doFilter(req, resp, this);
		} else {
			servlet.service(req, resp);
			listenerIdx = 0;
		}
	}
	
}
