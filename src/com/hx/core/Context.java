/**
 * file name : Context.java
 * created at : 11:01:36 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hx.interf.ContainerBase;
import com.hx.interf.Filter;
import com.hx.util.Constants;
import com.hx.util.Tools;

// һ��web��Ŀ
public class Context extends ContainerBase {

	// ��ǰӦ����������е�servlet, servlet ������filter��name��ӳ��, ���е�filter��name��filter��ӳ��
	private String contextPath;
	private Map<String, List<String>> servletToFilters;
	private Map<String, Filter> filters;
	
	// ��ʼ��
		// ����web.json, ʵ����servlet, filters
		// ����servlet�����Ӧ��filter��ӳ��
	public Context(ContainerBase parent, String contextPath) {
		this.parent = parent;
		this.contextPath = contextPath;
		this.childs = new HashMap<>();
		this.servletToFilters = new HashMap<>();
		this.filters = new HashMap<>();
		File webAppFolder = new File(contextPath);
		if(! webAppFolder.isDirectory() ) {
			Tools.err(this, webAppFolder + " must be a folder !");
		}
		
		try {
			JSONObject webJSON = JSONObject.fromObject(Tools.getContent(new File(webAppFolder, Constants.webJSON)) );
			JSONArray servletEntrys = webJSON.names();
			Iterator<String> it = servletEntrys.iterator();
			while(it.hasNext() ) {
				String urlPat = it.next();
				JSONObject actionConfig = webJSON.getJSONObject(urlPat);
				String servletName = actionConfig.getString(Constants.CLASS);
				
				JSONArray configedFilters = actionConfig.optJSONArray(Constants.FILTERS);
				if(configedFilters != null) {
					for(int i=0; i<configedFilters.size(); i++) {
						String filterName = configedFilters.getString(i);
						if(! filters.containsKey(filterName) ) {
							Filter curFilter = (Filter) Tools.getInstance(contextPath, filterName);
							filters.put(filterName, curFilter);
						}
						
						List<String> filtersForCurServlet = servletToFilters.get(servletName);
						if(filtersForCurServlet == null) {
							filtersForCurServlet = new ArrayList<>();
							servletToFilters.put(servletName, filtersForCurServlet);
						}
						filtersForCurServlet.add(filterName);
					}
				}
				
				childs.put(urlPat, new ActionWrapper(this, contextPath, servletName) );
			}
		} catch (Exception e) {
			Tools.err(this, "error while parse " + Constants.webJSON + ", " + webAppFolder + " 's " + Constants.webJSON + " maybe not exists !");
			e.printStackTrace();
		}
	}
	
	// ��ȡcontext��·��
	// ��ȡ��Ӧ��servlet������listener������
	// ��ȡfilterName��Ӧ��filter
	public String getContextPath() {
		return contextPath;
	}
	public List<String> getFiltersForServlet(String servlet) {
		return servletToFilters.get(servlet);
	}
	public Filter getFilterForFilterName(String filterName) {
		return filters.get(filterName);
	}
	
	// Override form LifeCycleBase
	@Override
	public void startInternal() {
		for(Entry<String, ContainerBase> entry : childs.entrySet() ) {
			entry.getValue().start();
		}
		for(Entry<String, Filter> entry : filters.entrySet() ) {
			entry.getValue().onCreate();
		}
	}
	@Override
	public void stopInternal() {
		for(Entry<String, ContainerBase> entry : childs.entrySet() ) {
			entry.getValue().stop();
		}
		for(Entry<String, Filter> entry : filters.entrySet() ) {
			entry.getValue().onDestroy();
		}
	}
	
	// Override form ContainerBase
	@Override
	public String getContainerName() {
		return "context : " + Tools.getFileName(contextPath, Tools.SLASH);
	}

}
