package lais.mc851;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RouteEdit extends Activity
{
	private final static String TAG = "RouteEdit";
	
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
		
		buttonGpsSource.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Clicked on GPS Source");
			}
		});
		
		buttonGpsDestination.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Clicked on GPS Destination");
			}
		});
		
		buttonTypeSource.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Clicked on Type Source");
			}
		});
		
		buttonTypeDestination.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Clicked on Type Desination");
			}
		});
		
		buttonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Clicked on Save");
			}
		});
		
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Clicked on Cancel");
				onBackPressed();
			}
		});
		
		buttonDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "Clicked on Delete");
			}
		});
		
	}
	
}
