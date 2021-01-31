package com.study.temperature;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OutActivity extends Activity {

	private ListView lv_list;
	private ArrayList<Person> persons = new ArrayList<Person>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_out);
		
		lv_list = (ListView)findViewById(R.id.lv_list);
		MyopenHelper helper = new MyopenHelper(this,1);
		SQLiteDatabase readableDatabase = helper.getReadableDatabase();
		String sql = "select * from info";
		Cursor curor = readableDatabase.rawQuery(sql , null);
        //遍历查询到的结果
		int count=0;
		while(curor.moveToNext()){
            //创建person对象
			Person person = new Person();
            //根据列名 name 获取数据库中对应的值
			String name = curor.getString(curor.getColumnIndex("name"));
            //根据列名 phone 获取数据库中对应的值
			String temon = curor.getString(curor.getColumnIndex("temon"));
			String temunder = curor.getString(curor.getColumnIndex("temunder"));
			String time = curor.getString(4);
			String locat = curor.getString(5);
            //设置相应数据
			person.setName(name);
			person.setTemon(temon);
			person.setTemunder(temunder);
			person.setTime(time);
			person.setLocat(locat);
            //把数据添加到集合
			persons.add(person);
			count++;
		}
		//list显示
		//把数据适配器和listview绑定
		lv_list.setAdapter(new MyAdapter());
        //关闭游标
		curor.close();
		helper.close();
		if(count!=0) {
			Toast.makeText(this, "查询成功", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT).show();
		}
		lv_list.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转
                Intent intent =new Intent(OutActivity.this,SmallitemActivity.class);
                //启动填写页面
                Person tem =persons.get(position);
                intent.putExtra("name",tem.getName());
                intent.putExtra("info", tem.getTime()+"\n"+tem.getLocat());
                startActivity(intent);
            }
		});
	}
    private class MyAdapter extends BaseAdapter{
   	 
    	//返回要展示的数据集合的条目数
		@Override
		public int getCount() {
			return persons.size();
		}
		//通过adapter传进来的posion(listview中条目的position)
		//把它对应的数据集合中的对象返回来
		@Override
		public Object getItem(int position) {
			return persons.get(position);
		}
 
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//①把布局文件转换为View对象 需要判断convertView是否为空
			View view= null;
			if(convertView == null){
				//创建view对象
				 view = View.inflate(OutActivity.this, R.layout.item, null);
			}else{
				//复用旧的convertView
				view = convertView;
			}
			//②找到要修改的控件的对象  注意要调用view.findViewById
			TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
			TextView tv_tem = (TextView)view.findViewById(R.id.tv_tem);
			
			//③ 设置数据
			//3.1通过position 到数据集合中把要显示的数据找到
			Person person = persons.get(position);
			//3.2把要显示的内容展示到对应的View对象上
			tv_name.setText(person.getName());
			tv_tem.setText("上午："+person.getTemon()+"下午："+person.getTemunder());
			return view;
		}
    }
}
