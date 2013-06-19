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

public class _5_Coupons extends Activity {
	private ListView couponListView = null;
	private ArrayList<String> couponList = null;
	private MyArrayAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupons);

		initialize();
	}

	@Override
	protected void onResume() {
		initializeListView();
		super.onResume();
	}

	private void initialize() {
		if (couponListView == null) {
			couponListView = (ListView) findViewById(R.id.coupons_listview);
		}

		TextView noCoupons = (TextView) findViewById(R.id.coupons_text_view_no_coupons);

		// TODO: descomentar isso para adicionar cupons hardcoded
//		CouponManager.addCoupon("Coxinha", "Coxinha|Desconto de 15% na compra de Coxinha!");
//		CouponManager.addCoupon("Espinafre", "Espinafre|Desconto de 90% na compra de Espinafre, mas não vale para os dias primos do mês, nem para os dias em que o presidente Barack Obama aparece na televisão do México");
		ArrayList<String> coupons = CouponManager.getCouponList();
		couponList = new ArrayList<String>();
		for(String s : coupons)
		{
			couponList.add(s);
		}
		
		if (couponList == null || couponList.size() == 0) {
			noCoupons.setVisibility(View.VISIBLE);
		} else {
			noCoupons.setVisibility(View.INVISIBLE);
			initializeListView();
		}
	}

	private void initializeListView() {
		if (adapter == null) {
			adapter = new MyArrayAdapter(getApplicationContext(),
					R.layout.listview_item_simple, couponList);
			couponListView.setAdapter(adapter);
		}
		adapter.notifyDataSetChanged();
	}

	private class MyArrayAdapter extends ArrayAdapter<String> {
		LayoutInflater inflater;

		public MyArrayAdapter(Context ctx, int resourceId,
				ArrayList<String> objects) {
			super(ctx, resourceId, objects);
			inflater = LayoutInflater.from(getApplicationContext());
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = inflater.inflate(R.layout.listview_item_simple, null);

			System.out.println("Posicao: " + position);
			String coupon = couponList.get(position);
			System.out.println("coupon: " + coupon);
			String s[] = coupon.split("\\|");
			final String couponName = s[0];
			final String couponDescription = s[1];

			// limitando o tamanho ao apresentar na listview
			String auxDescription = couponDescription;
			if(auxDescription.length() > 40)
			{
				auxDescription = auxDescription.substring(0, 37) + "...";
			}
			TextView tv = (TextView) convertView
					.findViewById(R.id.listview_item_simple_text);
			tv.setText("\n" + couponName + "\n" + auxDescription + "\n");

			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),
							_51_CouponView.class);
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
