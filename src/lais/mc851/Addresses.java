package lais.mc851;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Addresses extends Activity
{
	private ListView addressListView = null;
	private ArrayList<String> addressList = null;
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
		setContentView(R.layout.activity_addresses);
		
		initialize();
		initializeButtons();
	}
	
	private void initialize()
	{
		if (addressListView == null)
		{
			addressListView = (ListView) findViewById(R.id.addresses_listview);
		}
		
		TextView noAddresses = (TextView) findViewById(R.id.addresses_no_saved_address);
		addressList = AddressManager.getAddressList();
		if (addressList ==  null || addressList.size()==0)
		{
			noAddresses.setVisibility(View.VISIBLE);
		}
		else
		{
			noAddresses.setVisibility(View.INVISIBLE);
			initializeListView();
		}
	}
	
	private void initializeListView()
	{
		if (adapter == null)
		{
			adapter = new MyArrayAdapter(getApplicationContext(), R.layout.listview_item_simple, addressList);
			addressListView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}
	
	private void initializeButtons()
	{
		Button addAddress = (Button) findViewById(R.id.addresses_button);
		addAddress.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(),AddressEdit.class);
				//intent.putExtra("addressName","");
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
			super (ctx,resourceId,objects);
			inflater = LayoutInflater.from(getApplicationContext());
		}
		
		@Override
		public View getView (int position, View convertView, ViewGroup parent) 
		{
			convertView = inflater.inflate(R.layout.listview_item_simple, null);
			
			String address = addressList.get(position);
			address = address.substring(0, address.indexOf("\n", address.indexOf("\n")+1));
			
			TextView tv = (TextView) convertView.findViewById(R.id.listview_item_simple_text);
			tv.setText(" \n"+address+"\n ");
			
			return convertView;
		}
		
	}
	
}
