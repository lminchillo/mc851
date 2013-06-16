package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Routes extends Activity
{

	private final static String TAG = "Routes";
	
	private Button buttonNewRoute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes);
		
		initializeButtons();
	}
	
	private void initializeButtons()
	{
		buttonNewRoute = (Button) findViewById(R.id.routes_button_new_route);
		buttonNewRoute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				Log.d(TAG, "Clicked on New Route button");
				startActivity(new Intent(getApplicationContext(), RouteEdit.class));
			}
		});
	}
	
}
