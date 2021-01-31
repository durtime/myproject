package com.study.temperature;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyopenHelper extends SQLiteOpenHelper {
//��������
	public MyopenHelper(Context context, int version) {
		super(context, "temper1.db", null, version);//null����Ĭ���α깤��
	}
//�������ݿ�
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table info (_id integer primary key autoincrement, name varchar(20),temon varchar(20),temunder varchar(20),time varchar(45),locat varchar(50))");
	}
//�汾�����򽵼��������ݿ�ĸ���
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table info add date vercher(45)");
	}

}
