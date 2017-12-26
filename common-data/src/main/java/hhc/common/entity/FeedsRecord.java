package hhc.common.entity;

import com.alibaba.fastjson.JSONObject;
import hhc.common.entity.base.CommonTag;
import hhc.common.entity.base.KeyValuePair;
import hhc.common.entity.base.RecordBase;

import java.util.List;

/**
 * Created by huhuichao on 2017/7/31.
 */
public final class FeedsRecord extends RecordBase {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8137470707350275786L;
	private Long pubTime;
    private Long updateTime;
    private List<KeyValuePair>     topic64; // topic特征信息
    private List<KeyValuePair>     keywords; // keyword特征信息
    private List<KeyValuePair>     category; // category特征信息
    private List<CommonTag> tagList;

    public List<CommonTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<CommonTag> tagList) {
        this.tagList = tagList;
    }


    public List<KeyValuePair> getTopic64() {
        return topic64;
    }

    public void setTopic64(List<KeyValuePair> topic64) {
        this.topic64 = topic64;
    }

    public List<KeyValuePair> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<KeyValuePair> keywords) {
        this.keywords = keywords;
    }

    public List<KeyValuePair> getCategory() {
        return category;
    }

    public void setCategory(List<KeyValuePair> category) {
        this.category = category;
    }

    public Long getPubTime() {
        return pubTime;
    }

    public void setPubTime(Long pubTime) {
        this.pubTime = pubTime;
    }

    public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public RecordBase build(JSONObject hit) {
		// TODO Auto-generated method stub
		return null;
	}

}
