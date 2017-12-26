package hhc.common.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by huhuichao on 2017/11/22.
 */
public class RankerRequest implements Serializable {

    private static final long serialVersionUID = -1L;
    private UserProfile userProfile;
    private FilterSet filterSet;
    private String rankerName ;//默认Rank

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }


    public RankerRequest(UserProfile userProfile, FilterSet filterSet, String rankerName) {
        this.userProfile = userProfile;
        this.filterSet = filterSet;
        this.rankerName = rankerName;
    }

    public RankerRequest() {
    }

    public String getRankerName() {
        return rankerName;
    }

    public void setRankerName(String rankerName) {
        this.rankerName = rankerName;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public FilterSet getFilterSet() {
        return filterSet;
    }

    public void setFilterSet(FilterSet filterSet) {
        this.filterSet = filterSet;
    }
}
