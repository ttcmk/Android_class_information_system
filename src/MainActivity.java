package com.example.popupmenutest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	
	public static String finaltext = "";
	private ClassInfoModel classmodel;
	private ClassInfoModel tempmodel;
	private SQLiteHelper sqlhelper; 
	
	private ImageView logoImageView;
	private String string;
	private static TextView textView;
	private Myhandler myhandler = new Myhandler();
	private Bitmap bitmap;
	private ImageView imageView;
	private EditText name,psw,code;
	private Button send,chongfu;
	private String res;
	private String viewstate;
	private boolean successed;
	private String called,url;
	private String called2 = "";
	private String xh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sqlhelper = new SQLiteHelper(this);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		logoImageView=(ImageView)findViewById(R.id.logoImageView);
		logoImageView.setImageResource(R.drawable.logo);
		textView = (TextView) findViewById(R.id.textview1);
		textView.setMovementMethod(ScrollingMovementMethod.getInstance());
		imageView = (ImageView)findViewById(R.id.codeimg);
		name = (EditText)findViewById(R.id.name);
		psw=(EditText)findViewById(R.id.psw);
		code=(EditText)findViewById(R.id.code);
		send = (Button)findViewById(R.id.send);
		send.setOnClickListener(this);
		chongfu = (Button)findViewById(R.id.button1);
		chongfu.setOnClickListener(xx2);
		new UrlThread().start();
	}
	private class GetCodeThread extends Thread{
		@Override
		public void run() {
			try {
				getcode();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}
	
	private class UrlThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				httpget();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
public void httpget() throws Exception{
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpPost httpPost = new HttpPost("http://xuanke.cufe.edu.cn/");
	HttpResponse httpResponse = httpClient.execute(httpPost);
	String htmlstr=EntityUtils.toString(httpResponse.getEntity());
	htmlstr = htmlstr.split(" name=\"__VIEWSTATE\" value=\"")[1];
	htmlstr = htmlstr.split("div class=\"login_main\">")[0];
	htmlstr = htmlstr.split("\" />")[0];
	viewstate=htmlstr;
	string=httpResponse.getFirstHeader("Set-Cookie").toString();
	String[] strings=string.split(";");
	string = strings[0];
	strings = string.split(":");
	string = strings[1];
	Log.i("mytest", htmlstr);	
	myhandler.sendEmptyMessage(0);
	
}
public void getcode() throws Exception{
	DefaultHttpClient httpClient = new DefaultHttpClient();
	HttpPost httpPost = new HttpPost("http://xuanke.cufe.edu.cn/CheckCode.aspx");
	httpPost.addHeader("Cookie", string);
	HttpResponse httpResponse = httpClient.execute(httpPost);
	byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());
	bitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		
	myhandler.sendEmptyMessage(1);
	
}
private class Myhandler extends Handler{
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case 0:
			new GetCodeThread().start();	
			break;
		case 1:
			//textView.setText(string);
			imageView.setImageBitmap(bitmap);
			break;
		case 2:
			xh=name.getText().toString();
			Log.i("xh",xh);
			Log.i("res",res);
			url = "http://xuanke.cufe.edu.cn/xskbcx.aspx?xh="+xh+"&xm="+res+"&gnmkdm=N121603";
			if (!successed) {
				new GetCodeThread().start();
			}else{
				try {
					called2 = post2();
				} catch (Exception e) {
					Log.i("unsuccessful", "unsuccessed");
					e.printStackTrace();
				}
				String finaltext = "";
			     try {
			      Log.i("called2",called2);
					finaltext = gethtml(called2);
					sqlhelper.close();
					Log.i("finaltext",finaltext);
				} catch (IOException e) {
					e.printStackTrace();
				}
			     Intent intent = new Intent();
				 intent.setClass(MainActivity.this, kebiaojiemian.class);
				 MainActivity.this.startActivity(intent);
				// 
			//textView.setText(finaltext); /*测试数据的格式和得到的网页内容*/
			break;}
		default:
			break;
		}
	}
}
@Override
public void onClick(View v) {
	new PostThread().start();
	
}
private class PostThread extends Thread {
	@Override
	public void run() {
		super.run();
		try {
			post();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}



public void post() throws Exception{
	HttpClient httpClient = new DefaultHttpClient();
	HttpPost httpPost  = new HttpPost("http://xuanke.cufe.edu.cn/");
	httpPost.addHeader("Connection", "keep-alive");
	httpPost.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	httpPost.addHeader("Cookie", string);
	httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
	httpPost.addHeader("Referer", "http://xuanke.cufe.edu.cn/");
	HashMap<String, String> parmas = new HashMap<String, String>();
	parmas.put("__VIEWSTATE", viewstate);
	parmas.put("txtUserName", name.getText().toString());
	parmas.put("TextBox2", psw.getText().toString());
	parmas.put("txtSecretCode", code.getText().toString());
	parmas.put("RadioButtonList1", "%D1%A7%C9%FA");
	parmas.put("Button1", "");
	parmas.put("lbLanguage", "");
	ArrayList<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
	     if(parmas != null){
	         Set<String> keys = parmas.keySet();
	         for(Iterator<String> i = keys.iterator(); i.hasNext();) {
	              String key = (String)i.next();
	              pairs.add(new BasicNameValuePair(key, parmas.get(key)));
	         }
	    }
	     UrlEncodedFormEntity p_entity = new UrlEncodedFormEntity(pairs, "gb2312");
	     httpPost.setEntity(p_entity);
	     HttpResponse httpResponse = httpClient.execute(httpPost);
	     res = EntityUtils.toString(httpResponse.getEntity());
	     if (!res.contains("<span id=\"xhxm\">")) {
			res = "failed";
				successed=false;
		}else {
			called = res;
			 res = res.split("<span id=\"xhxm\">")[1];
		     //res = res.split("  ")[1];
		     res = res.split("同学")[0];
		    successed=true;
		}
	    myhandler.sendEmptyMessage(2);
	    
	     
	     
}


public String post2() {
	String strResult = null;
	String uriAPI = url;
	HttpPost httpRequest = new HttpPost(uriAPI);
	
	httpRequest.addHeader("Connection", "keep-alive");
	httpRequest.addHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	httpRequest.addHeader("Cookie", string);
	httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
	httpRequest.addHeader("Referer", url);
	
	List <NameValuePair> params = new ArrayList <NameValuePair>();
	params.add(new BasicNameValuePair("str", "I am Post"));
	try{
		httpRequest.setEntity(new UrlEncodedFormEntity(params,"GB2312"));
		HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
		if(httpResponse.getStatusLine().getStatusCode() == 200)
		{
			strResult = EntityUtils.toString(httpResponse.getEntity());
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	Log.i("bbbbbbbbbbbbbbbb",strResult);
	return strResult;
	
	
}


public String gethtml(String AllContent) throws IOException {
	String s;
       //使用后HTML Parser 控件
       Parser myParser;   
       NodeList nodeList = null;   
       myParser =Parser.createParser(AllContent, "GB2312");   
       NodeFilter tableFilter = new NodeClassFilter(TableTag.class);   
       OrFilter lastFilter = new OrFilter();   
       lastFilter.setPredicates(new NodeFilter[] { tableFilter });   
       sqlhelper.deleteAll();
          try { 
             //获取标签为table的节点列表
             nodeList =myParser.parse(lastFilter);   
             //循环读取每个table
             for (int i = 1; i <=1; i++) {   
                  if (nodeList.elementAt(i)instanceof TableTag) {   
                     TableTag tag = (TableTag)nodeList.elementAt(i);   
                       TableRow[] rows =tag.getRows();   
                     //循环读取每一行
                      for (int j = 2; j <rows.length-2; j+=2) {   
                          TableRow tr =(TableRow) rows[j];   
                          TableColumn[] td =tr.getColumns();   

                          if(j%4 == 2)
                        	  for(int k = 2;k<9;k++){
                        		finaltext+="星期"+(k-1)+"第"+ (j+1)/2+ "节\n";
                        		 Distinguish(td[k].toPlainTextString(),(k-1), (j+1)/2);
                        	  }
                          else 
                        	  for(int k = 1;k<8;k++) {
                        		finaltext+="星期"+k+"第"+ (j+1)/2+ "节\n";
                        		 Distinguish(td[k].toPlainTextString(), k, (j+1)/2);
                        	  }
                      }
                      }
                  }   
              } catch (ParserException e) {   
              e.printStackTrace();   
         }
		return finaltext;
}

public  void Distinguish(String ttcmk, int i, int j) {
int f0 = i * 100 + j * 10; String F0 = Integer.toString(f0);
int f1 = i * 100 + j * 10 + 1; String F1 = Integer.toString(f1);
int f2 = i*10 + j; String F2 = Integer.toString(f2);

classmodel = new ClassInfoModel();
tempmodel = new ClassInfoModel();


	if(ttcmk.contains("单周")&&ttcmk.contains("双周")){
		  String a = ttcmk.split("M")[1];//双周
		  String b = ttcmk.replace(a, "");//单周
		  //单周0.双周1

		  classmodel.setId(F0);
		  classmodel.setCell_id(F2);
		  classmodel.setSdweek("0");
		  sqlhelper.create(classmodel);
		  classify(b,f0);
		  sqlhelper.update(classmodel);
//		 tempmodel = sqlhelper.getClassById(F0);
//		  sqlhelper.update(tempmodel);
		  
		  classmodel.setId(F1);
		  classmodel.setCell_id(F2);
		  classmodel.setSdweek("1");
		  sqlhelper.create(classmodel);
		  classify(a,f1);
		  sqlhelper.update(classmodel);
//		  tempmodel = sqlhelper.getClassById(F1);
//		  sqlhelper.update(tempmodel);
	  }
	else if (ttcmk.contains("单周")){
		  classmodel.setId(F0);
		  classmodel.setCell_id(F2);
		  classmodel.setSdweek("0");
		  sqlhelper.create(classmodel);
		  classify(ttcmk,f0);
		  sqlhelper.update(classmodel);
//		tempmodel = sqlhelper.getClassById(F0);
//		sqlhelper.update(tempmodel);
		
		classmodel.setId(F1);
		  classmodel.setCell_id(F2);
		  classmodel.setSdweek("1");
		  sqlhelper.create(classmodel);
		  classmodel.setTitle("");
			classmodel.setProperty("");
			classmodel.setTeacher("");
			classmodel.setTime("");
		  sqlhelper.update(classmodel);
//		  tempmodel = sqlhelper.getClassById(F1);
//		  sqlhelper.update(tempmodel);
	}
	else if (ttcmk.contains("双周")){
		classmodel.setId(F0);
		  classmodel.setCell_id(F2);
		  classmodel.setSdweek("0");
		  sqlhelper.create(classmodel);
		  classmodel.setTitle("");
			classmodel.setProperty("");
			classmodel.setTeacher("");
			classmodel.setTime("");
		  sqlhelper.update(classmodel);
//		 tempmodel = sqlhelper.getClassById(F0);
//		  sqlhelper.update(tempmodel);
		
		classmodel.setId(F1);
		classmodel.setCell_id(F2);
		classmodel.setSdweek("1");
		sqlhelper.create(classmodel);
		classify(ttcmk,f1);
		sqlhelper.update(classmodel);
//		tempmodel = sqlhelper.getClassById(F1);
//		sqlhelper.update(tempmodel);
	}
	else {
		classmodel.setId(F0);
		  classmodel.setCell_id(F2);
		  classmodel.setSdweek("0");
		  sqlhelper.create(classmodel);
		  classify(ttcmk,f0);
		  sqlhelper.update(classmodel);
//		 tempmodel = sqlhelper.getClassById(F0);
//		 sqlhelper.update(tempmodel);
		
		  
		  classmodel.setId(F1);
		  classmodel.setCell_id(F2);
		  classmodel.setSdweek("1");
		  sqlhelper.create(classmodel);
		  classify(ttcmk,f1);
		  sqlhelper.update(classmodel);
//		  tempmodel = sqlhelper.getClassById(F1);
//		  sqlhelper.update(tempmodel);
		 
	}
}

public  void classify(String b, int number){
	int m=0;
	int n=0;
	
	if(b.contains("&nbsp")){
		classmodel.setTitle("");
		classmodel.setProperty("");
		classmodel.setTeacher("");
		classmodel.setTime("");
		
	}
	else{
	if(b.contains("必修"))
	  {
	  m=b.indexOf("必修");
	  finaltext= finaltext + "property----->"+"必修\n";
//	  tempmodel.setProperty("bixiu");
	  classmodel.setProperty("必修");
	  }			  
  else if(b.contains("限选"))
  {
	  m=b.indexOf("限选");
	  finaltext= finaltext +"property----->"+"限选\n";
	  classmodel.setProperty("限选");
  }		
  else if(b.contains("任选"))
  {
	  m=b.indexOf("任选");
	  finaltext= finaltext +"property----->"+"任选\n";
	  classmodel.setProperty("任选");
  }	
	
 String btitle=b.substring(0,m);
  finaltext= finaltext +"title----->"+btitle+"\n"; 
//  tempmodel.setTitle(btitle);
  classmodel.setTitle(btitle);
  
  n=b.lastIndexOf("}");
  
  String btime=b.substring(m+2,n+1);
  finaltext= finaltext +"time----->"+btime+"\n";
//  tempmodel.setTime(btime);
  classmodel.setTime(btime);
  
  m=b.indexOf("沙河");
  if(m<0)
  m=b.indexOf("学院");
  if(m<0)
  m=b.indexOf("体育馆");
  
  String bteacher = b.substring(n+1, m);
  finaltext= finaltext +"teacher----->"+bteacher+"\n";
//  tempmodel.setTeacher(bteacher);
  classmodel.setTeacher(bteacher);
  
  String bplace = b.substring(m);
  finaltext= finaltext +"place----->"+bplace+"\n";
//  tempmodel.setPlace(bplace);
  classmodel.setPlace(bplace);
	}
	
}


private OnClickListener xx2 = new OnClickListener() 
{
	public void onClick(View v2)
	{
		Intent intent2 = new Intent();
		intent2.setClass(MainActivity.this, kebiaojiemian.class);
		startActivity(intent2);
	}			
};



}





