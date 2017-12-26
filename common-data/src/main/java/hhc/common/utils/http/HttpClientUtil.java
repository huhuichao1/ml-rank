package hhc.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

public class HttpClientUtil {

	private static final Logger LOG = Logger.getLogger(HttpClientUtil.class);
	private static CloseableHttpClient client;
	private static int defaultSocketTimeout = 20000;
	private static int defaultConnectTimeout = 20000;
	private static int defaultConnectionRequestTimeout = 5000;// 从manager请求获取connection的超时时间
	private static int defaultMaxTotal = 200;
	private static int defaultMaxPerRoute = 10;
	private static String defaultCharset = "utf-8";

	static {
		// 设置RequestConfig(超时时间，跳转，cookie,代理等)
		Builder builder = RequestConfig.custom();
		builder.setSocketTimeout(defaultSocketTimeout);
		builder.setConnectTimeout(defaultConnectTimeout);
		builder.setConnectionRequestTimeout(defaultConnectionRequestTimeout);
		// builder.setMaxRedirects(2);//最大跳转次数
		// builder.setProxy(new HttpHost("10.199.75.12", 8080));// 设置代理
		RequestConfig requestConfig = builder.build();

		// 设置连接管理器
		// PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
		// manager.setMaxTotal(defaultMaxTotal);
		// manager.setDefaultMaxPerRoute(defaultMaxPerRoute);

		HttpClientBuilder httpClientBuilder = HttpClients.custom();// HttpClientBuilder.create();
		// httpClientBuilder.setConnectionManager(manager);
		httpClientBuilder.setDefaultRequestConfig(requestConfig);
		httpClientBuilder.setMaxConnTotal(defaultMaxTotal);
		httpClientBuilder.setMaxConnPerRoute(defaultMaxPerRoute);
		httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, false));// 重试次数
		// httpClientBuilder.setProxy(new HttpHost("10.199.75.12", 8080));// 设置代理
		client = httpClientBuilder.build();// HttpClients.createDefault()

		// 已废弃设置超时间
		// HttpClient client = new DefaultHttpClient();
		// client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
		// client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000);
	}



	public static String get(String uri) {
		return get(uri, defaultCharset);
	}

	public static String getByProxy(String uri) {
		return get(uri, new HttpHost("10.199.75.12", 8080), defaultCharset);
	}

	public static String get(String uri, String charsetName) {
		return get(uri, null, charsetName);
	}

	public static String get(String uri, HttpHost target, String charsetName) {
		if (uri == null || uri.trim().equals("")) {
			return null;
		}
		try {
			return get(new URI(uri), target, CharsetUtils.lookup(charsetName));
		} catch (URISyntaxException e) {
			LOG.error(uri, e);
		}
		return null;
	}

	public static String get(URI uri, HttpHost target, Charset charset) {
		if (uri == null) {
			return null;
		}
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(uri);
			// RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build();
			// httpGet.setConfig(requestConfig);
			if (target != null) {
				response = client.execute(target, httpGet);
			} else {
				response = client.execute(httpGet);
			}
			// print(response);
			if (response != null && response.getEntity() != null) {
				return EntityUtils.toString(response.getEntity(), charset);
			}
		} catch (IOException e) {
			LOG.error(uri, e);
		} finally {
			if (response != null) {
				try {
					// EntityUtils.consume(response.getEntity()); // 关闭流
					response.close();
				} catch (IOException e) {
					LOG.error(uri, e);
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String post(String url, JSONObject json) {
		HttpPost method = new HttpPost(url);
		method.addHeader("Content-type", "application/json; charset=utf-8");
		method.setHeader("Accept", "application/json");
		method.setEntity(new StringEntity(json.toJSONString(), defaultCharset));
		HttpResponse response;
		try {
			response = client.execute(method);
			if (response != null && response.getEntity() != null) {
				return EntityUtils.toString(response.getEntity(), defaultCharset);
			} else {
				LOG.error(url + " : response is null or response.getEntity() is null !");
			}
		} catch (ClientProtocolException e) {
			LOG.error(url, e);
		} catch (IOException e) {
			LOG.error(url, e);
		}
		return null;
	}

	public static void main(String[] args) throws URISyntaxException {
//		 for (int i = 0; i < 1; i++) {
//		 new Thread() {
//		 public void run() {
//		 String body = HttpClientUtil.getByProxy("http://www.baidu.com");
//		 // String body = HttpClientUtil.get("http://www.baidu.com");
//		 System.out.println("====body" + body);
//		 }
//		 }.start();
//		 }
//		 URI uri = new
//		 URI("http://www.baidu.com/ddId=29b74dd3f36d432ebbfc1a33f4f2af78ce135aac&version=1&facetLimit=300&highlightPre=__HLPRE__&__v=v1&FFClientVersion=23100&from=100&size=20&highlightFields=storeViewName,title,subtitle,title_original&timeout=300&FFClientType=2&fields=id,city_id,plaza_id,product_id,title,subtitle,title_original,storeViewName,store_type_name,pub_time,sale_num,time_length,show_type,comment_score,sale_time_start,sale_time_end,icon,averageScore_d,category_id,sub_category_id,store_category_id,store_subcategory_id,store_id,store_x_d,store_y_d,store_z,storeBunkNo_ss,is_shangou_i,hasGiftContract_i,is_coup,is_activity,isSupportReserveSeat,isSupportSelfOrder,isSupportSmartQueue,isCommon,sale_price,ori_price,pay_type,resource_id&filters=resource_id%3a(1),plaza_id%3a(1000383),city_id%3a(510100)&imei=29b74dd3f36d432ebbfc1a33f4f2af78ce135aac&highlightPost=__HLPOST__&facetMinCount=1&wdId=e0bbb4335c7d2649c3818d2831d589a2&sorts=store");

		String url = "http://promotionapi.intra.sit.ffan.com/promotionapi/v1/all";
		JSONObject json = new JSONObject();
		json.put("__uni_source", "4.10.3");
		json.put("pagesize", 20);
		json.put("page", 1);
		String result = HttpClientUtil.post(url, json);
		System.out.println(result);
	}
}
