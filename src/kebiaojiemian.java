package com.example.popupmenutest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class kebiaojiemian extends ActionBarActivity implements View.OnClickListener{
	
	ActionBar actionBar;
	
	private int colors[] = {
			Color.rgb(0xee,0xff,0xff),
			Color.rgb(0xf0,0x96,0x09),
			Color.rgb(0x8c,0xbf,0x26),
			Color.rgb(0x00,0xab,0xa9),
			Color.rgb(0x99,0x6c,0x33),
			Color.rgb(0x3b,0x92,0xbc),
			Color.rgb(0xd5,0x4d,0x34),
			Color.rgb(0xcc,0xcc,0xcc)
		};
	
	//��������
	private Spinner weeknumberSpinner = null; 
	ArrayAdapter<String> weeknumberAdapter = null;
	private String[] weeknumber = new String[] {"��1��","��2��","��3��","��4��","��5��","��6��","��7��"
			,"��8��","��9��","��10��","��11��","��12��","��13��","��14��","��15��","��16��","��17��","��18��"
			,"��19��","��20��","��21��","��22��","��23��","��24��","��25��"};
	static int weeknumberPosition = 0;
	private WeeknumberModel weeknumberinfo;
	private SQLWeeknumber  sqlweek;
	private CalendarUtil tt = new CalendarUtil();
	private ClassInfoModel classinfo;
	private SQLiteHelper sqlclass;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	setTheme(R.style.Theme_AppCompat_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schooltimetable);
        
        actionBar = getSupportActionBar();
        
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        
		
		setSpinner();
		
		weeknumberinfo = new WeeknumberModel();
        sqlweek = new SQLWeeknumber(this);
		classinfo = new ClassInfoModel();
		sqlclass = new SQLiteHelper(this);
		
		LinearLayout ll1 = (LinearLayout)findViewById(R.id.ll1);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout)findViewById(R.id.ll3);
        LinearLayout ll4 = (LinearLayout)findViewById(R.id.ll4);
        LinearLayout ll5 = (LinearLayout)findViewById(R.id.ll5);
        LinearLayout ll6 = (LinearLayout)findViewById(R.id.ll6);
        LinearLayout ll7 = (LinearLayout)findViewById(R.id.ll7);
        
        weeknumberinfo=sqlweek.getWeekById("1");	       
    	int num=Integer.parseInt(CalendarUtil.getTwoDay(tt.getNowTime("yyyy-MM-dd"), weeknumberinfo.getOtime()))/7;	     	
    	int oweeknumber=Integer.parseInt(weeknumberinfo.getWeeknumber());
    	int sd=(num+oweeknumber)%2;
    	
    	if(sd==1){
    		for(int i=11;i<16;i++){
    			String id=Integer.toString(i*10);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll1);
        		}
        		else{
        			setClass(ll1, title, property, time, teacher,place, i-10);
        		}
    		}
    		
    		for(int i=21;i<26;i++){
    			String id=Integer.toString(i*10);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll2);
        		}
        		else{
        			setClass(ll2, title, property, time, teacher,place, i-19);
        		}
    		}
    	    		
    		for(int i=31;i<36;i++){
    			String id=Integer.toString(i*10);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll3);
        		}
        		else{
        			setClass(ll3, title, property, time, teacher,place, i-28);
        		}
    		}
    		
    		for(int i=41;i<46;i++){
    			String id=Integer.toString(i*10);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll4);
        		}
        		else{
        			setClass(ll4, title, property, time, teacher,place, i-38);
        		}
    		}
    		
    		for(int i=51;i<56;i++){
    			String id=Integer.toString(i*10);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll5);
        		}
        		else{
        			setClass(ll5, title, property, time, teacher,place, i-48);
        		}
    		}
    	}
    	else{
    		for(int i=11;i<16;i++){
    			String id=Integer.toString(i*10+1);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll1);
        		}
        		else{
        			setClass(ll1, title, property, time, teacher,place, i-10);
        		}
    		}
    		
    		for(int i=21;i<26;i++){
    			String id=Integer.toString(i*10+1);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll2);
        		}
        		else{
        			setClass(ll2, title, property, time, teacher,place, i-19);
        		}
    		}
    	    		
    		for(int i=31;i<36;i++){
    			String id=Integer.toString(i*10+1);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll3);
        		}
        		else{
        			setClass(ll3, title, property, time, teacher,place, i-28);
        		}
    		}
    		
    		for(int i=41;i<46;i++){
    			String id=Integer.toString(i*10+1);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll4);
        		}
        		else{
        			setClass(ll4, title, property, time, teacher,place, i-38);
        		}
    		}
    		
    		for(int i=51;i<56;i++){
    			String id=Integer.toString(i*10+1);
    			classinfo =sqlclass.getClassById(id);
    			String title=classinfo.getTitle();
    			String property=classinfo.getProperty();
        		String time=classinfo.getTime(); 
        		String teacher =classinfo.getTeacher();
        		String place=classinfo.getPlace();
        		if(title==""){
        			setNoClass(ll5);
        		}
        		else{
        			setClass(ll5, title, property, time, teacher,place, i-48);
        		}
    		}
    	}
