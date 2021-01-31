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
//地址
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
     * 接受百度地图定位的回调类，该类是派生于百度地图的一个地图定位的监听类，用于定位后信息的返回。
     */
    /**
     * 自定义的定位回调接口监听器,该接口是自定义的一个接口，用于在使用该对象时，把定位信息进行回调;
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
				if(bt_start.getText().equals("开始定位")) {
				bt_start.setText("stop");
				//声明AMapLocationClient类对象
				//初始化定位
				mLocationClient = new AMapLocationClient(getApplicationContext());
				mLocationListener = new AMapLocationListener(){
					@Override
					public void onLocationChanged(AMapLocation location) {
					    if (location != null) {
					        if (location.getErrorCode() == 0) {
					        //解析定位结果

						        String province = location.getProvince();    //获取省份
						        String city = location.getCity();    //获取城市
						        String district = location.getDistrict();    //获取区县
						        String street = location.getStreet();    //获取街道信息
						        StringBuilder myPosition=new StringBuilder();
						        myPosition.append("纬度：").append(location.getLatitude()).append("\n");
						        myPosition.append("经度：").append(location.getLongitude()).append("\n");
						        myPosition.append("省份：").append(province).append("\n");
						        myPosition.append("城市：").append(city).append("\n");
						        myPosition.append("区：").append(district).append("\n");
						        myPosition.append("街道：").append(street).append("\n");
						        myPosition.append("门牌号：").append(location.getStreetNum());//街道门牌号信息
						        tv_locat.setText(myPosition);
						    }
						}
					}
				};				
				//设置定位回调监听
				mLocationClient.setLocationListener(mLocationListener);

				//异步获取定位结果

				initLocation();

				//启动定位
				mLocationClient.startLocation();
				}else if(bt_start.getText().equals("stop")) {
					bt_start.setText("开始定位");
					close();

				}
			}
		});



//------------------------
		et_name = (EditText)findViewById(R.id.et_name);
		et_temon = (EditText)findViewById(R.id.et_temon);
		et_temunder = (EditText)findViewById(R.id.et_temunder);
		
		Calendar calendar = Calendar.getInstance();
		//年
		int year = calendar.get(Calendar.YEAR);
		//月
		int month = calendar.get(Calendar.MONTH)+1;
		//日
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		//获取系统时间
		//小时
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		//分钟
		int minute = calendar.get(Calendar.MINUTE);
		//秒
		int second = calendar.get(Calendar.SECOND);
		 
		tv_time = findViewById(R.id.tv_time);
		tv_time.setText(year+"年"+month+"月"+day+"日"+hour+":"+minute+":"+second);
		
		helper = new MyopenHelper(this,1);
		Button bt_tijiao = (Button)findViewById(R.id.bt_tijiao);
		bt_tijiao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//打开数据库
				SQLiteDatabase readableDatabase = helper.getReadableDatabase();
				//获取edittext数据
				String text = et_name.getText().toString().trim();
				String text2 = et_temon.getText().toString().trim();
				String text3 = et_temunder.getText().toString().trim();
				String text4 = tv_time.getText().toString().trim();
				String text5 = tv_locat.getText().toString().trim();
				if (TextUtils.isEmpty(text)||TextUtils.isEmpty(text2)||TextUtils.isEmpty(text3)) {
					Toast.makeText(InActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
				}else {

					String sql = "insert into info (name,temon,temunder,time,locat) values('"+text+"','"+text2+"','"+text3+"','"+text4+"','"+text5+"')";
					readableDatabase.execSQL(sql);
					readableDatabase.close();
					Toast.makeText(InActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
	                Intent intent =new Intent(InActivity.this,MainActivity.class);
	                finish();//结束当前活动
	                startActivity(intent);
				}
			}
		});
	}
	

	private void initLocation() {
		AMapLocationClientOption option = new AMapLocationClientOption();
        option.setGpsFirst(true);            // 打开gps;
		//可选，是否需要地址信息，默认为不需要，即参数为false
      //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        option.setLocationMode(AMapLocationMode.Hight_Accuracy);		//可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
		mLocationClient.setLocationOption(option);
		//mLocationClient为第二步初始化过的LocationClient对象
		//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
	}
	private void close() {
		mLocationClient.stopLocation();
		mLocationClient.unRegisterLocationListener(mLocationListener);
		mLocationClient.onDestroy();
	}

}

