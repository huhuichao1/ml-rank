package hhc.common.doc;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by liusenhua on 2017/8/16.
 */
public class Image implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4300818806914662693L;
    public static class VersionType {
        public static final String V_WIFI = "wifi";
        public static final String V_4G = "4g";
    }

    public static class Version {
        private String url;
        private int width;
        private int height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
    private String url; // 显示加载的URL
    private String urlOrigin; //原始抓取图片URL
    private String name;
    private String description;
    private int width; // 单位: 像素
    private int height; // 单位: 像素
    private int size; // 单位: byte
    private Map<String, Version> versions; // 存放不同版本的Image,比如"4g", "wifi". key为VersionType

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, Version> getVersions() {
        return versions;
    }

    public void setVersions(Map<String, Version> versions) {
        this.versions = versions;
    }
}
