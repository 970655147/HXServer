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

// ҵ����
public class Constants {

	// Server�̳߳����̵߳ĸ���
	public static int threadNums = 10;
	
	// web-apps, ��̬��Դ��׺, �ı���Դ��׺, ͼƬ��Դ��׺
	// web.json Context�����ļ�, ��Ӧ���ֽ����Ļ���
	// ������ԴContext, 404��Դ, favicon���ļ�·��, favicon����·��
	// �����������˿�
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
	
	// ����
	// host, context, wrapper, �Լ���������
	public final static String HOST = "host";
	public final static String CONTEXT = "context";
	public final static String ACTION_WRAPPER = "actionWrapper";
	public final static String PATH = "path";
	public final static ContainerBase ROOT_CONTAINER = null;
	
	// web.json ������ص�key
	// servlet������ [com.hx.core.HttpServlet]
	// ���������б�key
	public final static String CLASS = "classPath";
	public final static String FILTERS = "filters";
	
	// ����, �ȴ��ر��̳߳صļ������
	public final static String SHUTDOWN = "shutdown";
	public final static String RELOAD = "reload";
//	public final static int SHUTDOWN_CHECK_INTERVAL = 3000;
	public final static int THREAD_POOL_CHECK_INTERVAL = 3000;
	
	// ��ʼ����Ӧ�ļ���
	// ����config.conf, ����port, ���webapps
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

	// ����config�е�����
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
