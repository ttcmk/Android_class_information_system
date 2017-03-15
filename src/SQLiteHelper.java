package com.example.popupmenutest;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper{
	public ClassInfoModel classInfo;

	public static final String CLASS_ID = "id";
	public static final String CLASS_TITLE = "title";
	public static final String CLASS_PROPERTY = "property";
	public static final String CLASS_TIME = "time";	
	public static final String CLASS_TEACHER = "teacher";
	public static final String CLASS_PLACE = "place";
	public static final String CLASS_CELL_ID = "cell_id";
	public static final String CLASS_SDWEEK = "sdweek";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME = "dbclass12";
	private static final String DATABASE_TABLE = "classinfo";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "create table classinfo (_id integer primary key autoincrement, "
			+ "id text not null,title text, property text, time text,"
			+ "teacher text,place text,cell_id text not null,sdweek text not null);";

	private final Context mCtx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS classinfo");
			onCreate(db);
		}
	}
	
	public SQLiteHelper(Context ctx) {
		this.mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);

		mDb = mDbHelper.getWritableDatabase();
	}

	public void close() {
		mDbHelper.close();
	}

	public boolean queryboolean(String id) {
		Cursor cursor = mDb.query(DATABASE_TABLE, new String[] { CLASS_ID,
				CLASS_TITLE, CLASS_PROPERTY, CLASS_TIME, CLASS_TEACHER, CLASS_PLACE,
				CLASS_CELL_ID, CLASS_SDWEEK }, CLASS_ID + "=" + id, null, null, null,
				null, null);
		if (cursor.getCount() == 0)
			return false;
		else
			return true;
	}
	
	public boolean create(ClassInfoModel infoModel) {
		if (queryboolean(infoModel.getId()))
			return false;
		ContentValues values = new ContentValues();
		values.put(CLASS_ID, infoModel.getId());
		values.put(CLASS_TITLE, infoModel.getTitle());
		values.put(CLASS_PROPERTY, infoModel.getProperty());
		values.put(CLASS_TIME, infoModel.getTime());
		values.put(CLASS_TEACHER, infoModel.getTeacher());
		values.put(CLASS_PLACE, infoModel.getPlace());
		values.put(CLASS_CELL_ID, infoModel.getCell_id());
		values.put(CLASS_SDWEEK, infoModel.getSdweek());

		if (mDb.insert(DATABASE_TABLE, null, values) < 0)
			return false;
		else
			return true;
	}
	
	public boolean deleteById(String id) {
		return mDb.delete(DATABASE_TABLE, CLASS_ID + "=" + id, null) > 0;
	}
	
	public Cursor getAllNotes() {
		return mDb.query(DATABASE_TABLE, new String[] { CLASS_ID, CLASS_TITLE,
				CLASS_PROPERTY, CLASS_TIME, CLASS_TEACHER, CLASS_PLACE, CLASS_CELL_ID,
				CLASS_SDWEEK }, null, null, null, null, null);
	}
	
	public ArrayList<ClassInfoModel> getAllClassModels() {
		Cursor notes = mDb.query(DATABASE_TABLE, new String[] { CLASS_ID,
				CLASS_TITLE, CLASS_PROPERTY, CLASS_TIME, CLASS_TEACHER, CLASS_PLACE,
				CLASS_CELL_ID, CLASS_SDWEEK }, null, null, null, null, null);
		ArrayList<ClassInfoModel> classes = new ArrayList<ClassInfoModel>();
		int c = 0;
		if (notes != null) {

			int idIndex = notes.getColumnIndex("id");

			if (notes.moveToFirst()) {
				do {
					String id = notes.getString(idIndex);
					classes.add(getClassById(id));
				} while (notes.moveToNext());
			}
		}

		return classes;
	}
	
	public boolean deleteAll() {
		return mDb.delete(DATABASE_TABLE, CLASS_ID + "< 99999", null) > 0;
	}

	public Cursor query(String id) {
		Cursor cursor = mDb.query(DATABASE_TABLE, new String[] { CLASS_ID,
				CLASS_TITLE, CLASS_PROPERTY, CLASS_TIME, CLASS_TEACHER, CLASS_PLACE,
				CLASS_CELL_ID, CLASS_SDWEEK }, CLASS_ID + "=" + id, null, null, null,
				null, null);
		return cursor;
	}
	
	public boolean update(ClassInfoModel infoModel) {
		if (!queryboolean(infoModel.getId()))
			return false;
		ContentValues values = new ContentValues();
		values.put(CLASS_ID, infoModel.getId());
		values.put(CLASS_TITLE, infoModel.getTitle());
		values.put(CLASS_PROPERTY, infoModel.getProperty());
		values.put(CLASS_TIME, infoModel.getTime());
		values.put(CLASS_TEACHER, infoModel.getTeacher());
		values.put(CLASS_PLACE, infoModel.getPlace());
		values.put(CLASS_CELL_ID, infoModel.getCell_id());
		values.put(CLASS_SDWEEK, infoModel.getSdweek());
		if (mDb.update(DATABASE_TABLE, values,
				CLASS_ID + "=" + infoModel.getId(), null) < 0)
			return false;
		else
			return true;
	}

	public ClassInfoModel getClassById(String id) {
		ClassInfoModel temp = new ClassInfoModel();
		Cursor notes = query(id);
		if (notes.getCount() > 0) {
			notes.moveToFirst();
			int idIndex = notes.getColumnIndex("id");
			int titleIndex = notes.getColumnIndex("title");
			int propertyIndex = notes.getColumnIndex("property");
			int timeIndex = notes.getColumnIndex("time");
			int teacherIndex = notes.getColumnIndex("teacher");
			int placeIndex = notes.getColumnIndex("place");
			int cell_idIndex = notes.getColumnIndex("cell_id");
			int sdweekIndex = notes.getColumnIndex("sdweek");

			temp.setId(notes.getString(idIndex));
			temp.setTitle(notes.getString(titleIndex));
			temp.setProperty(notes.getString(propertyIndex));
			temp.setTime(notes.getString(timeIndex));
			temp.setTeacher(notes.getString(teacherIndex));
			temp.setPlace(notes.getString(placeIndex));
			temp.setCell_id(notes.getString(cell_idIndex));
			temp.setSdweek(notes.getString(sdweekIndex));
		}
		return temp;
	}
}
