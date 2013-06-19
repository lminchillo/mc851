package lais.mc851;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class _3_Routes extends Activity
{
	private final static String TAG = "Routes";
	
	private Button buttonNewRoute;
	private ListView routeListView = null;
	private ArrayList<String> routeList = null;
	private MyArrayAdapter adapter = null;
	
	@Override
	protected void onResume()
	{
		initializeListView();
		super.onResume();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes);
		
		initialize();
		initializeButtons();
	}
	
	private void initialize()
	{
		if (routeListView == null)
		{
			routeListView = (ListView) findViewById(R.id.routes_listview);
		}
		
		TextView noRoutes = (TextView) findViewById(R.id.routes_text_view_no_saved_routes);
		routeList = RouteManager.getRouteList();
		if (routeList ==  null || routeList.size() == 0)
		{
			noRoutes.setVisibility(View.VISIBLE);
		}
		else
		{
			noRoutes.setVisibility(View.INVISIBLE);
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
	
	private void initializeButtons()
	{
		buttonNewRoute = (Button) findViewById(R.id.routes_button_new_route);
		buttonNewRoute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Log.d(TAG, "Clicked on New Route button");
				Intent intent = new Intent(getApplicationContext(), _31_RouteEdit.class);
				intent.putExtra("routeName", "");
				intent.putExtra("routeSourceValue", "");
				intent.putExtra("routeDestValue", "");
				startActivity(intent);
				finish();
			}
		});
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
			convertView = inflater.inflate(R.layout.listview_item_simple, null);
			
			String route = routeList.get(position);
			route = route.substring(0, route.indexOf("\n", route.indexOf("\n") + 1));
			
			TextView tv = (TextView) convertView.findViewById(R.id.listview_item_simple_text);
			tv.setText(" \n"+route+"\n ");
			
			convertView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(getApplicationContext(), _31_RouteEdit.class);
					String aux = routeList.get(position), routeName;
					StringTokenizer st = new StringTokenizer(aux, "\n");

					routeName = st.nextToken();
					intent.putExtra("routeName", routeName);
					intent.putExtra("routeSourceValue", routeName+"\n"+st.nextToken()+"\n"+st.nextToken());
					intent.putExtra("routeDestValue", routeName+"\n"+st.nextToken()+"\n"+st.nextToken());
					startActivity(intent);
					finish();
				}
			});
			
			return convertView;
		}
		
	}
}
