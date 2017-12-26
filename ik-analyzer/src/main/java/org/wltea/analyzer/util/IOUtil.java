package org.wltea.analyzer.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {

	public static boolean write(OutputStream output, byte[] b) {
		if (output == null) {
			return false;
		}
		try {
			if (b != null) {
				output.write(b);
				return true;
			}
		} catch (IOException e) {
			LogUtil.IK.error("", e);
		} finally {
			try {
				output.flush();
			} catch (IOException e) {
				LogUtil.IK.error("", e);
			}
			try {
				output.close();
			} catch (IOException e) {
				LogUtil.IK.error("", e);
			}
		}
		return false;
	}

	public static boolean readAndWrite(InputStream input, OutputStream output) {
		try {
			if (input != null) {
				byte[] b = new byte[1024];
				while (true) {
					int length = input.read(b);
					if (length == -1) {
						break;
					}
					if (output != null) {
						output.write(b, 0, length);
					}
				}
				return true;
			}
		} catch (IOException e) {
			LogUtil.IK.error("", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LogUtil.IK.error("", e);
				}
			}
			if (output != null) {
				try {
					output.flush();
				} catch (IOException e) {
					LogUtil.IK.error("", e);
				}
				try {
					output.close();
				} catch (IOException e) {
					LogUtil.IK.error("", e);
				}
			}
		}
		return false;
	}

	public static byte[] read(InputStream input) {
		if (input == null) {
			return null;
		}
		try {
			byte[] b = new byte[input.available()];
			input.read(b);
			return b;
		} catch (FileNotFoundException e) {
			LogUtil.IK.error("", e);
		} catch (IOException e) {
			LogUtil.IK.error("", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LogUtil.IK.error("", e);
				}
			}
		}
		return null;
	}

	public static boolean writeLine(BufferedWriter writer, String[] lines) {
		if (writer == null || lines == null) {
			return false;
		}
		try {
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
			return true;
		} catch (IOException e) {
			LogUtil.IK.error("", e);
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				LogUtil.IK.error("", e);
			}
		}
		return false;
	}

	public static String[] readLine(BufferedReader reader) {
		if (reader == null) {
			return null;
		}
		try {
			List<String> list = null;
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (list == null) {
					list = new ArrayList<String>();
				}
				list.add(line);
			}
			return list != null ? list.toArray(new String[list.size()]) : null;
		} catch (IOException e) {
			LogUtil.IK.error("", e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				LogUtil.IK.error("", e);
			}
		}
		return null;
	}
}
