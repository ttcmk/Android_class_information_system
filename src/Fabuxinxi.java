package com.example.popupmenutest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class Fabuxinxi extends Activity{

	/**
	 * @param args
	 */
	private EditText nameText;
	private EditText zhujiangrenText;
	private EditText timeText;
	private EditText didianText;
	private EditText zhubanfangText;
	private EditText detailText;
	
	private TextView labelView;
	private TextView displayView;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.input);
	        
	 nameText = (EditText)findViewById(R.id.name);
	 zhujiangrenText = (EditText)findViewById(R.id.zhujiangren);
	 timeText = (EditText)findViewById(R.id.time);
	 didianText = (EditText)findViewById(R.id.didian);
	 zhubanfangText = (EditText)findViewById(R.id.zhubanfang);
	 detailText = (EditText)findViewById(R.id.detail);
	 }
	 //保存用户输入的数据
	 public void save(View v){
	 String name = nameText.getText().toString();
	 String zhujiangren = zhujiangrenText.getText().toString();
	 String time = timeText.getText().toString();
	 String didian = didianText.getText().toString();
	 String zhubanfang = zhubanfangText.getText().toString();
	 String detail = detailText.getText().toString();
	 
	 boolean result=NewsService.save(name,zhujiangren,time,didian,zhubanfang,detail);
 	 if(result){
 		Toast.makeText(getApplicationContext(), R.string.success, 1).show();
 	}else{
 		Toast.makeText(getApplicationContext(), R.string.error, 1).show();
 	}
	 }
}