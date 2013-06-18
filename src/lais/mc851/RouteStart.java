package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RouteStart extends Activity 
{
	private Button buttonNewRoute;
	private Button buttonSavedRoute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_start);
		
		initializeButtons();
	}
	
	private void initializeButtons()
	{
		buttonNewRoute = (Button) findViewById(R.id.take_bus_button_new_route);
		buttonNewRoute.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(getApplicationContext(), RouteNew.class));
			}
		});
		
		buttonSavedRoute = (Button) findViewById(R.id.take_bus_button_saved_route);
		buttonSavedRoute.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent(getApplicationContext(), RouteSaved.class));
			}
		});
	}
	
}
