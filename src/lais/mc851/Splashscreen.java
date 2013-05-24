package lais.mc851;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Splashscreen extends Activity
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
				new CouponManager();
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result)
			{
				startActivity(new Intent(getApplicationContext(),StartScreen.class));
				finish();
			}
		}.execute();
	}
}
