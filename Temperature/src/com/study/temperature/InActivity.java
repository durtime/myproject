package com.study.temperature;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;





public class InActivity extends Activity {
//��ַ
	private Button bt_start;
	private TextView tv_locat;
	
	private MyopenHelper helper;
	private EditText et_name;
	private EditText et_temon;
	private EditText et_temunder;
	private TextView tv_time;

	private AMapLocationClient mLocationClient = null;
	private AMapLocationListener mLocationListener = null;

	/**
     * ���ܰٶȵ�ͼ��λ�Ļص��࣬�����������ڰٶȵ�ͼ��һ����ͼ��λ�ļ����࣬���ڶ�λ����Ϣ�ķ��ء�
     */
    /**
     * �Զ���Ķ�λ�ص��ӿڼ�����,�ýӿ����Զ����һ���ӿڣ�������ʹ�øö���ʱ���Ѷ�λ��Ϣ���лص�;
     */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in);
		bt_start = (Button)findViewById(R.id.bt_start);
		tv_locat = (TextView)findViewById(R.id.tv_locat);
		bt_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bt_start.getText().equals("��ʼ��λ")) {
				bt_start.setText("stop");
				//����AMapLocationClient�����
				//��ʼ����λ
				mLocationClient = new AMapLocationClient(getApplicationContext());
				mLocationListener = new AMapLocationListener(){
					@Override
					public void onLocationChanged(AMapLocation location) {
					    if (location != null) {
					        if (location.getErrorCode() == 0) {
					        //������λ���

						        String province = location.getProvince();    //��ȡʡ��
						        String city = location.getCity();    //��ȡ����
						        String district = location.getDistrict();    //��ȡ����
						        String street = location.getStreet();    //��ȡ�ֵ���Ϣ
						        StringBuilder myPosition=new StringBuilder();
						        myPosition.append("γ�ȣ�").append(location.getLatitude()).append("\n");
						        myPosition.append("���ȣ�").append(location.getLongitude()).append("\n");
						        myPosition.append("ʡ�ݣ�").append(province).append("\n");
						        myPosition.append("���У�").append(city).append("\n");
						        myPosition.append("����").append(district).append("\n");
						        myPosition.append("�ֵ���").append(street).append("\n");
						        myPosition.append("���ƺţ�").append(location.getStreetNum());//�ֵ����ƺ���Ϣ
						        tv_locat.setText(myPosition);
						    }
						}
					}
				};				
				//���ö�λ�ص�����
				mLocationClient.setLocationListener(mLocationListener);

				//�첽��ȡ��λ���

				initLocation();

				//������λ
				mLocationClient.startLocation();
				}else if(bt_start.getText().equals("stop")) {
					bt_start.setText("��ʼ��λ");
					close();

				}
			}
		});



//------------------------
		et_name = (EditText)findViewById(R.id.et_name);
		et_temon = (EditText)findViewById(R.id.et_temon);
		et_temunder = (EditText)findViewById(R.id.et_temunder);
		
		Calendar calendar = Calendar.getInstance();
		//��
		int year = calendar.get(Calendar.YEAR);
		//��
		int month = calendar.get(Calendar.MONTH)+1;
		//��
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		//��ȡϵͳʱ��
		//Сʱ
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		//����
		int minute = calendar.get(Calendar.MINUTE);
		//��
		int second = calendar.get(Calendar.SECOND);
		 
		tv_time = findViewById(R.id.tv_time);
		tv_time.setText(year+"��"+month+"��"+day+"��"+hour+":"+minute+":"+second);
		
		helper = new MyopenHelper(this,1);
		Button bt_tijiao = (Button)findViewById(R.id.bt_tijiao);
		bt_tijiao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�����ݿ�
				SQLiteDatabase readableDatabase = helper.getReadableDatabase();
				//��ȡedittext����
				String text = et_name.getText().toString().trim();
				String text2 = et_temon.getText().toString().trim();
				String text3 = et_temunder.getText().toString().trim();
				String text4 = tv_time.getText().toString().trim();
				String text5 = tv_locat.getText().toString().trim();
				if (TextUtils.isEmpty(text)||TextUtils.isEmpty(text2)||TextUtils.isEmpty(text3)) {
					Toast.makeText(InActivity.this, "���벻��Ϊ��", Toast.LENGTH_SHORT).show();
				}else {

					String sql = "insert into info (name,temon,temunder,time,locat) values('"+text+"','"+text2+"','"+text3+"','"+text4+"','"+text5+"')";
					readableDatabase.execSQL(sql);
					readableDatabase.close();
					Toast.makeText(InActivity.this, "�ύ�ɹ���", Toast.LENGTH_SHORT).show();
	                Intent intent =new Intent(InActivity.this,MainActivity.class);
	                finish();//������ǰ�
	                startActivity(intent);
				}
			}
		});
	}
	

	private void initLocation() {
		AMapLocationClientOption option = new AMapLocationClientOption();
        option.setGpsFirst(true);            // ��gps;
		//��ѡ���Ƿ���Ҫ��ַ��Ϣ��Ĭ��Ϊ����Ҫ��������Ϊfalse
      //���ö�λģʽΪAMapLocationMode.Hight_Accuracy���߾���ģʽ��
        option.setLocationMode(AMapLocationMode.Hight_Accuracy);		//��ѡ�������Ƿ���Ҫ���°汾�ĵ�ַ��Ϣ��Ĭ����Ҫ��������Ϊtrue
		mLocationClient.setLocationOption(option);
		//mLocationClientΪ�ڶ�����ʼ������LocationClient����
		//�轫���úõ�LocationClientOption����ͨ��setLocOption�������ݸ�LocationClient����ʹ��
		//����LocationClientOption�����ã��������ο���LocationClientOption�����ϸ˵��
	}
	private void close() {
		mLocationClient.stopLocation();
		mLocationClient.unRegisterLocationListener(mLocationListener);
		mLocationClient.onDestroy();
	}

}

