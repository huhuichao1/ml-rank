package hhc.common.entity;


import hhc.common.entity.base.KeyValuePair;

import java.io.Serializable;
import java.util.List;

/**
 * @author huangzhiqiang
 * @param <V>
 * @param <K>
 */
public class UserProfile implements Serializable {
    private static final long  serialVersionUID = 7530852543920578473L;
    private String             userId;                                 // 用户id
    private int                age;                                    // 用户年龄
    private int                gender;                                 // 用户性别
    private long               registerTime;                           // 注册时间
    private String             targetCity;                             // 展示哪个城市的内容
    private int                impressionCnt;                          // 总共展示过多少文章
    private int                clickCnt;                               // 总共点击过多少文章
    private List<KeyValuePair> topic64Preference;                      // topic特征信息
    private List<KeyValuePair> topic64ImpressionCnt;                   // 每个topic特征展示过多少次
    private List<KeyValuePair> topic64ClickCnt;                        // 每个topic特征点击过多少次
    private List<KeyValuePair> categoryPreference;                     // category特征信息
    private List<KeyValuePair> categoryImpressionCnt;                  // 每个Category特征展示过多少次
    private List<KeyValuePair> categoryClickCnt;                       // 每个Category特征点击过多少次
    private List<KeyValuePair> keywordPreference;                      // keyword特征信息
    private List<KeyValuePair> keywordImpressionCnt;                   // 每个Keyword特征展示过多少次
    private List<KeyValuePair> keywordClickCnt;                        // 每个Keyword特征点击过多少次

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public String getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(String targetCity) {
        this.targetCity = targetCity;
    }

    public int getImpressionCnt() {
        return impressionCnt;
    }

    public void setImpressionCnt(int impressionCnt) {
        this.impressionCnt = impressionCnt;
    }

    public int getClickCnt() {
        return clickCnt;
    }

    public void setClickCnt(int clickCnt) {
        this.clickCnt = clickCnt;
    }

    public List<KeyValuePair> getTopic64Preference() {
        return topic64Preference;
    }

    public void setTopic64Preference(List<KeyValuePair> topic64Preference) {
        this.topic64Preference = topic64Preference;
    }

    public List<KeyValuePair> getTopic64ImpressionCnt() {
        return topic64ImpressionCnt;
    }

    public void setTopic64ImpressionCnt(List<KeyValuePair> topic64ImpressionCnt) {
        this.topic64ImpressionCnt = topic64ImpressionCnt;
    }

    public List<KeyValuePair> getTopic64ClickCnt() {
        return topic64ClickCnt;
    }

    public void setTopic64ClickCnt(List<KeyValuePair> topic64ClickCnt) {
        this.topic64ClickCnt = topic64ClickCnt;
    }

    public List<KeyValuePair> getCategoryPreference() {
        return categoryPreference;
    }

    public void setCategoryPreference(List<KeyValuePair> categoryPreference) {
        this.categoryPreference = categoryPreference;
    }

    public List<KeyValuePair> getCategoryImpressionCnt() {
        return categoryImpressionCnt;
    }

    public void setCategoryImpressionCnt(List<KeyValuePair> categoryImpressionCnt) {
        this.categoryImpressionCnt = categoryImpressionCnt;
    }

    public List<KeyValuePair> getCategoryClickCnt() {
        return categoryClickCnt;
    }

    public void setCategoryClickCnt(List<KeyValuePair> categoryClickCnt) {
        this.categoryClickCnt = categoryClickCnt;
    }

    public List<KeyValuePair> getKeywordPreference() {
        return keywordPreference;
    }

    public void setKeywordPreference(List<KeyValuePair> keywordPreference) {
        this.keywordPreference = keywordPreference;
    }

    public List<KeyValuePair> getKeywordImpressionCnt() {
        return keywordImpressionCnt;
    }

    public void setKeywordImpressionCnt(List<KeyValuePair> keywordImpressionCnt) {
        this.keywordImpressionCnt = keywordImpressionCnt;
    }

    public List<KeyValuePair> getKeywordClickCnt() {
        return keywordClickCnt;
    }

    public void setKeywordClickCnt(List<KeyValuePair> keywordClickCnt) {
        this.keywordClickCnt = keywordClickCnt;
    }


    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
