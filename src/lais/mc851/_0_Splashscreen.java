package lais.mc851;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;

@SuppressLint("CommitPrefEdits")
public class _0_Splashscreen extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		
		new AsyncTask<Void, Void, Void>()
		{
			@Override
			protected Void doInBackground(Void... params)
			{
				//Initialize SharedPreferences and Editor
				SharedPreferences pref = getSharedPreferences("SETTINGS_PREFS", 0);
				Editor e = pref.edit();
				
				//Initialize AddressManager
				new AddressManager(pref,e);
			    
				//Initialize CouponManager
				new CouponManager(pref,e);
				
				//Initialize RouteManager
				new RouteManager(pref, e);
				
				//Initialize RouteGetter
				new RouteGetter();
				
				//Initialize CouponService
				boolean running = false;
				ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
			    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
			    {
			        if (CouponService.class.getName().equals(service.service.getClassName()))
			        {
			            running = true;
			        }
			    }
			    if (!running)
			    {
			    	startService(new Intent(getApplicationContext(), CouponService.class));
			    }
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result)
			{
				startActivity(new Intent(getApplicationContext(),_1_StartScreen.class));
				finish();
			}
		}.execute();
	}
}
