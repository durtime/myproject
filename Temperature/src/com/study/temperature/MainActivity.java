package com.study.temperature;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bt_in = (Button)findViewById(R.id.bt_in);
         //数据填写跳转
         //绑定监听事件
		bt_in.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 
                 //点击跳转
                 Intent intent =new Intent(MainActivity.this,InActivity.class);
                 //启动填写页面
                 startActivity(intent);
             }
        });
		
//数据查询------------------------------------------------------------------------------------------
		Button bt_out = (Button)findViewById(R.id.bt_out);
		bt_out.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                //点击跳转
                Intent intent =new Intent(MainActivity.this,OutActivity.class);
                //启动填写页面
                startActivity(intent);
			}

		});
	}

}
