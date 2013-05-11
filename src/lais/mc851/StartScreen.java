package lais.mc851;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class StartScreen extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
		
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(getResources().getString(R.string.startscreen_take_bus));
		arrayList.add(getResources().getString(R.string.startscreen_saved_routes));
		arrayList.add(getResources().getString(R.string.startscreen_saved_adresses));
		arrayList.add(getResources().getString(R.string.startscreen_my_coupons));
		arrayList.add(getResources().getString(R.string.startscreen_config));
		
		ListView listView = (ListView) findViewById(R.id.startscreen_listview);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
		listView.setAdapter(arrayAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				position++;
				//if (position==1) startActivity(new Intent(getApplicationContext(),GetRoute.class));
				//else if (position==2) startActivity(new Intent(getApplicationContext(),Routes.class));
				//else if (position==3) startActivity(new Intent(getApplicationContext(),Adresses.class));
				//else if (position==4) startActivity(new Intent(getApplicationContext(),Coupons.class));
				if (position==5) startActivity(new Intent(getApplicationContext(),Settings.class));
			}
		});
		
	}
}
