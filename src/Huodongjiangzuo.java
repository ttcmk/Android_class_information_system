package com.example.popupmenutest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.popupmenutest.HuodongModel;
import com.example.popupmenutest.SQLhuodong;
import com.example.popupmenutest.Yonghurenzheng.sqlite;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
//import android.view.Menu;
import android.widget.Toast;

public class Huodongjiangzuo extends ListActivity{
	
	public static SQLiteDatabase myDb;
	private SQLhuodong sqlhuodong;
	private HuodongModel huodongmodel;
	private HuodongModel huodongmode2;
	 
	 public List<Map<String, Object>> mData=new ArrayList<Map<String, Object>>();
//	 public TextView lianjie; 

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	sqlhuodong=new SQLhuodong(this);
		huodongmodel=new HuodongModel();
		
		ProvinceAsyncTask2 task = new ProvinceAsyncTask2(this,sqlhuodong,huodongmodel);
		task.execute();
		
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
        
//      sqlite sql = new sqlite();
//	    infor();
        mData = getAllData("huodong");
        
  /*     lianjie=(TextView)this.findViewById(R.id.lianjie);
       lianjie.setText("http://www.baidu.com.cn");  //跳转到中财公告界面
       lianjie.setMovementMethod(LinkMovementMethod.getInstance());
   */
    }
	 private void infor() {
		// TODO Auto-generated method stub
//		sqlhuodong=new SQLhuodong(this);
//		huodongmodel=new HuodongModel();
//		huodongmode2=new HuodongModel();
//		
//		huodongmodel.setId("0");
//		huodongmodel.setNum("110");
//		huodongmodel.setLouhao("0");
//		
//		huodongmodel.setKecheng("大数据讲座");
//		huodongmodel.setZhubanfang("信息学院");
//		huodongmodel.setTeacher("王晓明");
//		huodongmodel.setShijian("2014-12-12");
//		huodongmodel.setMiaoshu("pretty good!!!");
//		sqlhuodong.create(huodongmodel);
//		sqlhuodong.update(huodongmodel);
//		
//		huodongmode2.setId("1");
//		huodongmode2.setNum("111");
//		huodongmode2.setLouhao("0");
//		sqlhuodong.create(huodongmode2);
//		huodongmode2.setKecheng("巴拉比巴博讲座");
//		huodongmode2.setZhubanfang("信息学院");
//		huodongmode2.setTeacher("王峥");
//		huodongmode2.setShijian("2014-12-26");
//		huodongmode2.setMiaoshu("pretty good！！！！！");
//		
//		sqlhuodong.update(huodongmode2);
	}
	private List<Map<String, Object>> getAllData(String string) {
		// TODO Auto-generated method stub
//			Cursor c = myDb.rawQuery("select * from " + table, null);
//			int columnsSize = 1;
			ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			//存储所有返回信息
			int i=0;
			Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+sqlhuodong.queryboolean(i+""));
			while (sqlhuodong.queryboolean(i+"")) {
				huodongmodel=sqlhuodong.getRoomById(i+"");
				//返回数据库中所有活动信息
				
				Map<String, Object> map=new HashMap<String,Object>();
				
				Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+" "+huodongmodel.getKecheng()+" "+huodongmodel.getMiaoshu()+" ");
				//设置每条返回信息的内容，用c.getString(num)获取字符串数据，c.getInt(num)获取整型数据，其中num为在建表示要获取的属性是第几个，从0开始
				map.put("title",huodongmodel.getKecheng()+" "+huodongmodel.getTeacher());
		        map.put("info", huodongmodel.getLouhao()+" "+huodongmodel.getNum()+" "+huodongmodel.getShijian());
		        map.put("detail",huodongmodel.getMiaoshu());
		        list.add(map);
		        i++;

			}
