package hhc.mllib.label.learn.recommend.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.mllib.evaluation.RegressionMetrics;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;
import org.apache.spark.rdd.RDD;
import org.jblas.DoubleMatrix;
import scala.Tuple2;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public class SparkCollaborativeFilterServiceImpl implements SparkCollaborativeFilterService, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log LOGGER = LogFactory.getLog(SparkCollaborativeFilterServiceImpl.class);

    private MatrixFactorizationModel recommender;

    private transient JavaSparkContext jsc;

    private RDD<Rating> ratingRDD;

    private JavaRDD<Rating> ratings;

    private JavaRDD<Tuple2<Object, Object>> userProducts;

    private RDD<Tuple2<Object, Object>> userProductsRDD;


    public SparkCollaborativeFilterServiceImpl(JavaSparkContext jsc, JavaRDD<Rating> ratings) {
        this.jsc = jsc;
        this.ratingRDD = JavaRDD.toRDD(ratings);
        this.ratings = ratings;
    }

    @Override
    public void trainExplicitRecommendModel(int rank, int iterations, double lambda) {
        if (recommender == null) {
            LOGGER.info("Begin train explicit ALS model......");
            this.recommender = ALS.train(ratingRDD, rank, iterations, lambda);
            LOGGER.info("Explicit ALS model train OK.");
        }
    }
    
    @Override
    public void trainIncrementalExplicitRecommendModel(int rank, int iterations, double lambda, RDD<Tuple2<Integer, double[]>> U, RDD<Tuple2<Integer, double[]>> V, long maxUserId, long maxItemId)
    {
        LOGGER.info("Begin train incremental explicit ALS model......");
        
        //this.recommender = ALS.train(ratingRDD, rank, iterations, lambda);
        //org.apache.spark.ml.recommendation.ALS.
        LOGGER.info("Explicit ALS model train OK.");
    }
    
    @Override
    public void reTrainExplicitRecommendModel(int rank, int iterations, double lambda) {
        LOGGER.info("Begin train explicit ALS model......");
        this.recommender = ALS.train(ratingRDD, rank, iterations, lambda);
        LOGGER.info("Explicit ALS model train OK.");
    }

    @Override
    public void trainImplicitRecommendModel(int rank, int iterations, double lambda, double alpha) {
        if (recommender == null) {
            LOGGER.info("Begin train implicit ALS model......");
            this.recommender = ALS.trainImplicit(ratingRDD, rank, iterations, lambda, alpha);
            LOGGER.info("Implicit ALS model train OK.");
        }
    }

    @Override
    public void reTrainImplicitRecommendModel(int rank, int iterations, double lambda, double alpha) {
        LOGGER.info("Begin train implicit ALS model......");
        this.recommender = ALS.trainImplicit(ratingRDD, rank, iterations, lambda, alpha);
        LOGGER.info("Implicit ALS model train OK.");
    }

    @Override
    public double evaluateMSE() {

        if (userProducts == null) {
            userProducts = ratings.map(
                    new Function<Rating, Tuple2<Object, Object>>() {
                        /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
                        public Tuple2<Object, Object> call(Rating r) {
                            return new Tuple2<Object, Object>(r.user(), r.product());
                        }
                    }
            );
            userProducts.cache();
            userProductsRDD = JavaRDD.toRDD(userProducts);
            userProductsRDD.cache();
        }

        JavaPairRDD<Tuple2<Integer, Integer>, Object> predictions = JavaPairRDD.fromJavaRDD(
                recommender.predict(userProductsRDD).toJavaRDD().map(
                        new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Object>>() {
                            /**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							@Override
                            public Tuple2<Tuple2<Integer, Integer>, Object> call(Rating r) {
                                return new Tuple2<Tuple2<Integer, Integer>, Object>(
                                        new Tuple2<>(r.user(), r.product()), r.rating());
                            }
                        }
                ));

        /*
        try {
			PrintStream out = new PrintStream("/tmp/ratings.txt");
			List<Rating> list = ratings.collect();
			for (Rating a: list){
				out.println(a.user() + "," + a.product() + "," + a.rating());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        try {
			PrintStream out = new PrintStream("/tmp/predictions.txt");
			List<Tuple2<Tuple2<Integer, Integer>, Object>> list = predictions.collect();
			for (Tuple2<Tuple2<Integer, Integer>, Object> a: list){
				out.println(a._1._1 + "," + a._1._2 + "," + a._2.toString());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        */
        
        JavaRDD<Tuple2<Object, Object>> ratesAndPreds =
                JavaPairRDD.fromJavaRDD(ratings.map(
                        new Function<Rating, Tuple2<Tuple2<Integer, Integer>, Object>>() {
                            /**
							 * 
							 */
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


    @Override
    public void recommendProductsForProducts(int num, Function2<Integer, List<Tuple2<Integer, Double>>, Void> callback) {
        long start = System.currentTimeMillis();
        try {
            LOGGER.info("Recommend products for products.....");
            JavaPairRDD<Object, double[]> productFeatures = JavaPairRDD.fromJavaRDD(recommender.productFeatures().toJavaRDD());
            for (Object key : productFeatures.keys().collect()) {

                double[] itemFactor = productFeatures.lookup(key).get(0);
                final DoubleMatrix itemVector = new DoubleMatrix(itemFactor);
                JavaPairRDD<Integer, Double> sims = JavaPairRDD.fromJavaRDD(productFeatures.map(
                        new Function<Tuple2<Object, double[]>, Tuple2<Integer, Double>>() {
                            /**
							 * 
							 */
							private static final long serialVersionUID = 1L;

							@Override
                            public Tuple2<Integer, Double> call(Tuple2<Object, double[]> v1) throws Exception {
                                DoubleMatrix factorVector = new DoubleMatrix(v1._2());
                                Double sim = factorVector.dot(itemVector); //cosineSimilarity(factorVector, itemVector);
                                return new Tuple2<>((Integer)v1._1(), sim);
                            }
                        }
                ));
                List<Tuple2<Integer, Double>> tops = sims.top(num, new TupleComparator());
                callback.call((Integer)key, tops);
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
        long end = System.currentTimeMillis();
        LOGGER.info("Recommend products for products"+ (end - start));
    }


    @Override
    public JavaRDD<Tuple2<Object, Rating[]>> recommendProductsForUsers(int num) {
        LOGGER.info("Recommend products for users......");
        
        RDD<Tuple2<Object, Rating[]>> tuple2RDD = recommender.recommendProductsForUsers(num);
        JavaRDD<Tuple2<Object, Rating[]>> tuple2JavaRDD = tuple2RDD.toJavaRDD();
        
        return tuple2JavaRDD;
        /*
        JavaPairRDD<Integer, Integer> usersRDD = ratings.mapToPair(new PairFunction<Rating, Integer, Integer>(){
			private static final long serialVersionUID = 1L;

			@Override
            public Tuple2<Integer, Integer> call(Rating rating) throws Exception {
            	return new Tuple2<Integer, Integer>(rating.user(), 0);
            }        	
        }).reduceByKey(new Function2<Integer, Integer, Integer>(){
			private static final long serialVersionUID = 1L;

			@Override
            public Integer call(Integer a, Integer b) throws Exception {
            	return a + b;
            }           	
        });
        List<Tuple2<Integer, Integer>> users = usersRDD.persist(StorageLevel.MEMORY_ONLY()).collect();
        
        for (Tuple2<Integer, Integer> u: users){
        	Rating[] ratings = recommender.recommendProducts(u._1, num);
        	String productList = "";
        	for (Rating rating: ratings){
        		if (!productList.isEmpty()){
        			productList += ',';
        		}
    			productList += Integer.valueOf(rating.product()).toString();
        	}
        	redisService.set(keyPrefix + ":" + Integer.valueOf(u._1).toString(), productList);
        }
        */
        
        // Generate the schema based on the string of schema
        //List<StructField> fields = Lists.newArrayList();
        //fields.add(DataTypes.createStructField("user_id", DataTypes.IntegerType, Boolean.TRUE));
        //fields.add(DataTypes.createStructField("recommend_item_id", DataTypes.IntegerType, Boolean.TRUE));
        //fields.add(DataTypes.createStructField("score", DataTypes.DoubleType, Boolean.TRUE));
        //fields.add(DataTypes.createStructField("gmt_create", DataTypes.TimestampType, Boolean.TRUE));
        //fields.add(DataTypes.createStructField("gmt_modified", DataTypes.TimestampType, Boolean.TRUE));
        //StructType schema = DataTypes.createStructType(fields);
        //JavaRDD<Row> buildRddSaleToMySql = tuple2JavaRDD.flatMap(new FlatMapFunction<Tuple2<Object, Rating[]>, Row>() {
        //    @Override
        //    public Iterable<Row> call(Tuple2<Object, Rating[]> objectTuple2) throws Exception {
        //        List<Row> rows = Lists.newArrayList();
        //        Integer userId = (Integer) objectTuple2._1();
        //        Rating[] recommendProducts = objectTuple2._2();
        //        for (Rating recommendProduct : recommendProducts) {
        //            rows.add(RowFactory.create(userId, recommendProduct.product(), recommendProduct.rating(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
        //        }
        //        return rows;
        //    }
        //});
        //DataFrame dataFrame = hiveContext.createDataFrame(buildRddSaleToMySql, schema);
        //dataFrame.write().mode(SaveMode.Overwrite).jdbc(DBConfig.get(RecommendConfig.REC_DB_WRITE_URL), Table.CF_RECOMMEND_RESULT, PropertiesUtil.buildDBProperties(DBConfig.get(RecommendConfig.REC_DB_WRITE_USER), DBConfig.get(RecommendConfig.REC_DB_WRITE_PWD)));
    }


    private double cosineSimilarity(DoubleMatrix vec1, DoubleMatrix vec2) {
        return vec1.dot(vec2) / (vec2.norm2() * vec1.norm2());
    }

    private class TupleComparator implements Comparator<Tuple2<Integer, Double>>, Serializable {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public int compare(Tuple2<Integer, Double> tuple1, Tuple2<Integer, Double> tuple2) {
            if (tuple1._2() < tuple2._2()) {
                return -1;
            } else if (Objects.equals(tuple1._2(), tuple2._2())) {
                return 0;
            } else {
                return 1;
            }
        }
    }



	@Override
	public void dumpModel() {
		JavaRDD<Tuple2<Object, double[]>> rdd;
		List<Tuple2<Object, double[]>> list;
		PrintStream out;
		
		try {
			out = new PrintStream("/tmp/dump_model.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		out.println("ratingRDD:");
		List<Rating> ratings = this.ratings.collect();
		for (Rating r: ratings){
			out.printf("%d,%d,%f\n", r.user(), r.product(), r.rating());
		}
		out.println();
		
		out.println("userFeatures:");
		rdd = this.recommender.userFeatures().toJavaRDD();
		list = rdd.collect();
		for (Tuple2<Object, double[]> i: list){
			out.print(i._1.toString());
			for (double j: i._2){
				out.print(" " + j);
			}
			out.println();
		}
		out.println();
		
		out.println("productFeatures:");
		rdd = this.recommender.productFeatures().toJavaRDD();
		list = rdd.collect();
		for (Tuple2<Object, double[]> i: list){
			out.print(i._1.toString());
			for (double j: i._2){
				out.print(" " + j);
			}
			out.println();
		}
		
		out.close();
	}
    
    
}