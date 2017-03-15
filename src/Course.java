package com.example.popupmenutest;


import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class Course extends Activity {
	
	private int louhao;
	private int jiaoshihao ;
	private int xingqi;
	private TextView textview;
	private String table="classroom";
	private SQLfreeclassroom sqlclassroom;
    private FreeclassroomModel classroommodel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course);
		sqlclassroom=new SQLfreeclassroom(this);
		classroommodel=new FreeclassroomModel();
		textview=(TextView)findViewById(R.id.kecheng);
		String string[]=new String[20];
		int number[]=new int[5];
		String string0="";
		int n0=0,n1=10;

		//接收数据
		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		
		louhao=bundle.getInt("louhao");
		jiaoshihao=bundle.getInt("jiaoshihao");
		xingqi=bundle.getInt("xingqi");
		
		int j=0,k=0;

		while (sqlclassroom.queryboolean(j+"")) {
			
			classroommodel=sqlclassroom.getRoomById(j+"");
			String x=classroommodel.getLouhao();
			String y=classroommodel.getNum();
			

			
			String danshuang;
			if (classroommodel.getDanshuang()==1){
				danshuang="（单周）";
			}
			else if(classroommodel.getDanshuang()==0){
				danshuang="（双周）";
			}
			else {
				danshuang="";
			}
			int z=classroommodel.getXingqi();
			if ((louhao==Integer.parseInt(x))&&(jiaoshihao==Integer.parseInt(y))&&(xingqi==z)){
			    
			    if (classroommodel.getBiaozhi()==0){
			    	n0++;
				    number[n0]=classroommodel.getJie();
			    	 string[n0]="第"+classroommodel.getJie()+"节   "+classroommodel.getKecheng()+" "+classroommodel.getTeacher()
					    		+" "+"第"+classroommodel.getBegin()+"周至第"+classroommodel.getEnd()+"周  "+" "+danshuang+"\n";
			    	 Log.i(MainActivity.ACTIVITY_SERVICE,"2!!!!!!!!!!"+j+" "+n0+" "+classroommodel.getBiaozhi()+" jiaoshihao="+jiaoshihao+" y="+y+" louhao="+louhao+" x="+x+" xingqi="+xingqi+" z="+z);
			    }
			
			    else {
			    	n1++;
			    	 string[n1]=classroommodel.getKecheng()+" "+classroommodel.getShijian()
					    		+" "+"（活动）"+"\n";
			    	 Log.i(MainActivity.ACTIVITY_SERVICE,"2!!!!!!!!!!"+j+" "+n1+" "+classroommodel.getBiaozhi()+" jiaoshihao="+jiaoshihao+" y="+y+" louhao="+louhao+" x="+x+" xingqi="+xingqi+" z="+z);
			    }  
			}   
			
			j++;
		}
		int max=100,p=100;
		String ps="";
		for(j=1;j<=n0;j++){
			for(k=j;k<=n0;k++){
				if (number[k]<max){
					max=number[k];p=k;
				}
			}
			max=number[j];number[j]=number[p];number[p]=max;
			ps=string[j];string[j]=string[p];string[p]=ps;
			max=10;
		}
		for(j=1;j<=n0;j++){
			string0+=string[j];
		}
		for(j=11;j<=n1;j++){
			string0+=string[j];
		}
		textview.setText(string0);
	} 
}
