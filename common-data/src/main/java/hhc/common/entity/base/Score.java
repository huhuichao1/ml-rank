package hhc.common.entity.base;

/**
 * Created by huhuichao on 2017/8/14.
 */
public class Score {
    private double score;
    private String explain;


    public Score(double score,String explain){
        this.score=score;
        this.explain=explain;
    }

    public Score(){}

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
