package com.ansj.vec.learn;

import com.ansj.vec.Learn;
import hhc.common.utils.io.FileOperater;
import hhc.common.utils.io.MyCallBack;
import hhc.common.utils.stringUtils.ProcessUtil;
import org.wltea.analyzer.util.TokenizerUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Word2VecLearn implements MyCallBack {

	private static String outPutPath;

	public static void parserFile(String path, String file) throws FileNotFoundException, IOException {
		outPutPath = path;
		FileOperater.readline(file, new Word2VecLearn());
	}

	public static void parserFile(String path, String file, String encode) throws FileNotFoundException, IOException {
		outPutPath = path;
		FileOperater.readline(file, new Word2VecLearn(), encode);
	}

	public static void parserFiles(String path, String files) {
		outPutPath = path;
		File fa[] = new File(files).listFiles();

		if (fa != null) {
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (!fs.isDirectory()) {

					System.out.println(files + "/" + fs.getName());
					try {
						parserFile(path, files + "/" + fs.getName(), "GBK");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void paserStr(String path, String title) throws IOException {
		StringBuilder sb1 = new StringBuilder();

		List<String> terms = TokenizerUtils.getIKTokenizer(false).analyzer(title);
		for (String str : terms) {
			sb1.append(str);
			sb1.append(" ");
		}
		sb1.append("\n");

		FileOperater.write(path, sb1.toString());
	}

	public void operation(String string, Object object) {
		if (string.startsWith("<docno>") || string.startsWith("<doc>") || string.startsWith("</doc>")
				|| string.startsWith("<url>")) {
			return;
		}
		if (string.startsWith("<contenttitle>")) {
			string = string.replace("<contenttitle>", "").replace("</contenttitle>", "");
			string = ProcessUtil.convertToHalfWidth(string);
			System.out.println(string);
		}
		if (string.startsWith("<content>")) {
			string = string.replace("<content>", "").replace("</content>", "");
			string = ProcessUtil.convertToHalfWidth(string);
			System.out.println(string);
		}

		if (string.trim().length() == 0) {
			return;
		}
		try {
			paserStr(outPutPath, string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void learn(String input, String outpath) {
		Learn lean = new Learn();
		lean.setLayerSize(20);
		outPutPath = input;
		try {
			lean.learnFile(new File(outPutPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		lean.saveModel(new File(outpath));
	}

	public static void learn(String input, String outpath, String file) {
		Learn lean = new Learn();
		try {
			parserFile(input, file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// lean.setLayerSize(layerSize)
		try {
			lean.learnFile(new File(outPutPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		lean.saveModel(new File(outpath));
	}

	public static void createFile() {

		System.out.println("SogouCA.reduced");
		Word2VecLearn.parserFiles("/Users/syp/Documents/tmp/sogouCA.txt", "/Users/syp/Downloads/SogouCA.reduced 2/");
//
//		try {
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt",
//					"/Users/syp/Documents/tmp/region-name.txt");
//			System.out.println("region-name.txt");
//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt", "/Users/syp/Documents/tmp/brand.txt");
//			System.out.println("brand.txt");
//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt",
//					"/Users/syp/Documents/tmp/category.txt");
//			System.out.println("category.txt");
//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt", "/Users/syp/Documents/tmp/plaza.txt");
//
//			System.out.println("plaza.txt");
//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt",
//					"/Users/syp/Documents/tmp/streetCNName.csv");
//			System.out.println("streetCNName.csv");
//			//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt", "/Users/syp/Documents/tmp/city.csv");
//			System.out.println("city.csv");
//			//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt",
//					"/Users/syp/Documents/tmp/genreextends.csv");
//			System.out.println("genreextends.csv");
//			//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt", "/Users/syp/Documents/tmp/genre.csv");
//			System.out.println("genre.csv");
//			//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt",
//					"/Users/syp/Documents/tmp/streetname.csv");
//
//			Word2VecLearn.parserFile("/Users/syp/Documents/tmp/verctor-ik.txt",
//					"/Users/syp/Documents/tmp/streetname.csv");
//
//			System.out.println("streetname");
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public static void main(String[] arg) {
		Word2VecLearn.createFile();
		//Word2VecLearn.learn("/Users/syp/Documents/tmp/verctor-keyword.txt", "/Users/syp/Documents/tmp/word2vec-20.mod");
		// Word2VecLearn.learn("/Users/syp/Documents/tmp/verctor-ik.txt",
		// "/Users/syp/Documents/tmp/word2vec-ik.mod");
	}

}
