package hhc.mllib.label.learn.ml;

import hhc.common.entity.TrainsStruct;
import hhc.common.utils.io.FileOperater;
import hhc.common.utils.io.MyCallBack;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CreaterBase {
//	protected static JavaSparkContext sc;
//	static {
//		SparkConf conf = new SparkConf();
//		conf.setAppName("test spark");
//		conf.setMaster("local");
//		conf.set("spark.executor.memory", "2g"); // spark的运行配置，意指占用内存2G
//		sc = new JavaSparkContext("local[*]", "Sparks", conf);
//		
//	}


    /**
     * 读取样本数据
     * @param path
     * @return
     */
    public static JavaRDD<LabeledPoint> getJavaRDD(String path, JavaSparkContext sc) {
        List<LabeledPoint> lp = new LinkedList<LabeledPoint>();
        MyCallBack callback = new MyCallBack() {

            @Override
            public void operation(String string, Object object) {
                String[] s = string.split(",");
                if(s.length!=2){
                    return;
                }

                String[] strs = s[1].split(" ");
                double[] dlist = new double[strs.length];
                int index = 0;
                for (String ss : strs) {
                    dlist[index] = Double.valueOf(ss);
                    index++;
                }
                LabeledPoint struct = new LabeledPoint( Double.valueOf(s[0]), Vectors.dense(dlist));
                lp.add(struct);
            }
        };

        FileOperater.readline(path, callback);
        return sc.parallelize(lp);
    }



    /**
     * 读取样本数据
     * @param path
     * @return
     */
    public static List<TrainsStruct> getTrainFromFile(String path) {
        List<TrainsStruct> list = new ArrayList<TrainsStruct>();
        MyCallBack callback = new MyCallBack() {

            @Override
            public void operation(String string, Object object) {
                TrainsStruct struct = new TrainsStruct();
                String[] s = string.split(",");
                if(s.length!=2){
                    return;
                }

                struct.label = Float.valueOf(s[0]);
                String[] strs = s[1].split(" ");
                double[] dlist = new double[strs.length];
                int index = 0;
                for (String ss : strs) {
                    dlist[index] = Double.valueOf(ss);
                    index++;
                }
                struct.vec = dlist;
                list.add(struct);
            }
        };

        FileOperater.readline(path, callback);
        return list;
    }
}
