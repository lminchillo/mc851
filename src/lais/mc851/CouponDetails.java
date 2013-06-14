package lais.mc851;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CouponDetails extends Activity 
{
	private final static String TAG = "CouponDetails";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupons_details);
	}
	
	public void onClickDelete(View view)
	{
		Log.d(TAG, "Clicked on Delete button");
	}
}
