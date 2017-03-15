package com.example.popupmenutest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity{
	
	private final int SPLASH_DISPLAY_LENGHT = 3000;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();				
			}
			
		}, SPLASH_DISPLAY_LENGHT);
	}
}
