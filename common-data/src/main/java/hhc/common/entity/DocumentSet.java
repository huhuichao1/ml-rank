package hhc.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class DocumentSet implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = -7816650430103716755L;
    private List<RankRecord> records;
    private Long time;

    public DocumentSet(List<RankRecord> records) {
        this.records = records;
    }

    public DocumentSet() {
    }
    

    public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public List<RankRecord> getRecords() {
        if (records == null) {
            return new ArrayList<RankRecord>();
        }
        return records;
    }

    public void setRecords(List<RankRecord> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        if (records != null && records.size() != 0) {
            StringBuilder sb = new StringBuilder();

            for (RankRecord record : records) {
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



    /**
     * 数据集出重
     *
     * @param result
     * @return
     */
    private List<RankRecord> getRemoveRepeat(List<RankRecord> result) {
        List<RankRecord> notRepeats = new ArrayList<>();
        // 去重
        result.stream().forEach(
                p -> {
                    if (!notRepeats.contains(p)) {
                        notRepeats.add(p);
                    }
                }
        );
        return notRepeats;
    }
}
