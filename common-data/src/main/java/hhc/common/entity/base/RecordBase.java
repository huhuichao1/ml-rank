package hhc.common.entity.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by huhuichao on 2017/7/31.
 */

public abstract class RecordBase implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String id;

	protected String type;

	protected String entryId;

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public abstract RecordBase build(JSONObject hit);

	// @Override
	// public String toString() {
	//// return JSONObject.fromObject(this).toString();
	// return "{id:"+id+"; score:"+score+"; explain:"+explain+"}";
	// }

}