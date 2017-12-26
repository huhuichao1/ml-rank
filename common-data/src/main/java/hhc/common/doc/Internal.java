package hhc.common.doc;

import java.io.Serializable;


/**
 * Created by liusenhua on 2017/7/13.
 */
public class Internal implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6951069350990453570L;

	 public static abstract class SCHEMA {
	        public static final String V_1 = "0.01";
	        public static final String V_2 = "0.02";
	    }

	    private String schemaVersion;
	    private long cycleVersion;

	    // 发布出去后，每次字段更改需修改scheam version
	    private static Internal current = new Internal(SCHEMA.V_1);

	    public static Internal get() {
	        return current;
	    }

	    public Internal() {
	    }

	    public Internal(String schemaVersion) {
	        this.schemaVersion = schemaVersion;
	    }

	    public String getSchemaVersion() {
	        return schemaVersion;
	    }

	    public void setSchemaVersion(String schemaVersion) {
	        this.schemaVersion = schemaVersion;
	    }

	    public long getCycleVersion() {
	        return cycleVersion;
	    }

	    public void setCycleVersion(long cycleVersion) {
	        this.cycleVersion = cycleVersion;
	    }
}
