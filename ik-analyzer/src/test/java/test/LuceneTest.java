package test;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.dic.Dict;
import org.wltea.analyzer.dic.loader.FileDictLoader;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneTest {
	private static Version version = Version.LUCENE_4_10_3;
	// private Analyzer standard = new StandardAnalyzer();
	// private Analyzer ikSmart = new IKAnalyzer(true, false);
	private static Analyzer ik = new IKAnalyzer(false);
	private static Directory directory;
	private static DirectoryReader reader;
	static {
		Dict.load(new FileDictLoader());// 先加载一次默认词库
		// Dict.addDictLoader(new JdbcDictLoader());// 设置jdbc加载器
		// Dict.load();// 加载一次词库
		// Dict.update();// 定时更新词库

		// try {
		// directory = FSDirectory.open(new File("index"));// 64位用MMapDirectory，NativeFSLockFactory
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public static void write() {
		IndexWriterConfig config = new IndexWriterConfig(version, ik);
		// config.setOpenMode(OpenMode.CREATE);
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, config);
			for (int i = 0; i < 3; i++) {
				Document doc = new Document();
				doc.add(new TextField("title", "我们是中國人长春天气候鸟" + i, Store.YES));
				doc.add(new StringField("author", "小不点", Store.YES));
				doc.add(new StringField("no", String.valueOf(i), Store.YES));
				doc.add(new LongField("age", i, Store.YES));
				FieldType type = new FieldType();
				type.setIndexed(true);
				type.setStored(true);
				type.setTokenized(true);
				doc.add(new Field("test", "apple tree hello" + i, type));
				writer.addDocument(doc);
				writer.commit();
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.commit();
					writer.close();
				} catch (CorruptIndexException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static IndexSearcher getIndexSearcher() {
		if (reader == null) {
			try {
				reader = DirectoryReader.open(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				reader = DirectoryReader.openIfChanged(reader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new IndexSearcher(reader);
	}

	public static void analyze(String text) {
		try {
			IKAnalyzerTest.tokenStream(ik.tokenStream("", text));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		analyze("我是中国人");
		analyze("长春天气");
	}
}
