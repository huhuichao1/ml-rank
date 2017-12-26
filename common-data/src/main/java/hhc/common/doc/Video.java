package hhc.common.doc;

import java.io.Serializable;

/**
 * Created by liusenhua on 2017/9/15.
 */
public class Video implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4156470021216566447L;
	private String url; // 视频url
    private String urlOrigin; // 原始视频url
    private String name; //名称
    private String description; // 描述
    private String mimeType; // 视频格式
    private String preloadUrl; // 预加载的视频url
    private String preloadUrlOrigin; // 原始的预加载的视频url
    private int videoLength; //视频时长，单位秒
    private int playCount; // 播放次数

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlOrigin() {
        return urlOrigin;
    }

    public void setUrlOrigin(String urlOrigin) {
        this.urlOrigin = urlOrigin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPreloadUrl() {
        return preloadUrl;
    }

    public void setPreloadUrl(String preloadUrl) {
        this.preloadUrl = preloadUrl;
    }

    public String getPreloadUrlOrigin() {
        return preloadUrlOrigin;
    }

    public void setPreloadUrlOrigin(String preloadUrlOrigin) {
        this.preloadUrlOrigin = preloadUrlOrigin;
    }

    public int getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(int videoLength) {
        this.videoLength = videoLength;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
}
