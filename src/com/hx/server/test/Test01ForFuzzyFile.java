/**
 * file name : Test01ForFuzzyFile.java
 * created at : 8:19:09 PM Nov 12, 2015
 * created by 970655147
 */

package com.hx.server.test;

import java.io.File;

import com.hx.server.util.Log;

public class Test01ForFuzzyFile {

	// ÎÄ¼þ²âÊÔ, File
	public static void main(String []args) {
		
		File file = new File("sdf");
		// sdf
		Log.log(file.getName() );
		// false
		Log.log(file.exists() );
		
		// so should judge if is exists first !
		
	}
	
}
