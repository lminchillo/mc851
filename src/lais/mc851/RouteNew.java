package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RouteNew extends Activity
{
	private LinearLayout layoutSave;
	
	private CheckBox checkBoxSave;
	private EditText editTextRouteName;
	
	private Button buttonCancel;
	private Button buttonConfirm;
	private Button buttonGpsSource;
	private Button buttonTypeSource;
	private Button buttonSavedAddressSource;
	private Button buttonTypeDestination;
	private Button buttonSavedAddressDestination;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_new);
		
		initializeWidgets();
	}
	
	private void initializeWidgets()
	{
		layoutSave = (LinearLayout) findViewById(R.id.take_bus_new_route_layout_save);
		layoutSave.setVisibility(View.GONE);
		
		checkBoxSave = (CheckBox) findViewById(R.id.take_bus_new_route_checkbox_save_address);
		checkBoxSave.setChecked(false);
		checkBoxSave.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) 
			{
				layoutSave.setVisibility(isChecked ? View.VISIBLE : View.GONE);
				editTextRouteName.setEnabled(isChecked);
			}
		});
		
		editTextRouteName = (EditText) findViewById(R.id.take_bus_new_route_edit_text_route_name);
		
		buttonCancel = (Button) findViewById(R.id.take_bus_new_route_button_cancel);
		buttonCancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});
		
		buttonConfirm = (Button) findViewById(R.id.take_bus_new_route_button_confirm);
		buttonConfirm.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getApplicationContext(), Route.class));
			}
		});
		
		buttonGpsSource = (Button) findViewById(R.id.take_bus_new_route_button_gps_source);
		buttonGpsSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonSavedAddressDestination = (Button) findViewById(R.id.take_bus_new_route_button_saved_destination);
		buttonSavedAddressDestination.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonSavedAddressSource = (Button) findViewById(R.id.take_bus_new_route_button_saved_source);
		buttonSavedAddressSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonTypeDestination = (Button) findViewById(R.id.take_bus_new_route_button_type_destination);
		buttonTypeDestination.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
		
		buttonTypeSource = (Button) findViewById(R.id.take_bus_new_route_button_type_source);
		buttonTypeSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
	}
	
}
