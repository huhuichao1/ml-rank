package hhc.mllib.label.learn.recommend;

import hhc.mllib.label.learn.config.AppConfig;
import hhc.mllib.label.learn.ml.CreaterBase;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.evaluation.RegressionMetrics;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huhuichao on 2017/12/7.
 */
public class ALSModelCreater extends CreaterBase{



    private MatrixFactorizationModel model;

    private transient JavaSparkContext jsc;


    public ALSModelCreater(JavaSparkContext jsc) {
        this.jsc = jsc;
    }

    /**
     * 读取样本数据
     * @param path
     * @return
     */
    public static JavaRDD<Rating> getALSJavaRDD(String path, JavaSparkContext sc,String split) {
        JavaRDD<String> data=sc.textFile(path);
        JavaRDD<Rating> ratings = data.map(
                new Function<String, Rating>() {
                    public Rating call(String s) {
                        String[] sarray = s.split(split);
                        return new Rating(Integer.parseInt(sarray[0]), Integer.parseInt(sarray[1]),
                                Double.parseDouble(sarray[2]));
                    }
                }
        );
        return ratings;
    }


    public  MatrixFactorizationModel training (JavaRDD<Rating> ratings,int rank, int numIterations, double v){
        return  ALS.train(ratings.rdd(), rank, numIterations, v);
    }

    /**
     *
     * 计算方差
     * @param ratings  样本数据
     *  @param model  model
     * @return
     */
    public static double evaluateMSE(JavaRDD<Rating> ratings,MatrixFactorizationModel model) {

        JavaRDD<Tuple2<Object, Object>>   userProducts = ratings.map(
                    new Function<Rating, Tuple2<Object, Object>>() {
                        private static final long serialVersionUID = 1L;
                        @Override
                        public Tuple2<Object, Object> call(Rating r) {
                            return new Tuple2<Object, Object>(r.user(), r.product());
                        }
                    }
            );
        JavaPairRDD<Tuple2<Integer, Integer>, Object> predictions = JavaPairRDD.fromJavaRDD(
                model.predict( JavaRDD.toRDD(userProducts)).toJavaRDD().map(
                        new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Object>>() {
                            private static final long serialVersionUID = 1L;
                            @Override
                            public Tuple2<Tuple2<Integer, Integer>, Object> call(Rating r) {
                                return new Tuple2<Tuple2<Integer, Integer>, Object>(
                                        new Tuple2<>(r.user(), r.product()), r.rating());
                            }
                        }
                ));
        JavaRDD<Tuple2<Object, Object>> ratesAndPreds =
                JavaPairRDD.fromJavaRDD(ratings.map(
                        new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Object>>() {
                            private static final long serialVersionUID = 1L;
                            @Override
                            public Tuple2<Tuple2<Integer, Integer>, Object> call(Rating r) {
                                return new Tuple2<Tuple2<Integer, Integer>, Object>(
                                        new Tuple2<>(r.user(), r.product()), r.rating());
                            }
                        }
                )).join(predictions).values();

        // Create regression metrics object
        RegressionMetrics regressionMetrics = new RegressionMetrics(ratesAndPreds.rdd());
        return regressionMetrics.meanSquaredError();
    }


    /**
     * 获取矩阵分解后的物品特征矩阵
     * @param model
     * @return
     */
    public static JavaPairRDD<Object, double[]>  getProductPeatures(MatrixFactorizationModel model){
        return JavaPairRDD.fromJavaRDD(model.productFeatures().toJavaRDD());
    }


    /**
     * recommendProductsForUsers  对所有用户推荐物品，取前n个物品
     * @param num
     * @param model
     * @return
     */
    public static JavaPairRDD<Object, Rating[]> recommendProductsForUsers(int num,MatrixFactorizationModel model) {

        RDD<Tuple2<Object, Rating[]>> tuple2RDD = model.recommendProductsForUsers(num);
        JavaRDD<Tuple2<Object, Rating[]>> tuple2JavaRDD = tuple2RDD.toJavaRDD();
        JavaPairRDD<Object, Rating[]> productFeatures=JavaPairRDD.fromJavaRDD(tuple2JavaRDD);
        return productFeatures;
    }
    public static void main(String[] args) {
//        ALSModelCreater alsModel=new ALSModelCreater(AppConfig.getInstance().sc);
//        //读取样本数据
//        JavaRDD<Rating> ratings= getALSJavaRDD("data/ml/recommend/als/test.data", alsModel.jsc,",");
////        List<Rating> list=ratings.collect();
//        //建立模型
//        int rank=10;
//        int numIterations=5;
//        MatrixFactorizationModel model = ALS.train(ratings.rdd(), rank, numIterations, 0.01);
//
//        System.out.println("Mean Squared Error = " + evaluateMSE(ratings,model));
//        model.save(alsModel.jsc.sc(),"data/ml/recommend/als/model");
        MatrixFactorizationModel model=MatrixFactorizationModel.load(AppConfig.getInstance().sc.sc(),"data/ml/recommend/als/model");
        System.out.println(Arrays.toString(model.recommendProducts(4,2)));

        JavaPairRDD<Object, double[]> productFeatures = getProductPeatures(model);
        List<Tuple2<Object, double[]>> list=productFeatures.collect();
        System.out.println(list);

        JavaPairRDD<Object, Rating[]> features=recommendProductsForUsers(2,model);
        List<Tuple2<Object, Rating[]>> list1=features.collect();
        System.out.println(list1);
    }


}
