/**
 * 
 */
package com.manage.common.utils.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manage.common.utils.DateUtils;
import com.manage.project.system.server.domain.ServerMessage;

/**
 * HttpClient工具类
 * 
 * @author zhangjiawei
 * @date 2018年11月2日
 * 
 */
public class HttpClientUtils {
	
	private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

	public static ServerMessage getToServer(String url,String message) {
		/*try {
			log.info("get请求调用核心服务器接口,url=:"+url+",param="+message);
			HttpClient client = HttpClients.createDefault();
			//发送请求
			String encodeParam = URLEncoder.encode(message,"UTF-8");
			String urlStr=url+encodeParam;
			HttpGet get = new HttpGet(urlStr);
	        HttpResponse response = client.execute(get);
	        HttpEntity entity = response.getEntity();
	        String result = EntityUtils.toString(entity, "UTF-8");
	        return ServerMessage.jsonToMessage(result);
		}catch (Exception e) {
			log.error("调用HttpClientUtils.getToServer Exception,url="+url+",param="+message,e.getMessage());
			return null;
		}*/	
		try {
			//String encodeParam = URLEncoder.encode(message,"UTF-8");
			//String result = HttpClientUtils.httpGet(url, encodeParam,true);
			String result = HttpClientUtils.httpGet(url, message,true);
			if(result!=null) {
				ServerMessage serverMessage = ServerMessage.jsonToMessage(result);
				log.info("从http获取返回结果:"+serverMessage.toJson()+",时间:"+DateUtils.getTime());
				return serverMessage;
			}else {
				log.error("调用httpClient失败,返回结果为空:url"+url+"message:"+message);
				return null;
			}
		} catch (Exception e) {
			log.error("调用httpClient失败:url"+url+"message:"+message,e);
			return null;
		}
		
	}
	
	/**
	 * 发送httpGet请求,不对参数编码
	 * 
	 * @param url
	 * @param message
	 * @return
	 */
	public static String httpGet(String url,String message,boolean isEncode) {
		try {
			log.info("get请求调用核心服务器接口,url=:"+url+",param="+message);
			if(isEncode) {
				message=URLEncoder.encode(message,"UTF-8");
			}
			HttpClient client = HttpClients.createDefault();
			//发送请求
			//String encodeParam = URLEncoder.encode(message,"UTF-8");
			//String urlStr=url+encodeParam;
			String urlStr=url+message;
			HttpGet get = new HttpGet(urlStr);
	        HttpResponse response = client.execute(get);
	        HttpEntity entity = response.getEntity();
	        return EntityUtils.toString(entity, "UTF-8"); 
		}catch (Exception e) {
			log.error("调用HttpClientUtils.httpGet Exception,url="+url+",param="+message,e.getMessage());
			return null;
		}		
	}
}
