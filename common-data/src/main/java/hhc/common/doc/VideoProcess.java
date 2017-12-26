package hhc.common.doc;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by liusenhua on 2017/9/15.
 */
public class VideoProcess implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6729343754796174573L;

	public static abstract class Status {
        public static final int CRAWLED = 0;//初始化状态
        public static final int CLEANED = 1;//清洗完待下载
        public static final int PUBED = 2;//已发kafka待下载
        public static final int DOWNLOADING = 3;//部分下载完成
        public static final int DOWNLOADED = 4;//所有下载完成
    }

    private int status;
    private Map<String, Video> videoMap;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, Video> getVideoMap() {
        return videoMap;
    }

    public void setVideoMap(Map<String, Video> videoMap) {
        this.videoMap = videoMap;
    }
}
