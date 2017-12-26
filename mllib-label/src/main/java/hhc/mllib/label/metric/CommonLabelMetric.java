package hhc.mllib.label.metric;

import hhc.mllib.label.metric.bean.EvaluationMetric;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * TP（true positive）：表示样本的真实类别为正，最后预测得到的结果也为正；
 FP（false positive）：表示样本的真实类别为负，最后预测得到的结果却为正；
 FN（false negative）：表示样本的真实类别为正，最后预测得到的结果却为负；
 TN（true negative）：表示样本的真实类别为负，最后预测得到的结果也为负.
 * 度量两个通用的结果集
 * 参考博客：http://www.cnblogs.com/sddai/p/5696870.html
 * <p>
 * Created by huhuichao on 2017/12/6.
 */
public class CommonLabelMetric {

    /**
     * 求acc值 accuracy  适用于多分类测试集合
     *
     * @param result model ranker 预测结果集
     * @param metric 测试集
     * @return
     */
    public static double accuracyMetric(List<Double> result, List<Double> metric) {

        if (result.size() != metric.size()) {
            return 0d;
        }
        double len = 0.0;
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i).equals(metric.get(i))){
                len++;
            }
        }
        return len / result.size();
    }


    /**
     * 求P值 precision
     * <p>
     * 正确被检索的item(TP)"占所有"实际被检索到的(TP+FP)"的比例.
     *
     * @param result model ranker 预测结果集
     * @param metric 测试集
     * @param item   度量值
     * @return
     */
    public static EvaluationMetric predictionMetric(List<Double> result, List<Double> metric, Double item) {

        int size = result.size();
        if (size != metric.size()) {
            return null;
        }
        EvaluationMetric em=new EvaluationMetric();
        double TP = 0.0;
        double FP = 0.0;
        double FN = 0.0;
        double TN = 0.0;

        for (int i = 0; i < size; i++) {

            //最后预测得到的结果也为正；
            if(result.get(i).equals(item)){
                //表示样本的真实类别为正，
                if(metric.get(i).equals(item)){
                    TP++;
                }else {
                    //表示样本的真实类别为负，
                    FP++;
                }

            //最后预测得到的结果也为负；
            }else{
                //表示样本的真实类别为正
                if(metric.get(i).equals(item)){
                    FN++;
                }else{
                    //表示样本的真实类别为负
                    TN++;
                }
            }
        }
        em.precision=TP/(TP+FP);
        em.recall=TP/(TP+FN);
        em.accuracy=(TP+TN)/(TP+FP+TN+FN);
        return em;
    }


    public static void main(String[] args) {
        List<Double> result=new ArrayList<>();
        result.add(1d);result.add(1d);result.add(0d);result.add(0d);result.add(0d);result.add(1d);

        List<Double> predict=new ArrayList<>();
        predict.add(0d);predict.add(1d);predict.add(1d);predict.add(1d);predict.add(0d);predict.add(1d);
        System.out.println(CommonLabelMetric.predictionMetric(result,predict,1d));
        System.out.println(CommonLabelMetric.accuracyMetric(result,predict));;
    }





}
