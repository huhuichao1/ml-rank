package hhc.common.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class FilterSet implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -7475055035731277630L;
	private List<DocNLPRecord> records;
	private List<DocNLPRecord> randomRecords;
	private List<DocNLPRecord> videoRecords;
	private boolean explain=false;
	private boolean metric=false;  //指定在测试数据集中或许doc和featrue信息
	private int size=10; //截断数量
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isMetric() {
		return metric;
	}

	public void setMetric(boolean metric) {
		this.metric = metric;
	}

	public boolean isExplain() {
		return explain;
	}

	public void setExplain(boolean explain) {
		this.explain = explain;
	}

	public FilterSet(List<DocNLPRecord> records) {
		this.records = records;
	}

	public FilterSet() {
	};

	public List<DocNLPRecord> getRandomRecords() {
		return randomRecords;
	}

	public List<DocNLPRecord> getRecords() {
		if (records == null) {
			return new ArrayList<DocNLPRecord>();
		}
		return records;
	}

	public void setRecords(List<DocNLPRecord> records) {
		this.records = records;
	}

//	public void setFeedsRecords(List<FeedsRecord> records) {
//		if (records == null) {
//			return;
//		}
//		for (FeedsRecord record : records) {
//			addRecord(record);
//		}
//	}

	public void setNLPRecords(List<DocNLPRecord> records) {
		if (records == null) {
			return;
		}
		for (DocNLPRecord record : records) {
			addRecord(record);
		}
	}

	public void addRandomRecords(List<DocNLPRecord> records) {
		if (records == null) {
			return;
		}
		for (DocNLPRecord record : records) {
			if (this.randomRecords == null) {
				this.randomRecords = new ArrayList<>();
			}
			this.randomRecords.add(record);
		}
	}

	public void addRecord(DocNLPRecord records) {

		if (this.records == null) {
			this.records = new ArrayList<>();
		}

		this.records.add(records);
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

	public String toIdString() {
		if (records != null && records.size() != 0) {
			StringBuilder sb = new StringBuilder();

			for (DocNLPRecord record : records) {
				sb.append(record.getId());
				sb.append(";");
			}
			return sb.toString();
		}
		return "";
	}

	public String toStringTopId(int end) {
		if (records != null && records.size() != 0) {
			StringBuilder sb = new StringBuilder();
			int length = records.size();
			for (int i = 0; i < length; i++) {
				sb.append(records.get(i).getId());
				sb.append(";");
				if (i == (end - 1)) {
					break;
				}
			}
			return sb.toString();
		}
		return "";
	}

	public List<RankRecord> getRandomRankRecord() {
		if (randomRecords == null) {
			return null;
		}
		List<RankRecord> rankRecords = new ArrayList<>();
		if (randomRecords.size() > 0) {
			for (DocNLPRecord DocNLPRecord : randomRecords) {
				rankRecords
						.add(new RankRecord(DocNLPRecord.getId(), DocNLPRecord.getEntryId(), 0, "random"));
			}
		}
		return rankRecords;
	}

	public void addVideoRecords(List<DocNLPRecord> records) {
		if (records == null) {
			return;
		}
		for (DocNLPRecord record : records) {
			if (this.videoRecords == null) {
				this.videoRecords = new ArrayList<>();
			}
			this.videoRecords.add(record);
		}
	}

	public void setRandomRecords(List<DocNLPRecord> randomRecords) {
		this.randomRecords = randomRecords;
	}

	public List<DocNLPRecord> getVideoRecords() {
		return videoRecords;
	}

	public void setVideoRecords(List<DocNLPRecord> videoRecords) {
		this.videoRecords = videoRecords;
	}
}
