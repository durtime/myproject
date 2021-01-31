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
        //������ѯ���Ľ��
		int count=0;
		while(curor.moveToNext()){
            //����person����
			Person person = new Person();
            //�������� name ��ȡ���ݿ��ж�Ӧ��ֵ
			String name = curor.getString(curor.getColumnIndex("name"));
            //�������� phone ��ȡ���ݿ��ж�Ӧ��ֵ
			String temon = curor.getString(curor.getColumnIndex("temon"));
			String temunder = curor.getString(curor.getColumnIndex("temunder"));
			String time = curor.getString(4);
			String locat = curor.getString(5);
            //������Ӧ����
			person.setName(name);
			person.setTemon(temon);
			person.setTemunder(temunder);
			person.setTime(time);
			person.setLocat(locat);
            //��������ӵ�����
			persons.add(person);
			count++;
		}
		//list��ʾ
		//��������������listview��
		lv_list.setAdapter(new MyAdapter());
        //�ر��α�
		curor.close();
		helper.close();
		if(count!=0) {
			Toast.makeText(this, "��ѯ�ɹ�", Toast.LENGTH_SHORT).show();
		}else {
			Toast.makeText(this, "��ѯʧ��", Toast.LENGTH_SHORT).show();
		}
		lv_list.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //�����ת
                Intent intent =new Intent(OutActivity.this,SmallitemActivity.class);
                //������дҳ��
                Person tem =persons.get(position);
                intent.putExtra("name",tem.getName());
                intent.putExtra("info", tem.getTime()+"\n"+tem.getLocat());
                startActivity(intent);
            }
		});
	}
    private class MyAdapter extends BaseAdapter{
   	 
    	//����Ҫչʾ�����ݼ��ϵ���Ŀ��
		@Override
		public int getCount() {
			return persons.size();
		}
		//ͨ��adapter��������posion(listview����Ŀ��position)
		//������Ӧ�����ݼ����еĶ��󷵻���
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
			//�ٰѲ����ļ�ת��ΪView���� ��Ҫ�ж�convertView�Ƿ�Ϊ��
			View view= null;
			if(convertView == null){
				//����view����
				 view = View.inflate(OutActivity.this, R.layout.item, null);
			}else{
				//���þɵ�convertView
				view = convertView;
			}
			//���ҵ�Ҫ�޸ĵĿؼ��Ķ���  ע��Ҫ����view.findViewById
			TextView tv_name = (TextView)view.findViewById(R.id.tv_name);
			TextView tv_tem = (TextView)view.findViewById(R.id.tv_tem);
			
			//�� ��������
			//3.1ͨ��position �����ݼ����а�Ҫ��ʾ�������ҵ�
			Person person = persons.get(position);
			//3.2��Ҫ��ʾ������չʾ����Ӧ��View������
			tv_name.setText(person.getName());
			tv_tem.setText("���磺"+person.getTemon()+"���磺"+person.getTemunder());
			return view;
		}
    }
}
