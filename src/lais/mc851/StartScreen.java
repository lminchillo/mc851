package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class StartScreen extends Activity
{
private final static String TAG = "StartScreen";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_screen);
	}
	
	public void onClickTakeBus(View view)
	{
		Log.d(TAG, "clicked on Take Bus");
		// startActivity(new Intent(getApplicationContext(), TakeBus.class));
	}
	
	public void onClickSavedRoutes(View view)
	{
		Log.d(TAG, "clicked on Saved Routes");
		// startActivity(new Intent(getApplicationContext(), Routes.class));
	}
	
	public void onClickSavedAddresses(View view)
	{
		Log.d(TAG, "clicked on Saved Addresses");
		startActivity(new Intent(getApplicationContext(), Addresses.class));
	}
	
	public void onClickMyCoupons(View view)
	{
		Log.d(TAG, "clicked on My Coupons");
		startActivity(new Intent(getApplicationContext(), Coupons.class));
	}
	
	public void onClickConfig(View view)
	{
		Log.d(TAG, "clicked on Config");
		startActivity(new Intent(getApplicationContext(), Settings.class));
	}
	
}
