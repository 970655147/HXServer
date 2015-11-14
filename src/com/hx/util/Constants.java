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

import com.hx.interf.ContainerBase;

// 业务常量
public class Constants {

	// Server线程池中线程的个数
	public static int threadNums = 10;
	
	// web-apps, 静态资源后缀, 文本资源后缀, 图片资源后缀
	// web.json Context配置文件, 响应的字节流的缓存
	// 静待资源Context, 404资源, favicon的文件路径, favicon请求路径
	// 服务器监听端口
	public final static Set<String> WEB_APPS = new HashSet<>();
	public final static Set<String> staticSourceSuffix = new HashSet<>();
	public final static Set<String> textSuffixes = new HashSet<>();
	public final static Set<String> imgSuffixes = new HashSet<>();
	public final static String webJSON = "web.json";
	public final static int RESP_BUFF_CAP = 4096;
	public final static int BUFF_SIZE = 2048;
	public final static String staticSource = "StaticSource";
	public final static String source404 = "404.html";
	public final static String favicon = "icons/favicon.ico";
	public final static String faviconPath = "/favicon.ico";
	public static int PORT = 8888;
	
	// 常量
	// host, context, wrapper, 以及顶级容器
	public final static String HOST = "host";
	public final static String CONTEXT = "context";
	public final static String ACTION_WRAPPER = "actionWrapper";
	public final static String PATH = "path";
	public final static ContainerBase ROOT_CONTAINER = null;
	
	// web.json 解析相关的key
	// servlet的名字 [com.hx.core.HttpServlet]
	// 过滤器的列表key
	public final static String CLASS = "classPath";
	public final static String FILTERS = "filters";
	
	// 命令, 等待关闭线程池的检测周期
	public final static String SHUTDOWN = "shutdown";
	public final static String RELOAD = "reload";
//	public final static int SHUTDOWN_CHECK_INTERVAL = 3000;
	public final static int THREAD_POOL_CHECK_INTERVAL = 3000;
	
	// 初始化相应的集合
	// 解析config.conf, 解析port, 添加webapps
	static {
		staticSourceSuffix.add(Tools.HTML);
		staticSourceSuffix.add(Tools.TXT);
		staticSourceSuffix.add(Tools.ICO);
		staticSourceSuffix.add(Tools.PNG);
		staticSourceSuffix.add(Tools.JPEG);
		
		textSuffixes.add(Tools.HTML);
		textSuffixes.add(Tools.TXT);
		imgSuffixes.add(Tools.ICO);
		imgSuffixes.add(Tools.PNG);
		imgSuffixes.add(Tools.JPEG);

		loadConfig();
	}

	// 加载config中的配置
	public static void loadConfig() {
		try {
			WEB_APPS.clear();
			
			JSONObject conf = JSONObject.fromObject(Tools.getContent(System.getProperty("user.dir") + "/conf/config.conf", "gbk") );
			JSONObject host = conf.getJSONObject("host");
			PORT = host.getInt("port");
			WEB_APPS.add(System.getProperty("user.dir") + "/webapps");
			WEB_APPS.addAll(host.getJSONArray("webappses") );
		} catch (IOException e) {
			Tools.err(Constants.class, "error while read the conf !");
			e.printStackTrace();
		}
	}
	
	
	
}
