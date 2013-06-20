package lais.mc851;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class _5_Coupons extends Activity
{
	private ListView couponListView = null;
	private ArrayList<String> couponList = null;
	private MyArrayAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupons);

		initialize();
	}

	@Override
	protected void onResume()
	{
		initializeListView();
		super.onResume();
	}

	private void initialize()
	{
		couponListView = (ListView) findViewById(R.id.coupons_listview);

		TextView noCoupons = (TextView) findViewById(R.id.coupons_text_view_no_coupons);

		ArrayList<String> coupons = CouponManager.getCouponList();
		couponList = new ArrayList<String>();
		for(String s : coupons)
		{
			couponList.add(s);
		}
		
		if (couponList == null || couponList.size() == 0)
		{
			noCoupons.setVisibility(View.VISIBLE);
		}
		else
		{
			noCoupons.setVisibility(View.INVISIBLE);
			initializeListView();
		}
	}

	private void initializeListView()
	{
		if (adapter == null)
		{
			adapter = new MyArrayAdapter(getApplicationContext(),
					R.layout.listview_item_simple, couponList);
			couponListView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	private class MyArrayAdapter extends ArrayAdapter<String>
	{
		LayoutInflater inflater;

		public MyArrayAdapter(Context ctx, int resourceId, ArrayList<String> objects)
		{
			super(ctx, resourceId, objects);
			inflater = LayoutInflater.from(getApplicationContext());
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.listview_item_simple, null);
			}
			
			String coupon[] = couponList.get(position).split("\n");
			final String couponName = coupon[0];
			final String couponDescription = coupon[1];
			
			TextView tv = (TextView) convertView.findViewById(R.id.listview_item_simple_text);
			if (couponDescription.length()>50)
			{
				tv.setText(" \n" + couponName + "\n" + couponDescription.substring(0, 47) + "..." + "\n ");
			}
			else
			{
				tv.setText(" \n" + couponName + "\n" + couponDescription + "\n ");
			}
			
			convertView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(getApplicationContext(), _51_CouponView.class);
					intent.putExtra("couponName", couponName);
					intent.putExtra("couponDescription", couponDescription);
					startActivity(intent);
					finish();
				}
			});

			return convertView;
		}

	}
}
