package hhc.mllib.label.learn.recommend;

import hhc.common.utils.stringUtils.StringUtil;
import hhc.mllib.label.learn.config.AppConfig;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.recommendation.ALS;
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel;
import org.apache.spark.mllib.recommendation.Rating;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by huhuichao on 2017/12/7.
 * <p>
 * 电影推荐
 */
public class MoiveRecommender  implements Serializable{

    private static final long serialVersionUID = 8257474753227515107L;

    private static String output="data/ml/recommend/als/moive.txt";
    private static String input = "data/ml/recommend/als/u.data";;
    private static String userDataInput = "data/ml/recommend/als/u.user";;
    private JavaSparkContext sc;

    private static int numRecommender=10;//推荐多少条

    private static int numIterations = 5;  //迭代次数
    private static Double lambda = 0.01;   // 正则因子（推荐0.01）
    private static int rank = 10;           //  特征数量
    private static int numUserBlocks = -1;
    private static int numProductBlocks = -1;
    private static Boolean implicitPrefs = false;


    public MoiveRecommender(AppConfig appConfig) {
        this.sc = appConfig.sc;
    }

    public static void main(String[] args) {


        if (args.length < 1) {

            System.out.println(" parameter not null..");
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].compareTo("-input") == 0)
                input = args[++i];
            else if (args[i].compareTo("-output") == 0)
                output = args[++i];
            else if (args[i].compareTo("-numIterations") == 0)
                numIterations = Integer.parseInt(args[++i]);
            else if (args[i].compareTo("-lambda") == 0)
                lambda = Double.parseDouble(args[++i]);
            else if (args[i].compareTo("-rank") == 0)
                rank = Integer.parseInt(args[++i]);
            else if (args[i].compareTo("-numUserBlocks") == 0)
                numUserBlocks = Integer.parseInt(args[++i]);
            else if (args[i].compareTo("-numProductBlocks") == 0)
                numProductBlocks = Integer.parseInt(args[++i]);
            else if (args[i].compareTo("-implicitPrefs") == 0)
                implicitPrefs = Boolean.parseBoolean(args[++i]);
            else if (args[i].compareTo("-numIterations") == 0)
                userDataInput = args[++i];
            else if (args[i].compareTo("-numRecommender") == 0)
                numRecommender = Integer.parseInt(args[++i]);
            else {
                System.out
                        .println("Unknown command-line parameter: " + args[i]);
                System.out.println("System will now exit.");
                System.exit(1);
            }
        }
        MoiveRecommender recommender = new MoiveRecommender(AppConfig.getInstance());
        recommender.training(input,userDataInput);
    }

    private void training(String input,String userDataInput) {
        JavaRDD<Rating> ratings= ALSModelCreater.getALSJavaRDD(input,sc,"\t");
        MatrixFactorizationModel model = ALS.train(ratings.rdd(), rank, numIterations, lambda);
        if(!StringUtil.isEmpty(userDataInput)){
            JavaRDD<User> test=getUserJavaRdd(userDataInput,sc);
            List<User> users=test.collect();

            for(User user:users){
                Rating [] rating=model.recommendProducts(user.getUserId(),numRecommender);
                write(output,rating);
            }
        }
    }

    private JavaRDD<User> getUserJavaRdd(String path, JavaSparkContext sc) {
        JavaRDD<String> data=sc.textFile(path);
        JavaRDD<User> users = data.map(
                new Function<String, User>() {
                    private static final long serialVersionUID = 8257474753227515107L;

                    public User call(String s) {
                        String[] sarray = s.split("\\|");
                        return new User(Integer.parseInt(sarray[0]), Integer.parseInt(sarray[1]),
                               sarray[2],sarray[3],sarray[4]);
                    }
                }
        );
        return users;
    }


    public  void write(final String path, Rating [] ratings) {

        try {
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);
            StringBuffer sb = new StringBuffer();
            for(Rating  rating:ratings){

                sb.append(rating.user()+ "\r"+rating.product()+"\r"+rating.rating());
            }
            out.write(sb.toString().getBytes("utf-8"));
            // }
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
