package com.example.popupmenutest;

import com.example.popupmenutest.Huodongjiangzuo.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class Yonghurenzheng extends Activity {
	public static SQLiteDatabase myDb;

	private TextView huanyingText;
	private TextView yonghuText;
	private TextView mimaText;
	
	private EditText yonghuEdit;
	private EditText mimaEdit;
	
	private CheckBox ckbox;
	private Button button1;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renzheng);
        
        final sqlite sql = new sqlite();
        
        huanyingText = (TextView)findViewById(R.id.huanying);
        yonghuText = (TextView)findViewById(R.id.yonghu_label);
        mimaText = (TextView)findViewById(R.id.mima_label);
        
        yonghuEdit = (EditText)findViewById(R.id.yonghu);
        mimaEdit = (EditText)findViewById(R.id.mima);
        
        ckbox = (CheckBox)findViewById(R.id.checkbox1);
        
        button1 = (Button)findViewById(R.id.button1);
        
        String html="<font color='blue'><big>��ӭ������Ϣ��������</big></font><br>";   
        CharSequence charSequence=Html.fromHtml(html);  
        huanyingText.setText(charSequence);  
        //��ʾ�Զ����������ַ�
        mimaEdit.setTransformationMethod(PasswordTransformationMethod.getInstance()); 
        
        OnCheckedChangeListener listener=new OnCheckedChangeListener(){
        	 
    		@Override
    		public void onCheckedChanged(CompoundButton buttonView,
    				boolean isChecked) {
    			// TODO Auto-generated method stub
    			if(isChecked){
    				mimaEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    				//���ѡ�У���ʾ����
    			}else{
    				 mimaEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
    				 //������������
    			}
    			
    		}
        	
        };
        ckbox.setOnCheckedChangeListener(listener);
        
        	button1.setOnClickListener(new OnClickListener(){
        		
        		public void onClick(View v){
        			
        			boolean judge = false;
        			String nameInput ="";
        			String mimaInput ="";
        	        nameInput = yonghuEdit.getText().toString();
        	        mimaInput =	mimaEdit.getText().toString();
        			judge = sql.Query("client",nameInput,mimaInput);
        			if(judge == true){
        			Intent intent = new Intent();
                    intent.setClass(Yonghurenzheng.this,Fabuxinxi.class);
                    startActivity(intent);
        			}
        			 else{
        	        	 Toast.makeText(Yonghurenzheng.this, "�����û��������벻��ȷ", Toast.LENGTH_LONG).show();
        	         }
        	}
	
        	});
	}
 
  public class clientInfor{
	 public int[] id = new int[2];
	 public String[] name = new String[100];
	 public String[] passwd = new String[100];
	 
	//�����û����������
	 public clientInfor(){
		 name[0] = "123";
		 passwd[0] = "123";
		 
		 name[1] = "234";
		 passwd[1] = "234";
	 }
  } 
  
  public class sqlite{
	//���췽�����½�һ������
		public sqlite() {
			myDb = openOrCreateDatabase("users.db",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);
			// ��ʼ��������
			createTable(myDb, "client");
			//�����ʼ����
			insert(myDb, "client");
		}
		// ����һ�����ݿ�
					public void createTable(SQLiteDatabase mDb, String table) {
						try { 
							mDb.execSQL("DROP TABLE IF EXISTS "+table);
							mDb.execSQL("create table if not exists "
									+ table
									+ " (id integer primary key autoincrement, "
									+ "name text, passwd text);"
									);
						} catch (SQLException e) {
							Toast.makeText(getApplicationContext(), "���ݱ���ʧ��",
									Toast.LENGTH_LONG).show();
						}
					}

					// ��������
					public void insert(SQLiteDatabase mDb, String table) {

						// ��ʼ����������
						ContentValues values = new ContentValues();
						int i;
						int n=2;//nΪ��������Ŀ
						clientInfor infor=new clientInfor();
						for(i=0;i<n;i++){
							values.put("name", infor.name[i]);//�û���
							values.put("passwd", infor.passwd[i]);//����
							mDb.insert(table, null, values);
						}
					}

					
					// ��ѯ������������
					public boolean Query(String table,String s1,String s2) {
						Cursor c = myDb.rawQuery("select * from " + table, null);
						int i=0;
						while (c.moveToNext()) {
							//�������ݿ������л��Ϣ
							i++;
						    if(s1.equals (c.getString(1))&&s2.equals (c.getString(2)))
						    	{
						    	return true; 
						    	}
						}
						c.close();
						 return false;
					}
					

					// ɾ��һ������
					public boolean delete(SQLiteDatabase mDb, String table, int id) {
						String whereClause = "id=?";
						String[] whereArgs = new String[] { String.valueOf(id) };
						try {
							mDb.delete(table, whereClause, whereArgs);
						} catch (SQLException e) {
							//Toast.makeText(getApplicationContext(), "ɾ�����ݿ�ʧ��",
									//Toast.LENGTH_LONG).show();
							return false;
						}
						return true;
					}
				
			
  }
  }
  
