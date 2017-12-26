package hhc.common.entity.base;

import java.util.List;
import java.util.Map;

public class RequestBase {

	public static enum Relationship {
		term, must, regexp, not,timeRange

	}

	public static enum AggsType {
		term, range

	}

	public static class QueryStruct {
		public String keyword;
		public Relationship relation = Relationship.term;

	}

	public static enum SortType {
		ASC, DESC

	}

	public static class AggsStruct {
		public String field;
		public AggsType type;
		public String name;
		public long gt;
		public long lt;
	}

	/** 备用解析 **/

	protected String indexname;
	protected String type;
	protected int start;
	protected int size;
	protected Sort sort;
	protected boolean isScroll;
	protected Map<String, QueryStruct> freeQuery;
	protected List<AggsStruct> aggsFeild;

	public List<AggsStruct> getAggsFeild() {
		return aggsFeild;
	}

	public void setAggsFeild(List<AggsStruct> aggsFeild) {
		this.aggsFeild = aggsFeild;
	}

	public Map<String, QueryStruct> getFreeQuery() {
		return freeQuery;
	}

	public void setFreeQuery(Map<String, QueryStruct> freeQuery) {
		this.freeQuery = freeQuery;
	}

	public static class Sort {
		public String sortFiled;
		public SortType sortType;
	}

	public String getIndexname() {
		return indexname;
	}

	public void setIndexname(String indexname) {
		this.indexname = indexname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public boolean isScroll() {
		return isScroll;
	}

	public void setScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

}
