package com.example.popupmenutest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.example.testandroid.R;

//import com.example.elist.DatebaseService;
//import com.example.elist.Classroom;
//import com.example.elist.Data;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity2 extends Activity   {
	
	private static ArrayList<Map<String,String>> parentData = new ArrayList<Map<String,String>>();
	private static ArrayList<ArrayList<Map<String,String>>> childData = new ArrayList<ArrayList<Map<String,String>>>();
//	SimpleExpandableListAdapter selAdapter;
//	public static SQLiteDatabase myDb;
	private SQLfreeclassroom sqlclassroom;
	private FreeclassroomModel classroommodel;
	private CalendarUtil tt = new CalendarUtil();
	private SQLWeeknumber sqlweek;
	private WeeknumberModel weekmodel;
	private String[] louhao={"主教","1号楼","2号楼","3号楼","4号楼","5号楼","6号楼","7号楼"};
	private boolean p=false;
	public int m=0;
	public static int[][] number={{22,102,104,105,106,107,108,109,110,111,207,208,209,210,211,212,213,214,215,216,217,218,219,
		301,302,303,304,305,306,307,308,309,
		401,402,403,404,405,406,407,408,409,
		501,502,503,504,505,506,507,508,509
		},
			{10,101,102,103,104,105,106,107,108,109,201},
			{10,101,102,103,104,105,106,107,108,109,201},
			{10,101,102,103,104,105,106,107,108,109,201},
			{11,101,102,103,104,105,106,107,108,109,201,202},
			{10,101,102,103,104,105,106,107,108,109,201},
			{10,101,102,103,104,105,106,107,108,109,201},
			{10,101,102,103,104,105,106,107,108,109,201}}; 
	private ExpandableListView elistview;
	private TextView tv;
	private Spinner spinner;
	
	public static SQLiteDatabase myDb;
	
	/**
	 *当前打开的父节点
	 */
	private int the_group_expand_position=-1;
	/**
	 * 打开的父节点所与的子节点数
	 */
	private int position_child_count=0;
	/**
	 * 是否有打开的父节点
	 */
	private boolean isExpanding=false;
	
	public void onCreate(Bundle saveBundle){

		super.onCreate(saveBundle);
		setContentView(R.layout.activity_main2);
		sqlclassroom=new SQLfreeclassroom(this);
		classroommodel=new FreeclassroomModel();
		
//		TextView sp_province = (TextView) findViewById(R.id.sp_province);
		ProvinceAsyncTask task = new ProvinceAsyncTask(this,sqlclassroom,classroommodel);
		task.execute();
	
//		infor();
		
		//Spinner
		Spinner spinner = null; 
    	ArrayAdapter<String> adapter = null;
    	String[] time = new String[] {"当前时间","第1,2节","第3,4节","第5,6节","第7,8节","第9,10,11节"};
    	spinner = (Spinner)findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, time);
        spinner.setAdapter(adapter);	
        
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
            	//点击事件
            	m=position;
            	operation(m);
                Log.d(MainActivity2.ACTIVITY_SERVICE,""+m);
            }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        });
		
		//多级列表
		elistview = (ExpandableListView)findViewById(R.id.qq_listview);
		//替换ExpandableListView的打开关闭时的箭头图标
		//elistview.setGroupIndicator(this.getResources().getDrawable(R.drawable.expand_list_selector));
		tv = (TextView)findViewById(R.id.qq_list_textview);
		
		/**
		 * 滑动子列表时在上方显示父节点的view
		 */
		final LinearLayout linear = (LinearLayout)findViewById(R.id.gone_linear);
		
		/**
		 * 监听父节点打开的事件
		 */
		elistview.setOnGroupExpandListener(new OnGroupExpandListener(){

			@Override
			public void onGroupExpand(int groupPosition) {
				the_group_expand_position=groupPosition;
				position_child_count=childData.get(groupPosition).size();
				isExpanding=true;
			}
			
		});
		
		/**
		 * 监听父节点关闭的事件
		 */
		elistview.setOnGroupCollapseListener(new OnGroupCollapseListener(){

			@Override
			public void onGroupCollapse(int groupPosition) {
				if(linear.getVisibility()==View.VISIBLE){
					linear.setVisibility(View.GONE);
				}
				isExpanding=false;
			}
			
		});
		

		linear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				linear.setVisibility(View.GONE);
				elistview.collapseGroup(the_group_expand_position);
			}
			
		});
		
		/**
		 * 通过setOnScrollListener来监听列表上下滑动时item显示和消失的事件
		 */
		elistview.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(isExpanding){
					// 当当前第一个item id小于打开的父节点id 或大于打开的父节点id和它的子节点总数之和时
					if(firstVisibleItem<the_group_expand_position ||
							firstVisibleItem>(the_group_expand_position+position_child_count)){
						linear.setVisibility(View.GONE);
					}else{
						linear.setVisibility(View.VISIBLE);
						tv.setText(((Map)parentData.get(the_group_expand_position)).get("parend").toString());
					}
				}
			}	
		});
		
		operation(m);
		
		elistview.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, 
					int childPosition, long id) {
				Intent intent=new Intent();
				intent.setClass(MainActivity2.this,Course.class);
				Calendar q = Calendar.getInstance();
				int xingqi=q.get(Calendar.DAY_OF_WEEK)-1;
				Bundle bundle =new Bundle();
				bundle.putInt("xingqi",xingqi);
				bundle.putInt("louhao", groupPosition);
				bundle.putInt("jiaoshihao",
						Integer.parseInt(((childData.get(groupPosition)).get(childPosition)).get("child")));
				intent.putExtras(bundle);
				startActivity(intent);
				return true;
			}
		});

	}
	


	private void operation(int m) {

		parentData.clear();
		childData.clear();
//		data= new Data();
		for(int i=0;i<8;i++){
			childData.add(getAllData("classroom0",i,m));
		} 
		for(int i=0; i<8;i++){
			 Map<String,String> map = new HashMap<String,String>();
			 map.put("parend", louhao[i]);
			 parentData.add(map);
		}
		SimpleExpandableListAdapter selAdapter;
		selAdapter = new SimpleExpandableListAdapter(this,
				parentData,
				R.layout.qq_list_parent,
				new String[]{"parend"},
				new int[]{R.id.parend},
				childData,
				R.layout.qq_listview_child,
				new String[]{"child"},
				new int[]{R.id.child}
				);
		elistview.setAdapter(selAdapter);
		
	}
	   
	public ArrayList<Map<String, String>> getAllData(String table,int louhao,int m) {
				ArrayList<Map<String, String>>listData = new ArrayList<Map<String, String>>();
				int j=0;
				// 获取表的内容
				int free[]=new int[600];
				for(int i=0;i<600;i++){
					free[i]=0;
				}
				for(int i=1;i<=number[louhao][0];i++){
					free[number[louhao][i]]=1;
				}	
				while (sqlclassroom.queryboolean(j+"")) {
					classroommodel=sqlclassroom.getRoomById(j+"");
					//获取时间
					Calendar q = Calendar.getInstance();
					int hour=q.get(Calendar.HOUR_OF_DAY);
					int minute=q.get(Calendar.MINUTE);
					int xingqi=q.get(Calendar.DAY_OF_WEEK)-1;
					int jie,time;
					int zhou=0;
					sqlweek=new SQLWeeknumber(this);
					weekmodel=new WeeknumberModel();
					
//					weekmodel=sqlweek.getWeekById("1");
//		        	//获取当前周周一日期
//		        	String x=tt.getNowTime("yyyy-MM-dd");
//		        	//获取数据中存储的原始周一日期
//		        	String y=weekmodel.getOtime();
//		        	//计算当前周周一日期与原始周周一日期的间隔天数
//		        	String nums=CalendarUtil.getTwoDay(tt.getNowTime("yyyy-MM-dd"),weekmodel.getOtime());
//		        	//计算当前周周一日期与原始周周一日期的间隔星期数
//		        	int num=Integer.parseInt(nums)/7;
//		        	//获取数据中记录的原始周数
//		        	int oweeknumber=Integer.parseInt(weekmodel.getWeeknumber());
//		        	//设定当前周数的显示位置
//		        	zhou=oweeknumber + num;
//		        	Log.i(MainActivity.ACTIVITY_SERVICE,"***!!!!!!!!"+zhou+" "+j);
					zhou=8;//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					time=hour*60+minute;
					if (m==0){
					if (time<60*9+50) jie=1;
					else if(time<60*12) jie=2;
					else if(time<60*15+20) jie=3;
					else if(time<60*17*60+30) jie=4;
					else jie=5;}
					else jie=m;
					
//					Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+j+" "+louhao);
					if (louhao==0){
						Log.i(MainActivity2.ACTIVITY_SERVICE,"*****"+j+" "+classroommodel.getJie()+" "+jie);
					}
					
					if ((louhao==Integer.parseInt(classroommodel.getLouhao()))&&(zhou>=classroommodel.getBegin())&&(zhou<=classroommodel.getEnd())
							&&(((zhou%2)==classroommodel.getDanshuang())||classroommodel.getDanshuang()==2)&&(xingqi==classroommodel.getXingqi())
							&&(classroommodel.getJie()==jie))
					{free[Integer.parseInt(classroommodel.getNum())]=2;
//					Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+j+free[Integer.parseInt(classroommodel.getNum())]);
					}
					
					j++;			
				}
				
				//数据加入列表
				p=false;	
				for(int i=1;i<=number[louhao][0];i++) {
						Map<String, String> map = new HashMap<String, String>();
						if (free[number[louhao][i]]==1)
						{map.put("child",""+number[louhao][i]);p=true;listData.add(map);}
					}
					 if (!p)
					 {Map<String, String> map = new HashMap<String, String>();
			         map.put("child","无");
			         listData.add(map);}
				 //返回数据
				return listData;
	}

}