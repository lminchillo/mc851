package lais.mc851;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;

public class Splashscreen extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		
		new CountDownTimer(2000,1999)
		{
			@Override
			public void onTick(long millisUntilFinished)
			{
				
			}
			
			@Override
			public void onFinish()
			{
				startActivity(new Intent(getApplicationContext(),StartScreen.class));
				finish();
			}
		}.start();
	}
}
