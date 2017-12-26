package hhc.common.entity;

import com.alibaba.fastjson.JSONObject;
import hhc.common.entity.base.RecordBase;
import hhc.common.utils.JSONUtil;


public final class RankRecord extends RecordBase {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String explain;
	private Double score = 0d;

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public RankRecord(String id, double score) {
		super.setId(id);
		this.score = score;
	}


    public RankRecord(){}
	public RankRecord(String id, String entryId, double score, String explain) {
		super.setId(id);
		this.score = score;
		this.explain = explain;
		this.entryId = entryId;
	}

	@Override
	public String toString() {
		return JSONUtil.bean2String(this);
	}

	@Override
	public RecordBase build(JSONObject hit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		RankRecord record = (RankRecord) o;
		if (id.equals(record.id)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		return 31 * result;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

}