//    	 setClass(ll1, "���Դ���", "����", "��һ��1,2��{��1-18��}", "���Ⱦ�","ɳ������213M", 1);
//        setClass(ll1, "Ӣ��������Ļ�(1)", "����", "��һ��3,4��{��1-18��}", "��boyu(2012��)", "ѧԺ¥2��¥202", 2);
//        setClass(ll1, "ë��˼����й���ɫ�������������ϵ����", "����","��һ��5,6��{��1-18��}","���","ɳ������209M",3);
//        setClass(ll1, "����������߿γ����", "����", "��һ��7,8��{��1-9��}", "����÷", "ɳ������104M", 4);
//        setClass(ll1, "ȫ���������й��⽻", "��ѡ", "��һ��9,10��{��1-18��}", "����/��ռ˳", "ɳ������212M", 5);
//        
//        setClass(ll2, "����ϵͳ��˫�", "����", "�ܶ���1,2��{��1-18��}", "����(��)", "ѧԺ¥3��¥217M", 3);
//        setClass(ll2, "�˳�ѧ", "��ѡ", "�ܶ���3,4��{��1-18��}", "�μ�Ʊ", "ɳ������403M", 4);
//        setClass(ll2, "ͳ��ѧ", "��ѡ", "�ܶ���5,6��{��1-18��}", "���ž�", "ɳ������302M", 5);
//        setNoClass(ll2);
//        setNoClass(ll2);
//        
//        setClass(ll3, "Web���򿪷�", "����", "������1,2��{��1-18��}", "��ѩ��", "ɳ������301M", 5);
//        setNoClass(ll3);
//        setNoClass(ll3);
//        setNoClass(ll3);
//        setNoClass(ll3);
//        
//        setClass(ll4, "�˳�ѧ", "��ѡ", "���ĵ�1,2��{��1-17��|����}", "�μ�Ʊ", "ɳ������403M", 7);
//        setClass(ll4, "��ѧ����(4)", "����", "���ĵ�3,4��{��1-18��}", "������", "������", 1);
//        setClass(ll4, "Ӣ��������Ļ�(1)", "����", "���ĵ�5,6��{��1-18��}", "��boyu(2012��)", "ɳ������502M", 2);
//        setNoClass(ll4);
//        setNoClass(ll4);
//        
//        setClass(ll5, "Web���򿪷�", "����", "�����1,2��{��1-18��}", "��ѩ��", "ɳ������301M", 2);
//        setClass(ll5, "����ϵͳ��˫�", "����", "�����3,4��{��1-17��|����}", "����(��)", "ѧԺ¥3��¥218M", 3);
//        setNoClass(ll5);
//        setClass(ll5, "ë��˼����й���ɫ�������������ϵ����", "����", "�����7,8��{��1-18��}", "���", "ɳ������106M", 5);
//        setNoClass(ll5);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(this, "����", 0).show();
			break;
		case R.id.action_more:
			createPopupMenu(findViewById(R.id.action_more));
			break;
		default:
			break;
		}
    	return true;
    }
	
  //��ӵ����������
	private void createPopupMenu(View v){
		PopupMenu popMenu = new PopupMenu(this, v);
		popMenu.inflate(R.menu.pop_menu);
		popMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			//��ӵ��item֮����ת
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
			if(arg0.getTitle().equals("�������ѯ")){
					
	                Intent intent = new Intent();
	                intent.setClass(kebiaojiemian.this,Huodongjiangzuo.class);
	                startActivity(intent);
			}
			//��ת����ѯ���н��ҵĽ���
			if(arg0.getTitle().equals("���н��Ҳ�ѯ")){
				
                Intent intent = new Intent();
                intent.setClass(kebiaojiemian.this,MainActivity2.class);
                startActivity(intent);
		      }
			//��ת��������Ϣ�Ľ���
			if(arg0.getTitle().equals("������Ϣ")){
				
                Intent intent = new Intent();
                intent.setClass(kebiaojiemian.this,Yonghurenzheng.class);
                startActivity(intent);
		      }
			//��ת�����ý���
            if(arg0.getTitle().equals("����")){
				
                Intent intent = new Intent();
                intent.setClass(kebiaojiemian.this,Settings.class);
                startActivity(intent);
		      }
		    	return false;
			}
		});
		popMenu.show();

	}

public void onClick(View arg0) {
	// TODO Auto-generated method stub
	
}	

