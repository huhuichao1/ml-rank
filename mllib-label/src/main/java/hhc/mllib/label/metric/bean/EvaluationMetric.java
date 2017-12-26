package hhc.mllib.label.metric.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by huhuichao on 2017/12/6.
 */
public class EvaluationMetric {

    public Double recall;
    public Double precision;
    public Double accuracy;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
