package hhc.mllib.label.learn.recommend.service;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;
import scala.Tuple2;

import java.util.List;

public interface SparkCollaborativeFilterService {


    /**
     * 为所用商品离线推荐TOP N商品
     *
     * @param num 推荐商品数
     */
    void recommendProductsForProducts(int num, Function2<Integer, List<Tuple2<Integer, Double>>, Void> callback);

    /**
     * 为所有用户离线计算推荐商品
     *
     * @param num 推荐商品数
     */
    JavaRDD<Tuple2<Object, Rating[]>> recommendProductsForUsers(int num);

    /**
     * 训练显性ALS模型
     *
     * @param rank       模型中隐藏因子的个数
     * @param iterations 迭代的次数，推荐值：10-20
     * @param lambda     惩罚函数的因数，是ALS的正则化参数，推荐值：0.01
     */
    void trainExplicitRecommendModel(int rank, int iterations, double lambda);

    /**
     * 重新训练显性ALS模型
     *
     * @param rank       模型中隐藏因子的个数
     * @param iterations 迭代的次数，推荐值：10-20
     * @param lambda     惩罚函数的因数，是ALS的正则化参数，推荐值：0.01
     */
    
    void trainIncrementalExplicitRecommendModel(int rank, int iterations, double lambda, RDD<Tuple2<Integer, double[]>> U, RDD<Tuple2<Integer, double[]>> V, long maxUserId, long maxItemId);

    /**
     * 重新训练显性ALS模型
     *
     * @param rank       模型中隐藏因子的个数
     * @param iterations 迭代的次数，推荐值：10-20
     * @param lambda     惩罚函数的因数，是ALS的正则化参数，推荐值：0.01
     */
    
    void reTrainExplicitRecommendModel(int rank, int iterations, double lambda);

    /**
     * 训练隐性ALS模型
     *
     * @param rank       模型中隐藏因子的个数
     * @param iterations 迭代的次数，推荐值：10-20
     * @param lambda     惩罚函数的因数，是ALS的正则化参数，推荐值：0.01
     * @param alpha      针对于隐性反馈 ALS 版本的参数，这个参数决定了偏好行为强度的基准
     */
    void trainImplicitRecommendModel(int rank, int iterations, double lambda, double alpha);

    /**
     * 重新训练隐形ALS模型
     *
     * @param rank       模型中隐藏因子的个数
     * @param iterations 迭代的次数，推荐值：10-20
     * @param lambda     惩罚函数的因数，是ALS的正则化参数，推荐值：0.01
     * @param alpha      针对于隐性反馈 ALS 版本的参数，这个参数决定了偏好行为强度的基准
     */
    void reTrainImplicitRecommendModel(int rank, int iterations, double lambda, double alpha);

    /**
     * 计算均方差
     *
     * @return MSE
     */
    double evaluateMSE();

    /**
     * 输出训练后的模型
     */
    void dumpModel();
}
