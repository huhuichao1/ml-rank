package hhc.common.doc;

import java.io.Serializable;

/**
 * Created by liusenhua on 2017/7/25.
 */
public class Tag implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1362322658781616898L;

	public static abstract class Type {
        public static final int UNKNOWN = 0; // 中性标签
        public static final int POSITIVE = 1; // 正面标签
        public static final int NEGATIVE = 2; // 负面标签
    }

    private String name; // 标签名称
    private int type; //标签类型
    private int count; // 该标签的统计数量

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
