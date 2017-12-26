package hhc.common.doc;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

/**
 * Created by liusenhua on 2017/7/13.
 */
public class Thumbnail implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3710952547467786516L;

    public enum SizeType {
        BIG("BIG"), MEDIUM("MEDIUM"), SMALL("SMALL");
        private String value;

        @JsonValue
        public String getValue() {
            return value;
        }

        SizeType(String value) {
            this.value = value;
        }
    }

    private String url;
    private SizeType sizeType;
    private Image image; //缩略图对应的图片

    public Thumbnail() {
    }

    public Thumbnail(String url, SizeType sizeType) {
        this.url = url;
        this.sizeType = sizeType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
