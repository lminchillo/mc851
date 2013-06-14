package lais.mc851;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class StartScreen extends Activity
{
	ArrayList<String> arrayList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
		
		arrayList = new ArrayList<String>();
		arrayList.add(getResources().getString(R.string.startscreen_takeBus));
		arrayList.add(getResources().getString(R.string.startscreen_savedRoutes));
		arrayList.add(getResources().getString(R.string.startscreen_savedAddresses));
		arrayList.add(getResources().getString(R.string.startscreen_myCoupons));
		arrayList.add(getResources().getString(R.string.startscreen_config));
		
		ListView listView = (ListView) findViewById(R.id.startscreen_listview);
		MyArrayAdapter mAdapter = new MyArrayAdapter(getApplicationContext(), 0, arrayList);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				position++;
				if (position==1) startActivity(new Intent(getApplicationContext(),Route.class));
				//if (position==2) startActivity(new Intent(getApplicationContext(),Routes.class));
				else if (position==3) startActivity(new Intent(getApplicationContext(),Addresses.class));
				else if (position==4) startActivity(new Intent(getApplicationContext(),Coupons.class));
				else if (position==5) startActivity(new Intent(getApplicationContext(),Settings.class));
			}
		});
	}
	
	class MyArrayAdapter extends ArrayAdapter<String>
	{
		LayoutInflater inflater;
		
		public MyArrayAdapter (Context ctx, int resourceId, ArrayList<String> objects)
		{
			super (ctx,resourceId,objects);
			inflater = LayoutInflater.from(getApplicationContext());
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public View getView (int position, View convertView, ViewGroup parent) 
		{
			convertView = inflater.inflate(R.layout.listview_item_simple, null);
			TextView tv = (TextView) convertView.findViewById(R.id.listview_item_simple_text);
			tv.setText(" \n\t"+arrayList.get(position)+"\n ");
			ImageView img = (ImageView) convertView.findViewById(R.id.listview_item_simple_img);
			if (position==0)
			{
				img.getLayoutParams().height = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.40);
			}
			else
			{
				img.getLayoutParams().height = (int) (getWindowManager().getDefaultDisplay().getHeight() * 0.14);
			}
			
			return convertView;
		}
		
	}
	
}
