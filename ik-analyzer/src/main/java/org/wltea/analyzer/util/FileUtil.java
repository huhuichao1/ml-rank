package org.wltea.analyzer.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * 文件工具类
 * 
 * @author shouyilin
 * 
 */
public class FileUtil {

	public static boolean mkdirs(File directory) {
		if (directory == null) {
			return false;
		}
		if (directory.isFile()) {
			if (directory.delete()) {
			} else {
				return false;
			}
		}
		if (!directory.exists()) {
			if (directory.mkdirs()) {
			} else {
				return false;
			}
		}
		return true;
	}

	public static String readString(File file) {
		return readString(file, null);
	}

	/**
	 * 
	 * @param file
	 *            文本文件
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String readString(File file, String charset) {
		if (file == null || !file.isFile()) {
			return null;
		}
		byte[] b = read(file);
		if (b == null) {
			return null;
		}
		if (StringUtil.isEmpty(charset)) {
			return new String(b);
		} else {
			try {
				return new String(b, charset);
			} catch (UnsupportedEncodingException e) {
				LogUtil.IK.error("", e);
			}
		}
		return null;
	}

	/**
	 * 读取文件
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] read(File file) {
		// TODO Auto-generated method stub
		if (file == null || !file.isFile()) {
			return null;
		}
		try {
			return IOUtil.read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param input
	 * @return
	 */
	public static File write(File file, byte[] input) {
		return write(file, false, input);
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param input
	 * @return
	 */
	public static File write(File file, boolean append, byte[] input) {
		if (file == null || input == null) {
			return null;
		}
		try {
			if (IOUtil.write(new FileOutputStream(file, append), input)) {
				return file;
			}
		} catch (FileNotFoundException e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	/**
	 * 移动文件（不支持目录）
	 * 
	 * @param source
	 * @param target
	 * @param replace
	 * @return
	 */
	public static boolean move(File source, File target, boolean replace) {
		if (source == null || !source.isFile() || target == null) {
			return false;
		}
		if (copy(source, target, replace)) {
			if (source.delete()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 复制文件(不支持目录)
	 * 
	 * @param source
	 * @param target
	 * @param replace
	 * @return
	 */
	public static boolean copy(File source, File target, boolean replace) {
		if (source == null || !source.isFile() || target == null) {
			return false;
		}
		File file = null;
		if (target.exists() && target.isDirectory()) {// 目标文件存在并且为目录
			file = new File(target, source.getName());
		} else {
			file = target;
		}
		if (file.exists() && !replace) {
			return false;
		}
		try {
			IOUtil.readAndWrite(new FileInputStream(source), new FileOutputStream(file));
			return true;
		} catch (FileNotFoundException e) {
			LogUtil.IK.error("", e);
		}
		return false;
	}

	public static String[] readLine(File file, String charsetName) {
		if (file == null || !file.isFile()) {
			return null;
		}
		try {
			return readLine(new FileInputStream(file), charsetName);
		} catch (FileNotFoundException e) {
			LogUtil.IK.error("", e);
		}
		return null;
	}

	public static String[] readLine(InputStream input, String charsetName) {
		if (input == null) {
			return null;
		}
		Charset charset = CharsetUtil.forName(charsetName);
		InputStreamReader reader = null;
		if (charset == null) {
			reader = new InputStreamReader(input);
		} else {
			reader = new InputStreamReader(input, charset);
		}
		return IOUtil.readLine(new BufferedReader(reader));
	}

	public static boolean writeLine(File file, String[] lines) {
		return writeLine(file, null, lines);
	}

	public static boolean writeLine(File file, String charsetName, String[] lines) {
		return writeLine(file, charsetName, null, lines);
	}

	public static boolean writeLine(File file, String charsetName, Boolean append, String[] lines) {
		if (file == null) {
			return false;
		}
		try {
			FileOutputStream fos = null;
			if (append == null) {
				fos = new FileOutputStream(file);
			} else {
				fos = new FileOutputStream(file, append);
			}
			return writeLine(fos, charsetName, lines);
		} catch (FileNotFoundException e) {
			LogUtil.IK.error("", e);
		}
		return false;
	}

	public static boolean writeLine(OutputStream output, String charsetName, String[] lines) {
		if (output == null || lines == null) {
			return false;
		}
		Charset charset = CharsetUtil.forName(charsetName);
		OutputStreamWriter writer = null;
		if (charset == null) {
			writer = new OutputStreamWriter(output);
		} else {
			writer = new OutputStreamWriter(output, charset);
		}
		return IOUtil.writeLine(new BufferedWriter(writer), lines);
	}

	public static void main(String[] args) {
		File file = new File("aaatest.txt");
		String[] lines = { "aaa", "111", "", "666" };
		LogUtil.IK.info(FileUtil.writeLine(file, null, true, lines));
	}

}
