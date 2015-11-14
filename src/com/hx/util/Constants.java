/**
 * file name : Constants.java
 * created at : 8:38:12 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import net.sf.json.JSONObject;

// 业务常量
public class Constants {

	// Server线程池中线程的个数
	public static int threadNums = 10;
	
	// web-apps
	public final static Set<String> WEB_APPS = new HashSet<>();
	public final static Set<String> staticSourceSuffix = new HashSet<>();
	public final static String webJSON = "web.json";
	public final static int RESP_BUFF_CAP = 4096;
	public final static String staticSource = "StaticSource";
	public final static String source404 = "404.html";
	public static int PORT = 8888;
	
	// 常量
	public final static String HOST = "host";
	public final static String CONTEXT = "context";
	public final static String ACTION_WRAPPER = "actionWrapper";
	public final static String PATH = "path";
	
	// 命令
	public final static String SHUTDOWN = "shutdown";
	public final static int SHUTDOWN_CHECK_INTERVAL = 3000;
	
	// 初始化相应的集合
	static {
		WEB_APPS.add(System.getProperty("user.dir") + "/webapps");
		
		staticSourceSuffix.add(Tools.HTML);
		staticSourceSuffix.add(Tools.TXT);
		
		try {
			JSONObject conf = JSONObject.fromObject(Tools.getContent(System.getProperty("user.dir") + "/conf/config.conf", "gbk") );
			JSONObject host = conf.getJSONObject("host");
			PORT = host.getInt("port");
			WEB_APPS.addAll(host.getJSONArray("webappses") );
		} catch (IOException e) {
			Tools.err(Constants.class, "error while read the conf !");
			e.printStackTrace();
		}
	}
	
	
	
}
