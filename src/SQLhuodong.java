package com.example.popupmenutest;

import com.example.popupmenutest.HuodongModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLhuodong {
	
	public static final String ID = "id";
	public static final String NUM = "num";
	public static final String LOUHAO = "louhao";
	public static final String XINGQI = "xingqi";
	public static final String JIE = "jie";
	public static final String DANSHUANG = "danshuang";
	public static final String BEGIN = "begin";
	public static final String END = "end";
	public static final String KECHENG = "kecheng";
	public static final String TEACHER = "teacher";
	public static final String ZHUBANFANG = "zhubanfang";
	public static final String SHIJIAN = "shijian";
	public static final String MIAOSHU = "miaoshu";
	public static final String BIAOZHI = "biaozhi";

		
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME = "dbclassroom3";
	private static final String DATABASE_TABLE = "classroom";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE ="create table classroom (id integer primary key autoincrement, "
			+ "num text not null, louhao text not null,"
			+ "xingqi integer,jie integer,"
			+ "danshuang integer,begin integer,end integer,"
			+ "kecheng text,teacher text,zhubanfang text, shijian text,"
			+ "miaoshu text, biaozhi integer);";

	private final Context mCtx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			//Log.i(MainActivity.ACTIVITY_SERVICE,"2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//Log.i(MainActivity.ACTIVITY_SERVICE,"3!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			db.execSQL("DROP TABLE IF EXISTS classroom");
			onCreate(db);
		}
	}
	
	public SQLhuodong(Context ctx) {
		this.mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);

		mDb = mDbHelper.getWritableDatabase();
		// mDbHelper.onUpgrade(mDb, 1, 1);
	}

	public void close() {
		mDbHelper.close();
	}
	
	public boolean create(HuodongModel roomModel) {
		//Log.i(MainActivity.ACTIVITY_SERVICE,"4!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (queryboolean(roomModel.getId()))
			return update(roomModel);
//		Log.i(MainActivity.ACTIVITY_SERVICE,"5!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		ContentValues values = new ContentValues();
		values.put(ID, roomModel.getId());
		values.put(NUM, roomModel.getNum());
		values.put(LOUHAO, roomModel.getLouhao());
		values.put(XINGQI, roomModel.getXingqi());
		values.put(JIE, roomModel.getJie());
		values.put(DANSHUANG, roomModel.getDanshuang());
		values.put(BEGIN, roomModel.getBegin());
		values.put(END, roomModel.getEnd());
		values.put(KECHENG, roomModel.getKecheng());
		values.put(TEACHER, roomModel.getTeacher());
		values.put(ZHUBANFANG, roomModel.getZhubanfang());
		values.put(SHIJIAN, roomModel.getShijian());
		values.put(MIAOSHU, roomModel.getMiaoshu());
		values.put(BIAOZHI, roomModel.getBiaozhi());
		

		if (mDb.insert(DATABASE_TABLE, null, values) < 0)
			return false;
		else
			return true;
	}

	public boolean update(HuodongModel roomModel) {
		Log.i(MainActivity.ACTIVITY_SERVICE,"6!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+roomModel.getId());
		ContentValues values = new ContentValues();
		values.put(ID, roomModel.getId());
		if (roomModel.getNum() !="")
			values.put(NUM, roomModel.getNum());
		if (roomModel.getLouhao() !="")
			values.put(LOUHAO, roomModel.getLouhao());
		if (roomModel.getXingqi() !=0)
			values.put(XINGQI, roomModel.getXingqi());
		if (roomModel.getJie() !=0)
			values.put(JIE, roomModel.getJie());
		if (roomModel.getDanshuang() !=0)
			values.put(DANSHUANG, roomModel.getDanshuang());
		if (roomModel.getBegin() !=0)
			values.put(BEGIN, roomModel.getBegin());
		if (roomModel.getEnd() !=0)
			values.put(END, roomModel.getEnd());
		if (roomModel.getKecheng() !="")
			values.put(KECHENG, roomModel.getKecheng());
		if (roomModel.getTeacher() !="")
			values.put(TEACHER, roomModel.getTeacher());
		if (roomModel.getZhubanfang() !="")
			values.put(ZHUBANFANG, roomModel.getZhubanfang());
		if (roomModel.getBiaozhi() !=0)
			values.put(BIAOZHI, roomModel.getBiaozhi());
		if (roomModel.getShijian() !="")
			values.put(SHIJIAN, roomModel.getShijian());
		if (roomModel.getMiaoshu() !="")
			values.put(MIAOSHU, roomModel.getMiaoshu());
		
		if (mDb.update(DATABASE_TABLE, values, ID + "=" + roomModel.getId(),
				null) < 0)
			return false;
		else
			return true;
	}

	public boolean deleteById(String id) {
		return mDb.delete(DATABASE_TABLE, ID + "=" + id, null) > 0;
	}

	public Cursor getAllNotes() {
		return mDb.query(DATABASE_TABLE, new String[] { 
				ID, NUM, LOUHAO, XINGQI, JIE, DANSHUANG,BEGIN, END, KECHENG, TEACHER, ZHUBANFANG, SHIJIAN, MIAOSHU, BIAOZHI}, null,
				null, null, null, null);
	}

	public boolean deleteAll() {
		return mDb.delete(DATABASE_TABLE, ID + "< 99999", null) > 0;
	}

	public Cursor query(String id) {
		Cursor cursor = mDb.query(DATABASE_TABLE,
				new String[] { ID, NUM, LOUHAO, XINGQI, JIE, DANSHUANG,BEGIN, END, KECHENG, TEACHER, ZHUBANFANG, SHIJIAN, MIAOSHU, BIAOZHI}, ID + "=" + id, null, null,
				null, null, null);
		return cursor;
	}

	public boolean queryboolean(String id) {
		//Log.i(MainActivity.ACTIVITY_SERVICE,"");
		Cursor cursor = mDb.query(DATABASE_TABLE,
				new String[] { ID, NUM, LOUHAO, XINGQI, JIE, DANSHUANG,BEGIN, END, KECHENG, TEACHER, ZHUBANFANG, SHIJIAN, MIAOSHU, BIAOZHI}, ID + "=" + id, null, null,
				null, null, null);
		//Log.i(MainActivity.ACTIVITY_SERVICE,"8!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (cursor.getCount() == 0)
			return false;
		else
			//Log.i(MainActivity.ACTIVITY_SERVICE,"");
			return true;
	}
	
	public HuodongModel getRoomById(String id) {
		HuodongModel temp = new HuodongModel();
		Cursor notes = query(id);
		if (notes.getCount() > 0) {
			notes.moveToFirst();
			int idIndex = notes.getColumnIndex("id");
			int numIndex = notes.getColumnIndex("num");
			int louhaoIndex = notes.getColumnIndex("louhao");
			int xingqiIndex = notes.getColumnIndex("xingqi");
			int jieIndex = notes.getColumnIndex("jie");
			int danshuangIndex = notes.getColumnIndex("danshuang");
			int beginIndex = notes.getColumnIndex("begin");
			int endIndex = notes.getColumnIndex("end");
			int kechengIndex = notes.getColumnIndex("kecheng");
			int teacherIndex = notes.getColumnIndex("teacher");
			int zhubanfangIndex = notes.getColumnIndex("zhubanfang");
			int shijianIndex = notes.getColumnIndex("shijian");
			int miaoshuIndex = notes.getColumnIndex("miaoshu");
			int biaozhiIndex = notes.getColumnIndex("biaozhi");


			temp.setId(notes.getString(idIndex));
			temp.setNum(notes.getString(numIndex));
			temp.setLouhao(notes.getString(louhaoIndex));
			temp.setXingqi(notes.getInt(xingqiIndex));
			temp.setJie(notes.getInt(jieIndex));
			temp.setDanshuang(notes.getInt(danshuangIndex));
			temp.setBegin(notes.getInt(beginIndex));
			temp.setEnd(notes.getInt(endIndex));
			temp.setKecheng(notes.getString(kechengIndex));
			temp.setTeacher(notes.getString(teacherIndex));
			temp.setZhubanfang(notes.getString(zhubanfangIndex));
			temp.setShijian(notes.getString(shijianIndex));
			temp.setMiaoshu(notes.getString(miaoshuIndex));
			temp.setBiaozhi(notes.getInt(biaozhiIndex));
			Log.i(MainActivity.ACTIVITY_SERVICE,"116!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+temp.getId()+temp.getKecheng());
		}
		return temp;
	}
}
