package hhc.mllib.label.learn.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

/**
 * Created by huhuichao on 2017/12/4.
 */
public class AppConfig {

    private static AppConfig config;
    public JavaSparkContext sc;


    public AppConfig() {
        SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("classify spark");
        conf.set("spark.executor.memory", "2g"); // spark的运行配置，意指占用内存2G
        sc = new JavaSparkContext(conf);
    }

    /**
     *
     * @return
     */
    public static AppConfig getInstance() {
        if (config == null) {
            config = new AppConfig();
        }
        return config;
    }
}
