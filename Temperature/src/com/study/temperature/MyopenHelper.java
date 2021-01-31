package com.study.temperature;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyopenHelper extends SQLiteOpenHelper {
//构造重新
	public MyopenHelper(Context context, int version) {
		super(context, "temper1.db", null, version);//null采用默认游标工厂
	}
//创建数据库
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table info (_id integer primary key autoincrement, name varchar(20),temon varchar(20),temunder varchar(20),time varchar(45),locat varchar(50))");
	}
//版本升级或降级进行数据库的更新
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table info add date vercher(45)");
	}

}
