package hhc.common.doc;


import hhc.common.entity.base.KeyValuePair;
import hhc.common.utils.json.JsonUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liusenhua on 2017/7/25.
 */
public class Document extends Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = -2447008739791645390L;

	// The utility to do serialize/Deserialize
	public static Document fromJson(String json) {
		return JsonUtil.parseObject(json, Document.class);
	}

	public static String toJson(Document document) {
		return JsonUtil.toJSONString(document);
	}

	public static abstract class Type {
		public static final int UNKNOWN = 0;
		public static final int MERCHANT = 1; // 门店
		public static final int ARTICLE = 2; // 软文
		public static final int PRODUCT = 3; // 产品
	}

	public static abstract class SubType { // // 默认值为1， 用以区分是主要实体还是类似评论和门店的产品等
		public static final int UNKNOWN = 0;
		public static final int ENTITY = 1;
		public static final int COMMENT = 2;
		public static final int MERCHANT_PRODUCT = 3;
		public static final int MERCHANT_PROMOTION = 4;
		public static final int IMAGE_GALLERY = 5;
		public static final int IMAGE_AND_CONTENT = 6;
		public static final int VIDEO = 7;
	}

	public static abstract class Status {
		public static final int UNKNOWN = 0;
		public static final int CRAWLED = 1; // 抽取中
		public static final int CRAWL_FAILED = -1; // 抽取失败
		public static final int PROCESSED = 2; // 清洗
		public static final int PROCESS_FAILED = -2; // 清洗失败
	}

	private int type; // 类型，取上面定义的枚举值
	private int subType; // 子类型，取上面定义的枚举值
	private int status; // 状态, 取上面定义的枚举值
	private String name;
	private String description;
	private List<String> categories; // 原始类目
	private List<String> categoryIds; // 映射到我们的类目
	private List<Tag> tags;
	private String domain;
	private String sourceUrl;
	private List<Thumbnail> thumbnails; // 缩略图
	private Thumbnail defaultThumbnail; // 默认的缩略图
	private Internal internal = Internal.get(); // 内部对象，用以检查保存schema版本
	private String templateId; // 关联的模板ID
	private Boolean isPremium; // 是否优质文档
	private Boolean premiumMedia; //是否优质作者
	private Date createTime = new Date();
	private Date updateTime = new Date();
	private Date expireTime;
	private Double evergreenScore;
	private Boolean isEvergreen;

	// 图片/视频处理
	private ImageProcess imageProcess;
	private VideoProcess videoProcess;
	// private String videoCategory;

	// 具体文档内容信息
	private Merchant merchant; // 当type = 1, 不为空
	private Article article; // 当type = 2, 不为空
	private Product product; // 当type = 3, 不为空

	// NLP特征
	private Map<String, NLPFeature> nlps;
	private List<KeyValuePair> topic64; // topic特征信息
	private List<KeyValuePair> topic256; // topic特征信息
	private List<KeyValuePair> keywords; // keyword特征信息
	private List<KeyValuePair> category; // category特征信息
	private List<KeyValuePair> titleKeywords; //新增title keyword，仅对文章title提取keyword
	private List<KeyValuePair> videoCategory;
	private List<KeyValuePair> videoTitleKeywords;

	// 入库
	private String nlp;
	private String entryId;
	private String esUpdateTime;
	private Date timestamp;

	private String                imageProcessJson;
	// post tag
	private Double                premiumScore;
	private Double                consumeScore;
	private List<String>          sensitiveCityList;

	public Boolean getPremium() {
		return isPremium;
	}

	public void setPremium(Boolean premium) {
		isPremium = premium;
	}

	public String getNlp() {
		return nlp;
	}

	public void setNlp(String nlp) {
		this.nlp = nlp;
	}

	public List<KeyValuePair> getTopic256() {
		return topic256;
	}

	public void setTopic256(List<KeyValuePair> topic256) {
		this.topic256 = topic256;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public List<Thumbnail> getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(List<Thumbnail> thumbnails) {
		this.thumbnails = thumbnails;
	}

	public Thumbnail getDefaultThumbnail() {
		return defaultThumbnail;
	}

	public void setDefaultThumbnail(Thumbnail defaultThumbnail) {
		this.defaultThumbnail = defaultThumbnail;
	}

	public Internal getInternal() {
		return internal;
	}

	public void setInternal(Internal internal) {
		this.internal = internal;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Boolean getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Map<String, NLPFeature> getNlps() {
		return nlps;
	}

	public void setNlps(Map<String, NLPFeature> nlps) {
		this.nlps = nlps;
	}

	public List<KeyValuePair> getTopic64() {
		return topic64;
	}

	public void setTopic64(List<KeyValuePair> topic64) {
		this.topic64 = topic64;
	}

	public List<KeyValuePair> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<KeyValuePair> keywords) {
		this.keywords = keywords;
	}

	public List<KeyValuePair> getCategory() {
		return category;
	}

	public void setCategory(List<KeyValuePair> category) {
		this.category = category;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public String getEsUpdateTime() {
		return esUpdateTime;
	}

	public void setEsUpdateTime(String esUpdateTime) {
		this.esUpdateTime = esUpdateTime;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public ImageProcess getImageProcess() {
		return imageProcess;
	}

	public void setImageProcess(ImageProcess imageProcess) {
		this.imageProcess = imageProcess;
	}

	public VideoProcess getVideoProcess() {
		return videoProcess;
	}

	public void setVideoProcess(VideoProcess videoProcess) {
		this.videoProcess = videoProcess;
	}

	public Double getPremiumScore() {
		return premiumScore;
	}

	public void setPremiumScore(Double premiumScore) {
		this.premiumScore = premiumScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		return result;
	}

	public List<KeyValuePair> getVideoCategory() {
		return videoCategory;
	}

	public void setVideoCategory(List<KeyValuePair> videoCategory) {
		this.videoCategory = videoCategory;
	}

	public List<KeyValuePair> getVideoTitleKeywords() {
		return videoTitleKeywords;
	}

	public void setVideoTitleKeywords(List<KeyValuePair> videoTitleKeywords) {
		this.videoTitleKeywords = videoTitleKeywords;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}

	public Boolean getPremiumMedia() {
		return premiumMedia;
	}

	public void setPremiumMedia(Boolean premiumMedia) {
		this.premiumMedia = premiumMedia;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Double getEvergreenScore() {
		return evergreenScore;
	}

	public void setEvergreenScore(Double evergreenScore) {
		this.evergreenScore = evergreenScore;
	}

	public Boolean getEvergreen() {
		return isEvergreen;
	}

	public void setEvergreen(Boolean evergreen) {
		isEvergreen = evergreen;
	}

	public List<KeyValuePair> getTitleKeywords() {
		return titleKeywords;
	}

	public void setTitleKeywords(List<KeyValuePair> titleKeywords) {
		this.titleKeywords = titleKeywords;
	}

	public String getImageProcessJson() {
		return imageProcessJson;
	}

	public void setImageProcessJson(String imageProcessJson) {
		this.imageProcessJson = imageProcessJson;
	}

	public Double getConsumeScore() {
		return consumeScore;
	}

	public void setConsumeScore(Double consumeScore) {
		this.consumeScore = consumeScore;
	}

	public List<String> getSensitiveCityList() {
		return sensitiveCityList;
	}

	public void setSensitiveCityList(List<String> sensitiveCityList) {
		this.sensitiveCityList = sensitiveCityList;
	}
}
