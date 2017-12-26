package hhc.mllib.label.learn.ml;

import hhc.common.entity.TrainsStruct;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.SVMModel;
import org.apache.spark.mllib.classification.SVMWithSGD;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.util.LinkedList;
import java.util.List;

/**
 * 要求二分类 标签必须是0，1
 * @author syp
 *
 */
public class SVMModelCreater extends CreaterBase {

	public static SVMModel train(JavaSparkContext sc, List<TrainsStruct> struct, int numIterations) {
		if (struct == null || struct.size() == 0) {
			return null;
		}

		List<LabeledPoint> lp = new LinkedList<LabeledPoint>();
		for (TrainsStruct ts : struct) {
			LabeledPoint pos = new LabeledPoint(ts.label, Vectors.dense(ts.nonNagtiveVec));
			lp.add(pos);
		}
		JavaRDD<LabeledPoint> training = sc.parallelize(lp);
		SVMModel svm_model = SVMWithSGD.train(training.rdd(), numIterations);
		return svm_model;

	}

	public static SVMModel trainAndSave(JavaSparkContext sc,List<TrainsStruct> struct, int numIterations, String path) {
		if (struct == null || struct.size() == 0) {
			return null;
		}

		List<LabeledPoint> lp = new LinkedList<LabeledPoint>();
		for (TrainsStruct ts : struct) {
			LabeledPoint pos = new LabeledPoint(ts.label, Vectors.dense(ts.nonNagtiveVec));
			lp.add(pos);
		}
		JavaRDD<LabeledPoint> training = sc.parallelize(lp);
		SVMModel nb_model = SVMWithSGD.train(training.rdd(), numIterations);
		nb_model.save(JavaSparkContext.toSparkContext(sc), path);
		return nb_model;

	}

	public static SVMModel loadFromFile(JavaSparkContext sc,String path) {
		return SVMModel.load(JavaSparkContext.toSparkContext(sc), path);
	}
}
