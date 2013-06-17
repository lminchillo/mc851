package lais.mc851;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TakeBusSavedRoute extends Activity
{
	private final static String TAG = "TakeBusSavedRoute";
	
	private Button buttonCancel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_bus_saved_route);
		
		initializeButtons();
	}
	
	private void initializeButtons()
	{
		buttonCancel = (Button) findViewById(R.id.take_bus_saved_route_button_cancelar);
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Log.d(TAG, "Clicked on Cancel button");
				onBackPressed();
			}
		});
	}
}
