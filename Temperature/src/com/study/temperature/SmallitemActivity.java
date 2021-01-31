package com.study.temperature;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SmallitemActivity extends Activity {

	private TextView tv_myname;
	private TextView tv_myinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smallitem);
		tv_myname = (TextView)findViewById(R.id.tv_myname);
		tv_myinfo = (TextView)findViewById(R.id.tv_myinfo);
        //接收参数
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String info = intent.getStringExtra("info");
		tv_myname.setText(name);
		tv_myinfo.setText(info);
	}
}
