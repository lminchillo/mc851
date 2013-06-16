package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Coupons extends Activity
{
	private final static String TAG = "Coupons";
	
	// TODO: remover depois
	private Button buttonDummy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupons);
		
		initializeButtons();
	}
	
	private void initializeButtons()
	{
		buttonDummy = (Button) findViewById(R.id.coupons_button_dummy);
		buttonDummy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Clicked on Dummy Button");
				startActivity(new Intent(getApplicationContext(), CouponDetails.class));
			}
		});
	}
}
