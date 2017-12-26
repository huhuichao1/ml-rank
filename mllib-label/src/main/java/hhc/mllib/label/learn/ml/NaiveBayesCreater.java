package hhc.mllib.label.learn.ml;


import hhc.common.entity.TrainsStruct;
import hhc.common.utils.io.FileOperater;
import hhc.mllib.label.learn.config.AppConfig;
import org.apache.log4j.Level;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class NaiveBayesCreater extends CreaterBase {





	public static NaiveBayesModel train(JavaSparkContext sc, List<TrainsStruct> struct,String path) {
		if (struct == null || struct.size() == 0) {
			return null;
		}

		List<LabeledPoint> lp = new LinkedList<LabeledPoint>();
		for (TrainsStruct ts : struct) {
			LabeledPoint pos = new LabeledPoint(ts.label, Vectors.dense(ts.vec));
			lp.add(pos);
		}
		JavaRDD<LabeledPoint> training = sc.parallelize(lp);
		NaiveBayesModel nb_model = NaiveBayes.train(training.rdd(),1,"multinomial");
		nb_model.save(sc.sc(), path);
		return nb_model;

	}

	/**
	 * 
	 * @param sc
	 * @param struct
	 * @param path
	 * @return
	 */
	public static NaiveBayesModel trainAndSave(JavaSparkContext sc, List<TrainsStruct> struct, String path) {
		if (struct == null || struct.size() == 0) {
			return null;
		}

		List<LabeledPoint> lp = new LinkedList<LabeledPoint>();
		for (TrainsStruct ts : struct) {
			LabeledPoint pos = new LabeledPoint(ts.label, Vectors.dense(ts.vec));
			lp.add(pos);

		}
		JavaRDD<LabeledPoint> training = sc.parallelize(lp);
		double[] w = { 0.8d, 0.2d };
		JavaRDD<LabeledPoint>[] rdds = training.randomSplit(w, 0);

		// rdds.
		// training.
		NaiveBayesModel nb_model = NaiveBayes.train(rdds[0].rdd());

		JavaRDD<Double> jd = rdds[1].map(new org.apache.spark.api.java.function.Function<LabeledPoint, Double>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Double call(LabeledPoint arg0) throws Exception {
				// TODO Auto-generated method stub
				// return nb_model.(arg0.features());
				double res = nb_model.predict(arg0.features());
				double[] v = nb_model.predictProbabilities(arg0.features()).toArray();

				System.out.println(res +"\t"+arg0.label()+"\t"+Arrays.toString(v));
				FileOperater.write("/Users/syp/Documents/tmp/classfication/tset.txt", res +"\t"+arg0.label()+"\t"+Arrays.toString(v));

				if (res - arg0.label() < 0.00000001) {
					return 1d;
				}
				// System.out.println(Arrays.toString(res));
//				FileOperater.write("/Users/syp/Documents/tmp/classfication/tset.txt", Arrays.toString(res));
				return 0d;

			}
		});

		List<Double> list = jd.collect();
		double len = 0.0;
		for(Double l:list){
			len+=l;
		}
		System.out.println((len/list.size()));
		
		// nb_model.
		// NaiveBayes.

//		nb_model.save(JavaSparkContext.toSparkContext(sc), path);
		return nb_model;

	}

	public static NaiveBayesModel loadFromFile(JavaSparkContext sc, String path) {
		return NaiveBayesModel.load(JavaSparkContext.toSparkContext(sc), path);
	}



	public static double accuracyPrediction(JavaRDD<LabeledPoint> testing,final NaiveBayesModel model){
		JavaRDD<Double> jd = testing.map(new Function<LabeledPoint, Double>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Double call(LabeledPoint arg0) throws Exception {
				// TODO Auto-generated method stub
				// return nb_model.(arg0.features());
				double res = model.predict(arg0.features());
				double[] v = model.predictProbabilities(arg0.features()).toArray();

				System.out.println(res +"\t"+arg0.label()+"\t"+Arrays.toString(v));
//				FileOperater.write("/Users/syp/Documents/tmp/classfication/tset.txt", res +"\t"+arg0.label()+"\t"+Arrays.toString(v));

				if (res - arg0.label() < 0.00000001) {
					return 1d;
				}
				// System.out.println(Arrays.toString(res));
//				FileOperater.write("/Users/syp/Documents/tmp/classfication/tset.txt", Arrays.toString(res));
				return 0d;

			}
		});

		List<Double> list = jd.collect();
		double len = 0.0;
		for(Double l:list){
			len+=l;
		}
		return len/list.size();
	}

	public static void main(String arg[]) {

		org.apache.log4j.Logger.getRootLogger().setLevel(Level.WARN);
//		JavaRDD<LabeledPoint> inputData = NaiveBayesCreater.getJavaRDD("data/ml/classifi/bayes/sample_naive_bayes_data.txt", AppConfig.getInstance().sc);
//
//		//把数据划分成训练样本和测试样本
//		JavaRDD<LabeledPoint>[] tmp = inputData.randomSplit(new double[]{0.6, 0.4}, 0);
//
//		JavaRDD<LabeledPoint> training = tmp[0]; // training set
//		JavaRDD<LabeledPoint> testing = tmp[1]; // test set
//		List<LabeledPoint> train=training.collect();
//		List<LabeledPoint> test=testing.collect();
//
//		System.out.println("train:"+train);
//		System.out.println("test:"+test);
//		//新建贝叶斯模型，并训练
//		final NaiveBayesModel model = NaiveBayes.train(training.rdd(), 0.05);
//		System.out.println(accuracyPrediction(testing,model));
//		model.save(JavaSparkContext.toSparkContext(AppConfig.getInstance().sc), "data/ml/classifi/bayes/");

//		NaiveBayesCreater.trainAndSave(AppConfig.getInstance().sc, NaiveBayesCreater.getTrainFromFile("data/ml/classifi/bayes/sample_naive_bayes_data.txt"), "data/ml/classifi/bayes/");
		NaiveBayesModel modell=loadFromFile(AppConfig.getInstance().sc,"data/ml/classifi/bayes/");
		System.out.println("预测测试集。。");
		double res= modell.predict(Vectors.dense(new double[]{0 ,0 ,6}));
		System.out.println(res);

	}

}

