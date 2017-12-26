package hhc.common.doc;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liusenhua on 2017/8/16.
 */
public class ImageGallery implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5532064238908593298L;
	private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
