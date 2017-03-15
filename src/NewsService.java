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
	 * @param ����ʾ����������
	 */
	public static boolean save(String name, String zhujiangren,String time,String didian,String zhubanfang,String detail) {
		String path="http://192.168.1.111:8080/ManageServlet";//������·��
	
		//	List<Map<String, Object>> Data =new ArrayList<Map<String, Object>>();
		
		Map<String, Object> params=new HashMap<String, Object>();
		//��ÿһ����д����Ϣ���뵽һ��params��
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
		List<NameValuePair> pair=new ArrayList<NameValuePair>();//����������
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
	 * ͨ��HttpClient����GET����
	 * @param path ����·��
	 * @param params �������
	 * @param ecoding �������
	 * @return �����Ƿ�ɹ�
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
			//���շ��ص���Ӧ�����Ҳ���Բ�����
			HttpEntity entity=response.getEntity();
			EntityUtils.toString(entity,ecoding);//����Ӧ������б���
			return true;
		}
		return false;
	}
	/**
	 * ����POST����
	 * @param path ����·��
	 * @param params �������
	 * @param ecoding �������
	 * @return �����Ƿ�ɹ�
	 */
	private static boolean sendPOSTRequest(String path,
			Map<String, Object> params, String ecoding) throws Exception {
		//title=kaka&timelength=10
		//��ƴʵ������
		StringBuilder data=new StringBuilder();
		if(params!=null && !params.isEmpty()){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				data.append(entry.getKey()).append("=");
				data.append(URLEncoder.encode((String) entry.getValue(),ecoding));
				data.append("&");
			}
			data.deleteCharAt(data.length()-1);
		}
		byte[] entity=data.toString().getBytes();//�õ�ʵ������
		HttpURLConnection conn=(HttpURLConnection)new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);//������������������
		//����ͷ�ֶ�
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
	 * ����GET����
	 * @param path ����·��
	 * @param params �������
	 * @param ecoding �������
	 * @return �����Ƿ�ɹ�
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
