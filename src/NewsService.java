package com.example.popupmenutest;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class NewsService {

	/**
	 * @param 调用示例保存数据
	 */
	public static boolean save(String name, String zhujiangren,String time,String didian,String zhubanfang,String detail) {
		String path="http://192.168.1.111:8080/ManageServlet";//服务器路径
	
		//	List<Map<String, Object>> Data =new ArrayList<Map<String, Object>>();
		
		Map<String, Object> params=new HashMap<String, Object>();
		//把每一次填写的信息加入到一个params里
		params.put("ActivityName", name);
		params.put("Azhujiangren", zhujiangren);
		params.put("Atime", time);
		params.put("Adidian", didian);
		params.put("Azhubanfang", zhubanfang);
		params.put("Adetail", detail);
		//Data.add(params);
		
		try {
			return sendHttpClientGETRequest(path,params,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
		
	private static boolean sendHttpClientPOSTRequest(String path,
			Map<String, Object> params, String ecoding) throws Exception {
		List<NameValuePair> pair=new ArrayList<NameValuePair>();//存放请求参数
		if(params!=null && !params.isEmpty()){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				pair.add(new BasicNameValuePair(entry.getKey(),(String) entry.getValue()));
			}
		}
		UrlEncodedFormEntity enFormEntity=new UrlEncodedFormEntity(pair,ecoding);
		HttpPost httpPost=new HttpPost(path);
		httpPost.setEntity(enFormEntity);
		DefaultHttpClient client=new DefaultHttpClient();
		HttpResponse response=client.execute(httpPost);
		if(response.getStatusLine().getStatusCode()==200){
			return true;
		}
		return false;
	}
	/**
	 * 通过HttpClient发送GET请求
	 * @param path 请求路径
	 * @param params 请求参数
	 * @param ecoding 请求编码
	 * @return 请求是否成功
	 */
	private static boolean sendHttpClientGETRequest(String path,
			Map<String, Object> params, String ecoding) throws Exception {
		StringBuilder url=new StringBuilder(path);
		url.append("?");
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			url.append(entry.getKey()).append("=");
			url.append(URLEncoder.encode((String) entry.getValue(),ecoding));
			url.append("&");
		}
		url.deleteCharAt(url.length()-1);
		HttpGet httpGet=new HttpGet(url.toString());
		DefaultHttpClient client=new DefaultHttpClient();
		HttpResponse response=client.execute(httpGet);
		if(response.getStatusLine().getStatusCode()==200){
			//接收返回的响应结果，也可以不接收
			HttpEntity entity=response.getEntity();
			EntityUtils.toString(entity,ecoding);//对响应结果进行编码
			return true;
		}
		return false;
	}
	/**
	 * 发送POST请求
	 * @param path 请求路径
	 * @param params 请求参数
	 * @param ecoding 请求编码
	 * @return 请求是否成功
	 */
	private static boolean sendPOSTRequest(String path,
			Map<String, Object> params, String ecoding) throws Exception {
		//title=kaka&timelength=10
		//组拼实体数据
		StringBuilder data=new StringBuilder();
		if(params!=null && !params.isEmpty()){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				data.append(entry.getKey()).append("=");
				data.append(URLEncoder.encode((String) entry.getValue(),ecoding));
				data.append("&");
			}
			data.deleteCharAt(data.length()-1);
		}
		byte[] entity=data.toString().getBytes();//得到实体数据
		HttpURLConnection conn=(HttpURLConnection)new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);//设置允许对外输出数据
		//设置头字段
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		OutputStream outStream=conn.getOutputStream();
		outStream.write(entity);
		if(conn.getResponseCode()==200){
			return true;
		}
		return false;
	}

	/**
	 * 发送GET请求
	 * @param path 请求路径
	 * @param params 请求参数
	 * @param ecoding 请求编码
	 * @return 请求是否成功
	 */
	private static boolean sendGETRequest(String path,Map<String,Object> params,String ecoding) throws Exception {
		StringBuilder url=new StringBuilder(path);
		url.append("?");
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			url.append(entry.getKey()).append("=");
			url.append(URLEncoder.encode((String) entry.getValue(),ecoding));
			url.append("&");
		}
		url.deleteCharAt(url.length()-1);
		HttpURLConnection conn=(HttpURLConnection)new URL(url.toString()).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode()==200){
			return true;
		}
		return false;
	}

	
}
