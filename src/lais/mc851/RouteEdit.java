package lais.mc851;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RouteEdit extends Activity
{
	private Button buttonGpsSource;
	private Button buttonGpsDestination;
	private Button buttonTypeSource;
	private Button buttonTypeDestination;
	private Button buttonSave;
	private Button buttonCancel;
	private Button buttonDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_edit);
		
		initializeButtons();
	}
	
	private void initializeButtons()
	{
		buttonGpsSource = (Button) findViewById(R.id.route_edit_button_gps_source);
		buttonGpsDestination = (Button) findViewById(R.id.route_edit_button_gps_destination);
		buttonTypeSource = (Button) findViewById(R.id.route_edit_button_type_source);
		buttonTypeDestination = (Button) findViewById(R.id.route_edit_button_type_destination);
		buttonSave = (Button) findViewById(R.id.route_edit_button_save);
		buttonCancel = (Button) findViewById(R.id.route_edit_button_cancel);
		buttonDelete = (Button) findViewById(R.id.route_edit_button_delete);
		
		buttonGpsSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonGpsDestination.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonTypeSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonTypeDestination.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonSave.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonCancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		
		buttonDelete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
	}
	
}
