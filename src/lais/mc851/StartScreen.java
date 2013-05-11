package lais.mc851;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;

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
		arrayList.add(getResources().getString(R.string.startscreen_my_cupons));
		arrayList.add(getResources().getString(R.string.startscreen_config));
		
		ListView listView = (ListView) findViewById(R.id.startscreen_listview);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
		listView.setAdapter(arrayAdapter);
		
	}
}
