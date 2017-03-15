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
       lianjie.setText("http://www.baidu.com.cn");  //��ת���вƹ������
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
//		huodongmodel.setKecheng("�����ݽ���");
//		huodongmodel.setZhubanfang("��ϢѧԺ");
//		huodongmodel.setTeacher("������");
//		huodongmodel.setShijian("2014-12-12");
//		huodongmodel.setMiaoshu("pretty good!!!");
//		sqlhuodong.create(huodongmodel);
//		sqlhuodong.update(huodongmodel);
//		
//		huodongmode2.setId("1");
//		huodongmode2.setNum("111");
//		huodongmode2.setLouhao("0");
//		sqlhuodong.create(huodongmode2);
//		huodongmode2.setKecheng("�����ȰͲ�����");
//		huodongmode2.setZhubanfang("��ϢѧԺ");
//		huodongmode2.setTeacher("���");
//		huodongmode2.setShijian("2014-12-26");
//		huodongmode2.setMiaoshu("pretty good����������");
//		
//		sqlhuodong.update(huodongmode2);
	}
	private List<Map<String, Object>> getAllData(String string) {
		// TODO Auto-generated method stub
//			Cursor c = myDb.rawQuery("select * from " + table, null);
//			int columnsSize = 1;
			ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
			//�洢���з�����Ϣ
			int i=0;
			Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+sqlhuodong.queryboolean(i+""));
			while (sqlhuodong.queryboolean(i+"")) {
				huodongmodel=sqlhuodong.getRoomById(i+"");
				//�������ݿ������л��Ϣ
				
				Map<String, Object> map=new HashMap<String,Object>();
				
				Log.i(MainActivity.ACTIVITY_SERVICE,"!!!!!!!!!!!!"+i+" "+huodongmodel.getKecheng()+" "+huodongmodel.getMiaoshu()+" ");
				//����ÿ��������Ϣ�����ݣ���c.getString(num)��ȡ�ַ������ݣ�c.getInt(num)��ȡ�������ݣ�����numΪ�ڽ���ʾҪ��ȡ�������ǵڼ�������0��ʼ
				map.put("title",huodongmodel.getKecheng()+" "+huodongmodel.getTeacher());
		        map.put("info", huodongmodel.getLouhao()+" "+huodongmodel.getNum()+" "+huodongmodel.getShijian());
		        map.put("detail",huodongmodel.getMiaoshu());
		        list.add(map);
		        i++;

			}
//			c.close();
			 //��������
			return list;
	}
	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	        // ����Ƿ��ؼ�
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
	        .setTitle("��ϸ��Ϣ")
	        .setMessage((String)mData.get(position).get("detail"))
	        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
	 //��position�ĳ�final
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
//			public String[] num=new String[300];//���Һţ�������Ϊ��
//			public String[] louhao=new String[300];//�ص�
//			public int[] xingqi=new int[300];//���ڼ�
//			public int[] jie=new int[300];//��
//			public int[] danshuang=new int[300];//��
//			public int[] begin=new int[300];//��
//			public int[] end=new int[300];//��
//			public String[] kecheng=new String[300];//�����
//			public String[] teacher=new String[300];//������
//			
//			public String[] zhubanfang=new String[300];
//			public String[] shijian=new String[300];
//			public String[] miaoshu=new String[300];
//			public int[] biaozhi=new int[300];//�����ǻ���ǿγ̣��˴��ȫ������Ϊ1
//			
//			public Infor(){
//				id[0] = 0;
//				num[0] = "110";
//				louhao[0] = "����";
//				kecheng[0] = "�����ݽ���";
//				teacher[0] = "��С��";
//				zhubanfang[0] = "��ϢѧԺ";
//				shijian[0] = "2014-12-12";
//				miaoshu[0] = "�ǳ��úúúúúúúúúúúúúúúúúúúúúúúúúúã�";
//				
//				id[1] = 1;
//				num[1] = "111";
//				louhao[1] = "����";
//				kecheng[1] = "��ѧ����";
//				teacher[1] = "��С��";
//				zhubanfang[1] = "��ѧԺ";
//				shijian[1] = "2014-12-11";
//				miaoshu[1] = "�ǳ��ã�";
//				//��ȡ���ݲ�������������������и�ֵ
//				
//			}
  		}
//		
//		//���ݿ���ز���
		public class sqlite {
//			//���췽�����½�һ������
//			public sqlite() {
//				myDb = openOrCreateDatabase("users.db",
//						SQLiteDatabase.CREATE_IF_NECESSARY, null);
//				// ��ʼ��������
//				createTable(myDb, "huodong");
//				//�����ʼ����
//				insert(myDb, "huodong");
//			}
//
//			// ����һ�����ݿ�
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
//					Toast.makeText(getApplicationContext(), "���ݱ���ʧ��",
//							Toast.LENGTH_LONG).show();
//				}
//			}
//
//			// ��������
//			public void insert(SQLiteDatabase mDb, String table) {
//
//				// ��ʼ����������
//				ContentValues values = new ContentValues();
//				int i;
//				int n=2;//nΪ��������Ŀ
//				Infor infor=new Infor();
//				for(i=0;i<n;i++){
//					values.put("num", infor.num[i]);//���Һ� ����Ϊ��0
//					values.put("louhao", infor.louhao[i]);//�ص� ֱ��д����1
//					values.put("kecheng", infor.kecheng[i]);//�����2
//					values.put("shijian", infor.shijian[i]);//ʱ��3
//					values.put("miaoshu", infor.miaoshu[i]);//������Ϣ4
//					values.put("teacher", infor.teacher[i]);//������5
//					values.put("zhubanfang", infor.zhubanfang[i]);//���췽6
//					values.put("xingqi", infor.xingqi[i]);//���ڼ�7
//					mDb.insert(table, null, values);
//				}
//			}
//
//			
//			// ��ѯ������������
//			public ArrayList<Map<String, Object>> getAllData(String table) {
//				Cursor c = myDb.rawQuery("select * from " + table, null);
//				int columnsSize = 1;
//				ArrayList<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
//				//�洢���з�����Ϣ
//				int i=0;
//				while (c.moveToNext()) {
//					//�������ݿ������л��Ϣ
//					i++;
//					Map<String, Object> map=new HashMap<String,Object>();
//					//����ÿ��������Ϣ�����ݣ���c.getString(num)��ȡ�ַ������ݣ�c.getInt(num)��ȡ�������ݣ�����numΪ�ڽ���ʾҪ��ȡ�������ǵڼ�������0��ʼ
//					map.put("title",c.getString(1)+" "+c.getString(6));
//			        map.put("info", c.getString(2)+" "+c.getString(3)+" "+c.getString(4));
//			        map.put("detail",c.getString(5));
//			        list.add(map);
//
//				}
//				c.close();
//				 //��������
//				return list;
//			}
//			
//
//			// ɾ��һ������
//			public boolean delete(SQLiteDatabase mDb, String table, int id) {
//				String whereClause = "id=?";
//				String[] whereArgs = new String[] { String.valueOf(id) };
//				try {
//					mDb.delete(table, whereClause, whereArgs);
//				} catch (SQLException e) {
//					//Toast.makeText(getApplicationContext(), "ɾ�����ݿ�ʧ��",
//							//Toast.LENGTH_LONG).show();
//					return false;
//				}
//				return true;
//			}
//		
	}

	         
	    }
	     
