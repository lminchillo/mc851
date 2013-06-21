package lais.mc851;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class _22_RouteSaved extends Activity
{
	private final static String TAG = "RouteSaved";
	
	private ListView routeListView = null;
	private ArrayList<String> routeList = null;
	private MyArrayAdapter adapter = null;
	
	private TextView textViewNoSavedRoutes;
	private Button buttonCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_saved);
		
		initialize();
	}
	
	private void initialize()
	{
		textViewNoSavedRoutes = (TextView) findViewById(R.id.take_bus_saved_route_text_view_no_saved_routes);
		
		buttonCancel = (Button) findViewById(R.id.take_bus_saved_route_button_cancelar);
		buttonCancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				onBackPressed();
			}
		});
		
		if (routeListView == null)
		{
			routeListView = (ListView) findViewById(R.id.take_bus_saved_route_listview);
		}
		
		routeList = RouteManager.getRouteList();
		if (routeList ==  null || routeList.size() == 0)
		{
			textViewNoSavedRoutes.setVisibility(View.VISIBLE);
		}
		else
		{
			textViewNoSavedRoutes.setVisibility(View.INVISIBLE);
			initializeListView();
		}
	}
	
	private void initializeListView()
	{
		if (adapter == null)
		{
			adapter = new MyArrayAdapter(getApplicationContext(), R.layout.listview_item_simple, routeList);
			routeListView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}
	
	class MyArrayAdapter extends ArrayAdapter<String>
	{
		LayoutInflater inflater;
		
		public MyArrayAdapter (Context ctx, int resourceId, ArrayList<String> objects)
		{
			super(ctx,resourceId,objects);
			inflater = LayoutInflater.from(getApplicationContext());
		}
		
		@Override
		public View getView (final int position, View convertView, ViewGroup parent) 
		{
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.listview_item_simple, null);
			}
			
			String route = routeList.get(position);
			route = route.substring(0, route.indexOf("\n"));
			
			TextView tv = (TextView) convertView.findViewById(R.id.listview_item_simple_text);
			tv.setText(" \n"+route+"\n ");
			
			convertView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{					
					String[] routeValue = routeList.get(position).split("\n");
					String routeName = routeValue[0];
					String addressSource = routeValue[1];
					String latLngSource = routeValue[2];
					String addressDest = routeValue[3];
					String latLngDest = routeValue[4];
					
					String initialize = "";
					if(routeValue.length > 5)
					{
						for(int i=5; i<routeValue.length; i++)
						{
							initialize += routeValue[i] + "\n";
						}
					}
					
					Log.d(TAG, "routeName: " + routeName);
					Log.d(TAG, "addressSource: " + addressSource);
					Log.d(TAG, "latLngSource: " + latLngSource);
					Log.d(TAG, "addressDest: " + addressDest);
					Log.d(TAG, "latLngDest: " + latLngDest);
					Log.d(TAG, "functionInitialize: " + initialize);
					
					// TODO: passar estes atributos para a Activity Route (?)
					Intent intent = new Intent(getApplicationContext(), _23_Route.class);
					intent.putExtra("route", routeList.get(position));
					startActivity(intent);
				}
			});
			
			return convertView;
		}
		
	}
}
