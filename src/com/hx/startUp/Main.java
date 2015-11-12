/**
 * file name : Main.java
 * created at : 5:14:32 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.startUp;

import com.hx.ext.LogServerListener;
import com.hx.server.Server;

public class Main {

	// Æô¶¯·şÎñÆ÷
	public static void main(String []args) {
		
		Server server = new Server();
		server.addServerListener(new LogServerListener() );
		
		server.start();
		
	}
	
}
