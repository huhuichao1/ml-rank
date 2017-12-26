package hhc.common.entity;


import com.alibaba.fastjson.JSONObject;
import hhc.common.entity.base.RecordBase;

public class DocNLPRecord  extends RecordBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public double getHotScore() {
		return hotScore;
	}
	public void setHotScore(double hotScore) {
		this.hotScore = hotScore;
	}
	private double hotScore;
	private double premiumScore;
	private String recaller;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	@Override
	public RecordBase build(JSONObject hit) {
		// TODO Auto-generated method stub
		return null;
	}
	public double getPremiumScore() {
		return premiumScore;
	}
	public void setPremiumScore(double premiumScore) {
		this.premiumScore = premiumScore;
	}
	public String getRecaller() {
		return recaller;
	}
	public void setRecaller(String recaller) {
		this.recaller = recaller;
	}

}
