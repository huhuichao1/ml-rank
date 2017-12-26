package com.ansj.vec.learn;

import com.ansj.vec.Word2VEC;
import com.ansj.vec.domain.WordEntry;
import hhc.common.utils.io.FileOperater;
import hhc.common.utils.io.MyCallBack;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Word2VecHandler {

	static String sW2vModelPath = "word2vec/model-path";
	private static Word2VecHandler handler;
	private Word2VEC w2v;

	private Word2VecHandler() {

		w2v = new Word2VEC();
		String path = Word2VecHandler.class.getClassLoader().getResource(sW2vModelPath).toString().replace("file:", "");
		MyCallBack callback = new MyCallBack() {

			public void operation(String string, Object object) {
				// TODO Auto-generated method stub
				sW2vModelPath = string.toLowerCase().trim();
			}

		};
		System.out.println("init word2vec ...");

		FileOperater.readline(path, callback);

		try {
			w2v.loadJavaModel(sW2vModelPath);
			System.out.println("over...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Word2VecHandler getInstance() {
		if (handler == null)
			handler = new Word2VecHandler();
		return handler;

	}

	public Set<WordEntry> distance(List<String> words) {
		return w2v.distance(words);

	}

	public Set<WordEntry> distance(String words) {
		return w2v.distance(words);

	}

	public TreeSet<WordEntry> analogy(String word0, String word1, String word2) {
		return w2v.analogy(word0, word1, word2);

	}

	public float[] getWordVector(String word) {
		return w2v.getWordVector(word);
	}

	public static void main(String[] arg) {
		long start = System.currentTimeMillis();
		Word2VecHandler handler = Word2VecHandler.getInstance();
		System.out.println("=======加载时间：" + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		handler.distance("万达广场");
		System.out.println("=======distance 时间：" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		float[] f = handler.getWordVector("万达广场");
		System.out.println("=======getWordVector 时间：" + (System.currentTimeMillis() - start));
		if (f != null) {
			print(f);
		}

		start = System.currentTimeMillis();
		f = handler.getWordVector("青岛万达");
		System.out.println("=======getWordVector 时间：" + (System.currentTimeMillis() - start));
		if (f != null) {
			print(f);
		} else {
			System.out.println("=======青岛 万达 getWordVector is null");
		}

		start = System.currentTimeMillis();
		f = handler.getWordVector("万达影院");
		System.out.println("=======getWordVector 时间：" + (System.currentTimeMillis() - start));
		if (f != null) {
			print(f);
		}
	}

	public static void print(float[] f) {
		System.out.println("size=" + f.length);
		for (float fl : f) {
			System.out.print(fl);
			System.out.print("\t");
		}
	}
}
