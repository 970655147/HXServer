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

	// ��������, servlet
	private List<Filter> filters;
	private Servlet servlet;
	private int listenerIdx;
	
	// ��ʼ��
	public FilterChain(Servlet servlet) {
		filters = new ArrayList<>();
		this.servlet = servlet;
		listenerIdx = 0;
	}
	
	// �����Ƴ�filter
	public void addFilter(Filter filter){
		filters.add(filter);
	}
	public void removeFilter(Filter filter){
		filters.remove(filter);
	}	
	
	// ����ҵ��
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