private void setSpinner()
{        	    		 
    weeknumberSpinner = (Spinner)findViewById(R.id.weeknumber);
    
    weeknumberAdapter = new ArrayAdapter<String>(kebiaojiemian.this,
            android.R.layout.simple_spinner_item, weeknumber);
    weeknumberSpinner.setAdapter(weeknumberAdapter);	
    
    weeknumberinfo = new WeeknumberModel();
    sqlweek = new SQLWeeknumber(this);
        
   if(sqlweek.queryboolean("1")){
    	weeknumberinfo=sqlweek.getWeekById("1");
    	String nums="0";
    	//Log.i("aaaaaaaaaaaaaaaaaaaaaaaa",weeknumberinfo.getOtime());
    	nums=CalendarUtil.getTwoDay(tt.getNowTime("yyyy-MM-dd"),weeknumberinfo.getOtime());	        		     	
    	int num=Integer.parseInt(nums)/7;	     	
    	int oweeknumber=Integer.parseInt(weeknumberinfo.getWeeknumber());
    	weeknumberPosition=oweeknumber + num-1;
    weeknumberSpinner.setSelection(weeknumberPosition,true);
   }
     else{
    	weeknumberinfo.setId("1");
    	weeknumberinfo.setOtime(tt.getMondayOFWeek("yyyy-MM-dd"));
    	weeknumberinfo.setWeeknumber("1");
    	sqlweek.create(weeknumberinfo);
    	weeknumberSpinner.setSelection(weeknumberPosition,true);
    }	      
    
    weeknumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
	
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
        {
            //positionΪ��ǰѡ�е�ֵ�����	             
        	weeknumberPosition = position;    //��¼��ǰ��ţ����������޸���
        	weeknumberinfo.setId("1");
        	weeknumberinfo.setWeeknumber(Integer.toString(weeknumberPosition+1));
        	String ot=tt.getMondayOFWeek("yyyy-MM-dd");
        	weeknumberinfo.setOtime(ot);
        	sqlweek.update(weeknumberinfo);	       
        	
        	startActivity(new Intent(kebiaojiemian.this,kebiaojiemian.class));
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0)
        {

        }

    });

}

void setClass(LinearLayout ll, String title, String property,
	String time, String teacher, String place, int color)
{
View view = LayoutInflater.from(this).inflate(R.layout.activity_schooltimetable_item, null);
view.setMinimumHeight(dip2px(this,150));
view.setBackgroundColor(colors[color]);
((TextView)view.findViewById(R.id.title)).setText(title);
((TextView)view.findViewById(R.id.property)).setText(property);
((TextView)view.findViewById(R.id.time)).setText(time);
((TextView)view.findViewById(R.id.teacher)).setText(teacher);
((TextView)view.findViewById(R.id.place)).setText(place);
view.setOnClickListener(new OnClickClassListener());
TextView blank1 = new TextView(this);
TextView blank2 = new TextView(this);
blank1.setHeight(dip2px(this,2));
blank2.setHeight(dip2px(this,2));
ll.addView(blank1);
ll.addView(view);
ll.addView(blank2);
}

void setNoClass(LinearLayout ll)
{
TextView blank = new TextView(this);
blank.setMinHeight(dip2px(this,150));
blank.setBackgroundColor(colors[0]);
ll.addView(blank);
}

class OnClickClassListener implements OnClickListener{

public void onClick(View v) {
	// TODO Auto-generated method stub
	String title,property,time,teacher,place;
	
	title = (String) ((TextView)v.findViewById(R.id.title)).getText();
	property = (String) ((TextView)v.findViewById(R.id.property)).getText();
	time = (String) ((TextView)v.findViewById(R.id.time)).getText();
	teacher = (String) ((TextView)v.findViewById(R.id.teacher)).getText();
	place = (String) ((TextView)v.findViewById(R.id.place)).getText();
	
	final String[] arrayItem = new String[] { "�γ̣�"+title, "���ʣ�"+property, "�Ͽ�ʱ�䣺"+time,"��ʦ��"+ teacher, "�Ͽεص㣺"+place};
	
	Dialog alertDialog = new AlertDialog.Builder(kebiaojiemian.this).setTitle("��ϸ��Ϣ").setItems(arrayItem,new DialogInterface.OnClickListener() { 
		  
        @Override 
        public void onClick(DialogInterface dialog, int which) { 
            Toast.makeText(kebiaojiemian.this, arrayItem[which], Toast.LENGTH_SHORT).show(); 
        } 
    }).setPositiveButton("ȷ��" ,  null ).create();
	alertDialog.show(); 
}
}


public static int dip2px(Context context, float dpValue) {        
final float scale = context.getResources().getDisplayMetrics().density;        
return (int) (dpValue * scale + 0.5f);}
public static int px2dip(Context context, float pxValue) {        
final float scale = context.getResources().getDisplayMetrics().density;        
return (int) (pxValue / scale + 0.5f);}

}
