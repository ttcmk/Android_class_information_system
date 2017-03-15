package com.example.popupmenutest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EnterActivity extends Activity {

	private ImageView logoImageView;
	private TextView appnameTextView;
	private TextView studentNumberTextView;
	private TextView passwordTextView;
	private EditText studentNumberEditText;
	private EditText passwordEditText;
	private Button enterButton;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);						
		setContentView(R.layout.activity_enter);		
		
		logoImageView=(ImageView)findViewById(R.id.logoImageView);
		appnameTextView=(TextView)findViewById(R.id.appnameTextView);
		studentNumberTextView=(TextView)findViewById(R.id.studentNumberTextView);
		passwordTextView=(TextView)findViewById(R.id.passwordTextView);		
		studentNumberEditText=(EditText)findViewById(R.id.studentNumberEditText);
		passwordEditText=(EditText)findViewById(R.id.passwordEditText);
		enterButton=(Button)findViewById(R.id.enterButton);
		
		logoImageView.setImageResource(R.drawable.logo);
		appnameTextView.setText(R.string.appname);
		studentNumberTextView.setText(R.string.studentNumber);
		passwordTextView.setText(R.string.password);
		enterButton.setText(R.string.enter);
		
	}
	
	 public void onClick_Event(View view){
		 Intent intent = new Intent();
		 intent.setClass(EnterActivity.this, kebiaojiemian.class);
		 EnterActivity.this.startActivity(intent);
	 }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(0, 1, 1, R.string.exit);
		menu.add(0, 2, 2, R.string.about);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1){
			finish();
		}
		if(item.getItemId()==2){
			Dialog alertDialog = new AlertDialog.Builder(EnterActivity.this).setTitle("关于").setMessage(R.string.team).setNegativeButton("确定", null).create();
			alertDialog.show();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static int dip2px(Context context, float dpValue) {        
    	final float scale = context.getResources().getDisplayMetrics().density;        
    	return (int) (dpValue * scale + 0.5f);}
    public static int px2dip(Context context, float pxValue) {        
    	final float scale = context.getResources().getDisplayMetrics().density;        
    	return (int) (pxValue / scale + 0.5f);}

}
