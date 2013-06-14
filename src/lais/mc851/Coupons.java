package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Coupons extends Activity
{
private final static String TAG = "Coupons";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupons);
	}
	
	public void onClickDummy(View view)
	{
		Log.d(TAG, "Clicked on Dummy Button");
		startActivity(new Intent(getApplicationContext(), CouponDetails.class));
	}
}