//			c.close();
			 //返回数据
			return list;
	}
	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        // 如果是返回键
	        if(keyCode == KeyEvent.KEYCODE_BACK){
	           //want to do
	          Intent intent=new Intent();
	          intent.setClass(Huodongjiangzuo.this,kebiaojiemian.class);
	         startActivity(intent);
	         Huodongjiangzuo.this.finish();
	         
	        }
	        return super.onKeyDown(keyCode, event);
	 }

	    public void onListItemClick(ListView l, View v, int position, long id) {
	         
	        Log.v("MyListView4-click", (String)mData.get(position).get("title"));
	    }
	 
 public void showInfo(List<Map<String, Object>> mData,int position){
	        new AlertDialog.Builder(this)
	        .setTitle("详细信息")
	        .setMessage((String)mData.get(position).get("detail"))
	        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        })
	        .show();
	 }
 
	 public final class ViewHolder{
	        public TextView title;
	        public TextView info;
	        public TextView detail;
	    }
	     
	     
	 public class MyAdapter extends BaseAdapter{
	 
	        private LayoutInflater mInflater;
	         
	         
	        public MyAdapter(Context context){
	            this.mInflater = LayoutInflater.from(context);
	        }
	        @Override
	        public int getCount() {
	            // TODO Auto-generated method stub
	            return mData.size();
	        }
	 
	        @Override
	        public Object getItem(int arg0) {
	            // TODO Auto-generated method stub
	            return null;
	        }
	 
	        @Override
	        public long getItemId(int arg0) {
	            // TODO Auto-generated method stub
	            return 0;
	        }
	 //将position改成final
	        @Override
	        public View getView(final int position, View convertView, ViewGroup parent) {
	             
	            ViewHolder holder = null;
	            if (convertView == null) {
	                 
	              holder=new ViewHolder();  
	                 
	                convertView = mInflater.inflate(R.layout.viewlist, null);
	                holder.title = (TextView)convertView.findViewById(R.id.title);
	                holder.info = (TextView)convertView.findViewById(R.id.info);
	                holder.detail = (TextView)convertView.findViewById(R.id.detail);
	                convertView.setTag(holder);
	                 
	            }else {
	                 
	                holder = (ViewHolder)convertView.getTag();
	            }
	             
	            holder.title.setText((String)mData.get(position).get("title"));
	            holder.info.setText((String)mData.get(position).get("info"));
	            holder.detail.setText((String)mData.get(position).get("detail")); 
	            
	            holder.title.setOnClickListener(new View.OnClickListener() {    
	                @Override
	                public void onClick(View v) {
	                	 showInfo(mData,position);
	            	      }
	           
	            });
	           
	            return convertView;
	        }
	 }
	 
	 public class Infor{
//			public int[] id=new int[300];
//			public String[] num=new String[300];//教室号，橙厅等为空
//			public String[] louhao=new String[300];//地点
//			public int[] xingqi=new int[300];//星期几
//			public int[] jie=new int[300];//空
//			public int[] danshuang=new int[300];//空
//			public int[] begin=new int[300];//空
//			public int[] end=new int[300];//空
//			public String[] kecheng=new String[300];//活动名称
//			public String[] teacher=new String[300];//主讲人
//			
//			public String[] zhubanfang=new String[300];
//			public String[] shijian=new String[300];
//			public String[] miaoshu=new String[300];
//			public int[] biaozhi=new int[300];//区别是活动还是课程，此处活动全部设置为1
//			
//			public Infor(){
//				id[0] = 0;
//				num[0] = "110";
//				louhao[0] = "主教";
//				kecheng[0] = "大数据讲座";
//				teacher[0] = "王小明";
//				zhubanfang[0] = "信息学院";
//				shijian[0] = "2014-12-12";
//				miaoshu[0] = "非常好好好好好好好好好好好好好好好好好好好好好好好好好好好！";
//				
//				id[1] = 1;
//				num[1] = "111";
//				louhao[1] = "主教";
//				kecheng[1] = "法学讲座";
//				teacher[1] = "王小明";
//				zhubanfang[1] = "法学院";
//				shijian[1] = "2014-12-11";
//				miaoshu[1] = "非常好！";
//				//获取数据并对上述声明的数组进行赋值
//				
//			}
  		}
//		
//		//数据库相关操作
		public class sqlite {
//			//构造方法，新建一个数组
//			public sqlite() {
//				myDb = openOrCreateDatabase("users.db",
//						SQLiteDatabase.CREATE_IF_NECESSARY, null);
//				// 初始化创建表
//				createTable(myDb, "huodong");
//				//插入初始数据
//				insert(myDb, "huodong");
//			}
//
//			// 创建一个数据库
//			public void createTable(SQLiteDatabase mDb, String table) {
//				try { 
//					mDb.execSQL("DROP TABLE IF EXISTS "+table);
//					mDb.execSQL("create table if not exists "
//							+ table
//							+ " (id integer primary key autoincrement, "
//							+ "kecheng text, louhao text,"
//							+ "num text, shijian text,"
//							+ "miaoshu text, teacher text,"
//							+ "zhubanfang text, xingqi integer);"
//							);
//				} catch (SQLException e) {
//					Toast.makeText(getApplicationContext(), "数据表创建失败",
//							Toast.LENGTH_LONG).show();
//				}
//			}
//
//			// 插入数据
//			public void insert(SQLiteDatabase mDb, String table) {
//
//				// 初始化插入数据
//				ContentValues values = new ContentValues();
//				int i;
//				int n=2;//n为数据项数目
//				Infor infor=new Infor();
//				for(i=0;i<n;i++){
//					values.put("num", infor.num[i]);//教室号 橙厅为空0
//					values.put("louhao", infor.louhao[i]);//地点 直接写橙厅1
//					values.put("kecheng", infor.kecheng[i]);//活动名称2
//					values.put("shijian", infor.shijian[i]);//时间3
//					values.put("miaoshu", infor.miaoshu[i]);//具体信息4
//					values.put("teacher", infor.teacher[i]);//主讲人5
//					values.put("zhubanfang", infor.zhubanfang[i]);//主办方6
//					values.put("xingqi", infor.xingqi[i]);//星期几7
//					mDb.insert(table, null, values);
//				}
//			}
//
//			
//			// 查询返回所有数据
//			public ArrayList<Map<String, Object>> getAllData(String table) {
//				Cursor c = myDb.rawQuery("select * from " + table, null);
//				int columnsSize = 1;
//				ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
//				//存储所有返回信息
//				int i=0;
//				while (c.moveToNext()) {
//					//返回数据库中所有活动信息
//					i++;
//					Map<String, Object> map=new HashMap<String,Object>();
//					//设置每条返回信息的内容，用c.getString(num)获取字符串数据，c.getInt(num)获取整型数据，其中num为在建表示要获取的属性是第几个，从0开始
//					map.put("title",c.getString(1)+" "+c.getString(6));
//			        map.put("info", c.getString(2)+" "+c.getString(3)+" "+c.getString(4));
//			        map.put("detail",c.getString(5));
//			        list.add(map);
//
//				}
//				c.close();
//				 //返回数据
//				return list;
//			}
//			
//
//			// 删除一条数据
//			public boolean delete(SQLiteDatabase mDb, String table, int id) {
//				String whereClause = "id=?";
//				String[] whereArgs = new String[] { String.valueOf(id) };
//				try {
//					mDb.delete(table, whereClause, whereArgs);
//				} catch (SQLException e) {
//					//Toast.makeText(getApplicationContext(), "删除数据库失败",
//							//Toast.LENGTH_LONG).show();
//					return false;
//				}
//				return true;
//			}
//		
	}

	         
	    }
	     
