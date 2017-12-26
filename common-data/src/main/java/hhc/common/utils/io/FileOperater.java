/*---------------------------------------------------------------------------*/
/**
 * @(#)$Id: FileOperater.java 9495 2014-09-28 10:24:19Z shiyongping $ 
 * @(#) Implementation file of class Area.
 * @author SearchNLP
 * (c)  SUNTEC CORPORATION  2013
 * All Rights Reserved.
 */

package hhc.common.utils.io;


import org.apache.log4j.Logger;

import java.io.*;

public class FileOperater {
	static Logger logger = Logger.getLogger(FileOperater.class);

	public static void readline(String path, Class<? extends Operations> doThing, Object object)
			throws InstantiationException, IllegalAccessException, InterruptedException {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));

			// int count = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				// System.out.println(count++);
				// System.out.print(count++);
				doThing.newInstance().operation(line, object);
				// Thread.sleep(500);
				// System.out.println(line);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	public static void readline(String path, MyCallBack object) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));

			// int count = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				// System.out.println(count++);
				// System.out.print(count++);
				object.operation(line, null);
				// Thread.sleep(500);
				// System.out.println(line);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	

	/**
	 * 
	 * @param
	 * @param object
	 */
	public static void readline(InputStream inputStream, MyCallBack object) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

			// int count = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				// System.out.println(count++);
				// System.out.print(count++);
				object.operation(line, null);
				// Thread.sleep(500);
				// System.out.println(line);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}


	
	public static void readline(String path, MyCallBack object,String encode) {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encode));

			// int count = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				// System.out.println(count++);
				// System.out.print(count++);
				object.operation(line, null);
				// Thread.sleep(500);
				// System.out.println(line);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	public static String read(String path) throws IOException {
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();

		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1024];
		StringBuffer sb = new StringBuffer();
		while ((fis.read(buf)) != -1) {
			sb.append(new String(buf));
			buf = new byte[1024];// 閲嶆柊鐢熸垚锛岄伩鍏嶅拰涓婃璇诲彇鐨勬暟鎹�??��澶�

		}
		fis.close();
		return sb.toString();
	}

	public static void write(final String path, String print) {

		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, true);
			StringBuffer sb = new StringBuffer();
			sb.append(print + "\n");
			out.write(sb.toString().getBytes("utf-8"));
			// }
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	public static void writeStrings(final String path, String[] prints) {

		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, true);
			StringBuffer sb = new StringBuffer();
			for(String print:prints){
				sb.append(print + "\n");
			}
			out.write(sb.toString().getBytes("utf-8"));
			// }
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}


	/**
	 *
	 * @param
	 */
	public static String readFile(InputStream inputStream) {
		StringBuilder sb=new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

			// int count = 0;
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				// System.out.println(count++);
				// System.out.print(count++);
				sb.append(line);
				// Thread.sleep(500);
				// System.out.println(line);
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return sb.toString();
	}

}
