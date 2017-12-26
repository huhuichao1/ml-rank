package hhc.common.doc;

import java.io.Serializable;
import java.util.Map;

/**
 * @author huangzhiqiang
 */
public class NLPFeature implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8661102575456818133L;
	
	private Map<String, Map<String, Double>>	 	topics;
    private Map<String, Double>      keywords;
    private	Map<String, Double>       categories;
    
	public Map<String, Map<String, Double>> getTopics() {
		return topics;
	}
	public void setTopics(Map<String, Map<String, Double>> topics) {
		this.topics = topics;
	}
	public Map<String, Double> getKeywords() {
		return keywords;
	}
	public void setKeywords(Map<String, Double> keywords) {
		this.keywords = keywords;
	}
	public Map<String, Double> getCategories() {
		return categories;
	}
	public void setCategories(Map<String, Double> categories) {
		this.categories = categories;
	}
    
}
