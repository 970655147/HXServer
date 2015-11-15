/**
 * file name : Log.java
 * created at : 8:10:53 PM Apr 22, 2015
 * created by 970655147
 */

package com.hx.util;

import java.util.Iterator;
import java.util.List;

// ��ӡ������ص���
public class Log {

	// ����������ָ�����ļ���
//	private static String outputFilePath;
//	private static FileChannel fc;
//	private static int count;
//	
//	static {
//		count = 1;
//		outputFilePath = "D:\\sudoku.txt";
//		try {
//			fc = new RandomAccessFile(outputFilePath, "rw").getChannel();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
	
	// ��ӡ����
	public static void log() {
		System.out.println("get there...");
	}
	
	// ��ӡ����
	public static void log(Object obj) {
		System.out.println(obj.toString());
	}
	
	public static void logWithoutLn(Object obj) {
		System.out.print(obj.toString());
	}
	
	// ��ӡ����
	public static void err(Object obj) {
		System.err.println(obj.toString());
	}
	
	// ��ӡ�������е�Ԫ��
	public static void log(Iterator it) {
		while(it.hasNext()) {
			logWithoutLn(it.next() + " ");
//			enter();
		}
		enter();
	}
	
	// ��ӡList
	public static void log(List ls) {
		Iterator it = ls.iterator();
		while(it.hasNext()) {
			logWithoutLn(it.next() + " ");
		}
		Log.enter();
	}
	
	// ��ӡObject[]
	public static void log(Object[] arr) {
		for(Object obj : arr) {
			log(obj);
		}
	}
	
	// ��ӡInteger[][]  ��ʽ���� 
	// 1 2 3 
	// 2 1 3
	// 3 2 1
	public static void log(Integer[][] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) * arr.length << 2);
		
//		sb.append(count++ + ". \r\n");
		sb.append("c/r  ");
		for(int i=0; i<arr[0].length; i++) {
			sb.append(i + "  ");
		}
		sb.append("\r\n");
		
		for(int i=0; i<arr.length; i++) {
			Integer[] row = arr[i];
			sb.append(i + " : ");
			for(int j=0; j<row.length; j++) {
				if(arr[i][j] < 10) {
					sb.append(" ");
				}
				sb.append(arr[i][j] + " ");
//				sb.append(arr[i][j]);
			}
			sb.append("\r\n");
//			sb.append(" ");
		}
//		sb.append("\r\n");
		
//		try {
//			fc.write(ByteBuffer.wrap(sb.toString().getBytes()));
//			log("write success....");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		System.out.println(sb.toString());
	}
	
	public static void log(Integer[] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) << 1);
		
//		sb.append(count++ + ". \r\n");
		for(int i=0; i<arr.length; i++) {
			sb.append(arr[i]);
			sb.append(" ");
		}
//		sb.append("\r\n");
		
