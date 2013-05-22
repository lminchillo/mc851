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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Addresses extends Activity
{
	ArrayList<String> arrayList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addresses);
		
		arrayList = AddressManager.getAddressList();
		if (arrayList ==  null || arrayList.size()==0)
		{
			TextView tv = (TextView) findViewById(R.id.addresses_no_saved_address);
			tv.setVisibility(View.VISIBLE);
		}
		else
		{
			//TODO
			Toast.makeText(getApplicationContext(), "Initialize listview" , Toast.LENGTH_SHORT).show();
		}
		
		Button addAddress = (Button) findViewById(R.id.addresses_button);
		addAddress.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getApplicationContext(),AddressEdit.class);
				intent.putExtra("addressName","");
				startActivity(intent);
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
