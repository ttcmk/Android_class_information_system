package com.example.popupmenutest;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLWeeknumber {
	
	public WeeknumberModel weeknum;
	
	public static final String ID = "id";
	public static final String OTIME = "otime";
	public static final String WEEKNUMBER = "weeknumber";
		
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME = "dbweek";
	private static final String DATABASE_TABLE = "week";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "create table week (_id integer primary key autoincrement, "
			+ "id text not null,otime date not null,weeknumber text not null );";

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
			db.execSQL("DROP TABLE IF EXISTS week");
			onCreate(db);
		}
	}
	
	public SQLWeeknumber(Context ctx) {
		this.mCtx = ctx;
		mDbHelper = new DatabaseHelper(mCtx);

		mDb = mDbHelper.getWritableDatabase();
		// mDbHelper.onUpgrade(mDb, 1, 1);
	}

	public void close() {
		mDbHelper.close();
	}
	
	//插入周数信息
	public boolean create(WeeknumberModel weekModel) {
		if (queryboolean(weekModel.getId()))
			return update(weekModel);
		ContentValues values = new ContentValues();
		values.put(ID, weekModel.getId());
		values.put(OTIME, weekModel.getOtime());
		values.put(WEEKNUMBER, weekModel.getWeeknumber());

		if (mDb.insert(DATABASE_TABLE, null, values) < 0)
			return false;
		else
			return true;
	}

	//更新周数信息
	public boolean update(WeeknumberModel weekModel) {
		ContentValues values = new ContentValues();
		values.put(ID, weekModel.getId());
		if (weekModel.getOtime() !="")
			values.put(OTIME, weekModel.getOtime());
		if (weekModel.getWeeknumber() !="")
			values.put(WEEKNUMBER, weekModel.getWeeknumber());
		if (mDb.update(DATABASE_TABLE, values, ID + "=" + weekModel.getId(),
				null) < 0)
			return false;
		else
			return true;
	}

	public boolean deleteById(String id) {
		return mDb.delete(DATABASE_TABLE, ID + "=" + id, null) > 0;
	}

	public Cursor getAllNotes() {
		return mDb.query(DATABASE_TABLE, new String[] { ID, OTIME, WEEKNUMBER }, null,
				null, null, null, null);
	}

	public boolean deleteAll() {
		return mDb.delete(DATABASE_TABLE, ID + "< 99999", null) > 0;
	}

	public Cursor query(String id) {
		Cursor cursor = mDb.query(DATABASE_TABLE,
				new String[] { ID, OTIME, WEEKNUMBER }, ID + "=" + id, null, null,
				null, null, null);
		return cursor;
	}

	//查询周数信息是否存在
	public boolean queryboolean(String id) {
		Cursor cursor = mDb.query(DATABASE_TABLE,
				new String[] { ID, OTIME, WEEKNUMBER }, ID + "=" + id, null, null,
				null, null, null);
		if (cursor.getCount() == 0)
			return false;
		else
			return true;
	}
	
	//根据ID获取相应的周数信息
	public WeeknumberModel getWeekById(String id) {
		WeeknumberModel temp = new WeeknumberModel();
		Cursor notes = query(id);
		if (notes.getCount() > 0) {
			notes.moveToFirst();
			int idIndex = notes.getColumnIndex("id");
			int otimeIndex = notes.getColumnIndex("otime");
			int weeknumberIndex = notes.getColumnIndex("weeknumber");

			temp.setId(notes.getString(idIndex));
			temp.setOtime(notes.getString(otimeIndex));
			temp.setWeeknumber(notes.getString(weeknumberIndex));
		}
		return temp;
	}
}
