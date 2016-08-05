/**
 * file name : ActionWrapper.java
 * created at : 11:02:06 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.server.core;

import java.util.List;

import com.hx.server.interf.ContainerBase;
import com.hx.server.interf.Servlet;
import com.hx.server.util.Tools;

// ����һ��Servlet�ķ�װ
public class ActionWrapper extends ContainerBase {

	// servlet��path, ��ǰwrapper��Ӧ��servlet����
	// ��ǰwrapper��Ӧ��filterChain
	private String actionPath;
	private Servlet servlet;
	private FilterChain filterChain;
	
	// ��ʼ��
	public ActionWrapper(ContainerBase parent, String contextPath, String servletName) {
		this.parent = parent;
		actionPath = contextPath + Tools.INV_SLASH + servletName.replace(Tools.DOT, Tools.INV_SLASH);
		try {
			servlet = (Servlet) Tools.newInstance(contextPath, servletName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		init(servletName);
	}
	
	// ��ʼ��
		// ����filterChain, ����ӵ�ǰservlet���е�filter
	private void init(String servletName) {
		filterChain = new FilterChain(servlet);
		Context context = (Context) parent;
		List<String> filters = context.getFiltersForServlet(servletName);
		if(filters != null) {
			for(String filter : filters) {
				filterChain.addFilter(context.getFilterForFilterName(filter) );
			}
		}
	}

	// ҵ�� [����֮����ҵ��]
	public void service(Request req, Response resp) {
		filterChain.doFilter(req, resp);
	}
	
	// Override form LifeCycleBase
	@Override
	public void startInternal() {
		
	}
	@Override
	public void stopInternal() {
		
	}
	
	// Override form ContainerBase
	@Override
	public String getContainerName() {
		return "actionWrapper : " + Tools.getFileName(actionPath, Tools.INV_SLASH);
	}

}
