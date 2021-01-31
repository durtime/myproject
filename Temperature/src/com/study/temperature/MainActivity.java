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
         //������д��ת
         //�󶨼����¼�
		bt_in.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 
                 //�����ת
                 Intent intent =new Intent(MainActivity.this,InActivity.class);
                 //������дҳ��
                 startActivity(intent);
             }
        });
		
//���ݲ�ѯ------------------------------------------------------------------------------------------
		Button bt_out = (Button)findViewById(R.id.bt_out);
		bt_out.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                //�����ת
                Intent intent =new Intent(MainActivity.this,OutActivity.class);
                //������дҳ��
                startActivity(intent);
			}

		});
	}

}
