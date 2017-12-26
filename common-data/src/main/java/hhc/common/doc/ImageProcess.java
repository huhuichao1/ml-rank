package hhc.common.doc;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by liusenhua on 2017/9/15.
 */
public class ImageProcess implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4117712111133327711L;

	public static abstract class Status {
        public static final int CRAWLED = 0;//初始化状态
        public static final int CLEANED = 1;//清洗完待下载
        public static final int PUBED = 2;//已发kafka待下载
        public static final int DOWNLOADING = 3;//部分下载完成
        public static final int DOWNLOADED = 4;//所有下载完成
        public static final int THUMBNAIL_GENERATED = 5;//Thumbnail
    }

    private int status;
    private Map<String, Image> imageMap;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, Image> getImageMap() {
        return imageMap;
    }

    public void setImageMap(Map<String, Image> imageMap) {
        this.imageMap = imageMap;
    }
}
