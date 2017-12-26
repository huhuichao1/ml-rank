package org.wltea.analyzer.dic;

public class DictWord {

	private String name;// 词
	private Boolean enable;// 状态 true有效 其他无效

	public DictWord(String name, Boolean enable) {
		this.name = name;
		this.enable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "DictWord [" + (name != null ? "name=" + name + ", " : "") + (enable != null ? "enable=" + enable : "") + "]";
	}

}