//		try {
//			fc.write(ByteBuffer.wrap(sb.toString().getBytes()));
//			log("write success....");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		System.out.println(sb.toString());
	}
	
	// ��ӡInteger[][] ����������ʾ
	public static void logWithoutPosition(Integer[][] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) * arr.length << 2);

		for(int i=0; i<arr.length; i++) {
			Integer[] row = arr[i];
			for(int j=0; j<row.length; j++) {
				if(arr[i][j] < 10) {
					sb.append(" ");
				}
				sb.append(arr[i][j] + " ");
			}
			sb.append("\r\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// ��ӡint[][]
	public static void log(int[][] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) * arr.length << 2);
		
		sb.append("c/r  ");
		for(int i=0; i<arr[0].length; i++) {
			sb.append(i + "  ");
		}
		sb.append("\r\n");
		
		for(int i=0; i<arr.length; i++) {
			int[] row = arr[i];
			sb.append(i + " : ");
			for(int j=0; j<row.length; j++) {
				if(arr[i][j] < 10) {
					sb.append(" ");
				}
				sb.append(arr[i][j] + " ");
			}
			sb.append("\r\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// ��ӡint[]
	public static void log(int[] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) << 2);
		
		sb.append("r : ");
		for(int i=0; i<arr.length; i++) {
			sb.append(i + "  ");
		}
		sb.append("\r\n");
		sb.append("    ");
		
		for(int i=0; i<arr.length; i++) {
			sb.append(arr[i]);
			sb.append("  ");
		}
		
		System.out.println(sb.toString());
	}
	
	public static void log(byte[] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) << 2);
		
		for(int i=0; i<arr.length; i++) {
			sb.append(String.valueOf((int) (arr[i])) );
			sb.append("  ");
		}
		
		System.out.println(sb.toString());
	}
	
	// ��ӡint[]
	public static void logWithoutPosition(int[] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) << 1);
		
		for(int i=0; i<arr.length; i++) {
			sb.append(arr[i]);
			sb.append(" ");
		}
		sb.append(Tools.CRLF);
		
		System.out.println(sb.toString());
	}
	public static void logWithoutPosition(long[] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) << 1);
		
		for(int i=0; i<arr.length; i++) {
			sb.append(arr[i]);
			sb.append(" ");
		}
		sb.append(Tools.CRLF);
		
		System.out.println(sb.toString());
	}
	
	// ��ӡint[][] ����������ʾ
	public static void logWithoutPosition(int[][] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) * arr.length << 2);

		for(int i=0; i<arr.length; i++) {
			int[] row = arr[i];
			for(int j=0; j<row.length; j++) {
				if(arr[i][j] < 10) {
					sb.append(" ");
				}
				sb.append(arr[i][j] + " ");
			}
			sb.append("\r\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// ��ӡboolean[][]
	public static void log(boolean[][] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) * arr.length << 2);
		
		sb.append("c/r  ");
		for(int i=0; i<arr[0].length; i++) {
			sb.append(i + "     ");
		}
		sb.append("\r\n");
		
		for(int i=0; i<arr.length; i++) {
			boolean[] row = arr[i];
			sb.append(i + " : ");
			for(int j=0; j<row.length; j++) {
				sb.append(arr[i][j] + " ");
			}
			sb.append("\r\n");
		}
		
		System.out.println(sb.toString());
	}
	
	// ��ӡboolean[]
	public static void log(boolean[] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) << 1);
		for(int i=0; i<arr.length; i++) {
			sb.append(arr[i]);
			sb.append(" ");
		}
		
		System.out.println(sb.toString());
	}
	
	// ��ӡchar[]
	public static void log(char[] arr) {
		StringBuilder sb = new StringBuilder((arr.length + 1) << 1);
		
		for(int i=0; i<arr.length; i++) {
			sb.append(arr[i]);
			sb.append(" ");
		}
		
		System.out.println(sb.toString());
	}

	// ��ӡһ��ˮƽ��
	public static void horizon() {
		System.out.println("-----------------------------------");
	}
	
	public static void horizon(int n) {
		for(int i=0; i<n; i++) {
			horizon();
		}
	}

	// ��ӡ����int, boolean
	public static void log(int row, int col) {
		System.out.println(row + ", " + col);
	}

	public static void log(Object row, Object col) {
		System.out.println(row + ", " + col);
	}
	
	public static void log(int[] arr, Iterator<Integer> it) {
		while(it.hasNext()) {
			logWithoutLn(arr[it.next()] + " ");
		}
		enter();
	}
	
	public static <T> void log(T[] arr, Iterator<Integer> it) {
		while(it.hasNext()) {
			logWithoutLn(arr[it.next()] + " ");
		}
		enter();
	}

	public static void log(boolean bool01, boolean bool02) {
		System.out.println(bool01 + ", " + bool02);
	}
	
	// ����һ��/ n���س�
	public static void enter() {
		enter(1);
	}
	public static void enter(int n) {
		for(int i=0; i<n; i++ ) {
			System.out.println();
		}
	}

	// ��ӡҳ��
	public static void logForPage(int i) {
		log("----------------------- [ page : " + i + " ] ----------------------------");
	}

	public static void logForThemes(int i) {
		log("*********************** [ theme : " + i + " ] ****************************");
	}
	
}
