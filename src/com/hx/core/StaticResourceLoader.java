/**
 * file name : StaticResourceLoader.java
 * created at : 10:51:13 AM Nov 13, 2015
 * created by 970655147
 */

package com.hx.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import net.sf.image4j.codec.ico.ICODecoder;
import net.sf.image4j.codec.ico.ICOEncoder;

import com.hx.util.Constants;
import com.hx.util.Tools;

// 静态资源处理
public class StaticResourceLoader {

	// 加载请求的静态资源
		// 如果对应的context为空, 返回404
		// 如果对应的资源不存在, 返回404
		// 否则  资源存在
			// 如果文本资源, 加载其中的数据返回
			// 如果为图片资源, 加载图片返回
			// 其他, 待扩展
	public static void load(Host host, Request req, Response resp) {
		Context context = (Context) host.getChild(req.getAttribute(Constants.CONTEXT) );
		if(context == null) {
			Tools.notFound404(host, req, resp);
			return ;
		}
		
		String contextPath = context.getContextPath();
		String path = req.getAttribute(Constants.PATH);
		File staticSource = new File(contextPath + Tools.INV_SLASH + path);
		if(! staticSource.exists()) {
			Tools.notFound404(host, req, resp);
			return ;
		}
		
		String suffix = Tools.DOT + Tools.getFileName(path, Tools.DOT);
		if(Constants.textSuffixes.contains(suffix)) {
			handleTextResource(resp, staticSource);
		} else if(Constants.imgSuffixes.contains(suffix)) {
			handleImgResource(resp, staticSource, suffix);
		} else {
			handleUnsupportedResource(resp, staticSource);
		}

	}
	
	// 处理文本资源
	public static void handleTextResource(Response resp, File staticSource) {
		PrintWriter out = resp.getWriter();
		try {
			String content = Tools.getContent(staticSource);
			out.println(content );
//			Log.log(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 处理图片资源
	public static void handleImgResource(Response resp, File staticSource, String suffix) {
		resp.addHeader("Content-Type", "image");
		try {
			BufferedImage img = null;
			if(Tools.ICO.equals(suffix) ) {
				img = ICODecoder.read(staticSource).get(0);
				ICOEncoder.write(img, resp.getOutputStream() );
			} else {
				img = ImageIO.read(staticSource);
				ImageIO.write(img, suffix.substring(1), resp.getOutputStream() );
			}
		} catch (IOException e) {
			Tools.log(StaticResourceLoader.class, "error while load :" + staticSource.getAbsolutePath() );
			e.printStackTrace();
		}
	}
	
	// 处理不支持的资源
	public static void handleUnsupportedResource(Response resp, File staticSource) {
		PrintWriter out = resp.getWriter();
		out.println("unsupported resource !" );
	}
	
}
