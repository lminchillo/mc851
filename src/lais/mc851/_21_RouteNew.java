package lais.mc851;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class _21_RouteNew extends Activity
{
	private final static String TAG = "RouteNew";
	
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
	
	private TextView textViewAddressSource;
	private TextView textViewAddressDestination;
	
	private String addressSourceStreetValue = null;
	private String addressSourceLatLng = null;
	private String addressSourceValueTemp = null;
	private String addressSourceValue = null;
	
	private String addressDestStreetValue = null;
	private String addressDestLatLng = null;
	private String addressDestValueTemp = null;
	private String addressDestValue = null;
	
	private ArrayList<String> listSavedAddresses;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_new);
		
		initializeWidgets();
	}
	
	private void initializeWidgets()
	{
		listSavedAddresses = new ArrayList<String>();
		listSavedAddresses = AddressManager.getAddressList();
		
		textViewAddressSource = (TextView) findViewById(R.id.take_bus_new_route_text_view_address_source);
		
		textViewAddressDestination = (TextView) findViewById(R.id.take_bus_new_route_text_view_address_destination);
		
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
				actionConfirm();
			}
		});
		
		buttonGpsSource = (Button) findViewById(R.id.take_bus_new_route_button_gps_source);
		buttonGpsSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ArrayList<String> list = (ArrayList<String>) GPSManager.getAddress(GPSManager.getLastBestLocation());
				addressSourceStreetValue = list.get(0);
				textViewAddressSource.setText(addressSourceStreetValue);
				addressSourceLatLng = list.get(1);
				Log.d(TAG, "Source Address: " + addressSourceStreetValue);
				Log.d(TAG, "Source LatLng: " + addressSourceLatLng);
			}
		});
		
		buttonSavedAddressDestination = (Button) findViewById(R.id.take_bus_new_route_button_saved_destination);
		if(listSavedAddresses.isEmpty())
		{
			buttonSavedAddressDestination.setEnabled(false);
			buttonSavedAddressDestination.setVisibility(View.GONE);
		}
		buttonSavedAddressDestination.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String[] addresses = new String[listSavedAddresses.size()];
				for(int i = 0; i < listSavedAddresses.size(); i++)
				{
					String addr = listSavedAddresses.get(i);
					String[] tok = addr.split("\n");
					String name = tok[0];
					String address = tok[1];
					if(address.length() > 40)
					{
						address = address.substring(0, 37) + "...";
					}
					addresses[i] = name + " (" + address + ")";
				}
				
				Dialog dialog = new AlertDialog.Builder(_21_RouteNew.this)			        
		        .setTitle(getResources().getString(R.string.take_bus_new_route_saved_addr))
		        .setItems(addresses, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "index: " + which);
						String addr = listSavedAddresses.get(which);
						String[] tok = addr.split("\n");
						addressDestStreetValue = tok[1];
						addressDestLatLng = tok[2];
	                	textViewAddressDestination.setText(addressDestStreetValue);
	    				Log.d(TAG, "Dest Address: " + addressDestStreetValue);
	    				Log.d(TAG, "Dest LatLng: " + addressDestLatLng);
					}
				})
		        .create();
			
				dialog.show();
			}
		});
		
		buttonSavedAddressSource = (Button) findViewById(R.id.take_bus_new_route_button_saved_source);
		if(listSavedAddresses.isEmpty())
		{
			buttonSavedAddressSource.setEnabled(false);
			buttonSavedAddressSource.setVisibility(View.GONE);
		}
		buttonSavedAddressSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String[] addresses = new String[listSavedAddresses.size()];
				for(int i = 0; i < listSavedAddresses.size(); i++)
				{
					String addr = listSavedAddresses.get(i);
					String[] tok = addr.split("\n");
					String name = tok[0];
					String address = tok[1];
					if(address.length() > 40)
					{
						address = address.substring(0, 37) + "...";
					}
					addresses[i] = name + " (" + address + ")";
				}
				
				Dialog dialog = new AlertDialog.Builder(_21_RouteNew.this)			        
		        .setTitle(getResources().getString(R.string.take_bus_new_route_saved_addr))
		        .setItems(addresses, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "index: " + which);
						String addr = listSavedAddresses.get(which);
						String[] tok = addr.split("\n");
						addressSourceStreetValue = tok[1];
						addressSourceLatLng = tok[2];
	                	textViewAddressSource.setText(addressSourceStreetValue);
	    				Log.d(TAG, "Source Address: " + addressSourceStreetValue);
	    				Log.d(TAG, "Source LatLng: " + addressSourceLatLng);
					}
				})
		        .create();
			
				dialog.show();
			}
		});
		
		buttonTypeDestination = (Button) findViewById(R.id.take_bus_new_route_button_type_destination);
		buttonTypeDestination.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LayoutInflater factory = LayoutInflater.from(_21_RouteNew.this);
			    final View textEntryView = factory.inflate(R.layout.alert_dialog_simple_edittext, null);
			    final EditText editText = (EditText) textEntryView.findViewById(R.id.alert_dialog_edittext);
			    
			    if (addressDestValueTemp != null)
			    {
			    	editText.setText(GPSManager.getStreetAddress(addressDestValueTemp));
			    }
			    else if (addressDestValue != null)
			    {
			    	editText.setText(addressDestValue);
			    }
			    
				Dialog dialog = new AlertDialog.Builder(_21_RouteNew.this)			        
			        .setTitle(getResources().getString(R.string.address_edit_address_new))
			        .setView(textEntryView)
			        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int whichButton)
			            {
			            	addressDestValueTemp = editText.getText().toString().trim();

		                	ArrayList<String> ret = (ArrayList<String>) GPSManager.getLocation(addressDestValueTemp);
		                	addressDestStreetValue = ret.get(0);
		                	addressDestLatLng = ret.get(1);
		                	textViewAddressDestination.setText(addressDestStreetValue);
		    				Log.d(TAG, "Dest Address: " + addressDestStreetValue);
		    				Log.d(TAG, "Dest LatLng: " + addressDestLatLng);
			            }
			        })
			        .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int whichButton)
			            {
			            	
			            }
			        })
			        .create();
				
				dialog.show();
			}
		});
		
		buttonTypeSource = (Button) findViewById(R.id.take_bus_new_route_button_type_source);
		buttonTypeSource.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LayoutInflater factory = LayoutInflater.from(_21_RouteNew.this);
			    final View textEntryView = factory.inflate(R.layout.alert_dialog_simple_edittext, null);
			    final EditText editText = (EditText) textEntryView.findViewById(R.id.alert_dialog_edittext);
			    
			    if (addressSourceValueTemp != null)
			    {
			    	editText.setText(GPSManager.getStreetAddress(addressSourceValueTemp));
			    }
			    else if (addressSourceValue != null)
			    {
			    	editText.setText(addressSourceValue);
			    }
			    
				Dialog dialog = new AlertDialog.Builder(_21_RouteNew.this)			        
			        .setTitle(getResources().getString(R.string.address_edit_address_new))
			        .setView(textEntryView)
			        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int whichButton)
			            {
			            	addressSourceValueTemp = editText.getText().toString().trim();

		                	ArrayList<String> ret = (ArrayList<String>) GPSManager.getLocation(addressSourceValueTemp);
		                	addressSourceStreetValue = ret.get(0);
		                	addressSourceLatLng = ret.get(1);
		                	textViewAddressSource.setText(addressSourceStreetValue);
		    				Log.d(TAG, "Source Address: " + addressSourceStreetValue);
		    				Log.d(TAG, "Source LatLng: " + addressSourceLatLng);
			            }
			        })
			        .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int whichButton)
			            {
			            	
			            }
			        })
			        .create();
				
				dialog.show();
			}
		});
	}
	
	private void actionConfirm()
	{
		Log.d(TAG, "checking route values...");
		
		if(addressSourceStreetValue == null || addressSourceStreetValue.isEmpty())
		{
			Log.d(TAG, "no source address!");
			Toast.makeText(getApplicationContext(), "Por favor, preencha o endereço de origem", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(addressDestStreetValue == null || addressDestStreetValue.isEmpty())
		{
			Log.d(TAG, "no destination address!");
			Toast.makeText(getApplicationContext(), "Por favor, preencha o endereço de destino", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(checkBoxSave.isChecked())
		{
			Log.d(TAG, "saving new route...");
			
			String newRouteName = editTextRouteName.getText().toString();
			if(newRouteName.isEmpty())
			{
				Log.d(TAG, "no route name!");
				Toast.makeText(getApplicationContext(), "Por favor, preencha o nome para a nova rota", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Log.d(TAG, "new route name: " + newRouteName);
			
			String routeSaveValue = newRouteName+"\n"+addressSourceStreetValue+"\n"+addressSourceLatLng+"\n"+addressDestStreetValue+"\n"+addressDestLatLng;
			if(!RouteManager.addRoute(newRouteName, routeSaveValue))
			{
				Log.w(TAG, "error saving route!");
				Toast.makeText(getApplicationContext(), "Erro - o nome da nova rota pode já ter sido usado!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Log.d(TAG, "saved new route!");
			Toast.makeText(getApplicationContext(), "Nova rota salva com sucesso!", Toast.LENGTH_SHORT).show();
		}
		
		Log.d(TAG, "everything ok! proceeding to Activity Route...");
		startActivity(new Intent(getApplicationContext(), _23_Route.class));
	}
	
}
