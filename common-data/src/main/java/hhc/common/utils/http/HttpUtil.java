package hhc.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

public class HttpUtil {

	/**
	 * 自动补充调用者身份
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGet(String url) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		if (url.indexOf("?") != -1) {
			url = url + "&__uni_source=4.10.3";
		} else {
			url = url + "?__uni_source=4.10.3";
		}
//		LogUtil.ROOT.info("=======" + url);
		return HttpClientUtil.get(url);
	}

	/**
	 * post 方式请求，参数为json串
	 * @param url
	 * @param json
	 * @return
	 */
	public static String httpPost(String url, JSONObject json) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		json.put("__uni_source", "4.10.3");
//		LogUtil.ROOT.info("url== : " + url + " json :" + json.toJSONString());
		return HttpClientUtil.post(url, json);
	}
}
