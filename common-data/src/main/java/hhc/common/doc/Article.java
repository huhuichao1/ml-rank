package hhc.common.doc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by liusenhua on 2017/7/25.
 */
public class Article implements Serializable {
    public static abstract class DisplayType {
        public static final int NORMAL = 0;
        public static final int IMAGE_GALLERY = 1;
        public static final int IMAGE_AND_CONTENT = 2;
        public static final int VIDEO = 3;
    }

    /**
     *
     */
    private static final long serialVersionUID = -7062204914271312036L;
    private String title; //标题
    private String author; //作者
    private String summary; //摘要
    private String content; //内容
    private String text; // 纯文本
    private ImageGallery imageGallery; // 图集
    private List<Video> videos; // 视频
    private String media; //媒体名称
    private String mediaId; // 媒体ID
    private String mediaLogUrl; //媒体log
    private Date publishTime; //发表时间
    private long votes; // 投票数
    private long comments; // 评论数
    private long stars; // 几颗星
    private long followers; // 关注数
    private int displayType; // 显示类型

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaLogUrl() {
        return mediaLogUrl;
    }

    public void setMediaLogUrl(String mediaLogUrl) {
        this.mediaLogUrl = mediaLogUrl;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public long getComments() {
        return comments;
    }

    public void setComments(long comments) {
        this.comments = comments;
    }

    public long getStars() {
        return stars;
    }

    public void setStars(long stars) {
        this.stars = stars;
    }

    public long getFollowers() {
        return followers;
    }

    public void setFollowers(long followers) {
        this.followers = followers;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public ImageGallery getImageGallery() {
        return imageGallery;
    }

    public void setImageGallery(ImageGallery imageGallery) {
        this.imageGallery = imageGallery;
    }
}
