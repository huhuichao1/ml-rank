package hhc.common.entity.base;

import java.io.Serializable;

/**
 * @author hhc
 */
public class KeyValuePair implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6761291511139593128L;
	private String key;
    private Double value;

    public KeyValuePair() {}

    public KeyValuePair(String key, Double value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
