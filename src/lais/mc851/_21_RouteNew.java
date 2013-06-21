package lais.mc851;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class _21_RouteNew extends Activity
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
	public void onBackPressed()
	{
		startActivity(new Intent(getApplicationContext(),_2_RouteStart.class));
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_new);
		
		initializeButtons();
	}
	
	private void initializeButtons()
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
		        .setTitle(getResources().getString(R.string.route_new_saved_addr))
		        .setItems(addresses, new DialogInterface.OnClickListener()
		        {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						String addr = listSavedAddresses.get(which);
						String[] tok = addr.split("\n");
						addressDestStreetValue = tok[1];
						addressDestLatLng = tok[2];
	                	textViewAddressDestination.setText(addressDestStreetValue);
					}
				})
		        .create();
			
				dialog.show();
			}
		});
		
		buttonSavedAddressSource = (Button) findViewById(R.id.take_bus_new_route_button_saved_source);
		if (listSavedAddresses.isEmpty())
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
		        .setTitle(getResources().getString(R.string.route_new_saved_addr))
		        .setItems(addresses, new DialogInterface.OnClickListener()
		        {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						String addr = listSavedAddresses.get(which);
						String[] tok = addr.split("\n");
						addressSourceStreetValue = tok[1];
						addressSourceLatLng = tok[2];
	                	textViewAddressSource.setText(addressSourceStreetValue);
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
		if(addressSourceStreetValue == null || addressSourceStreetValue.isEmpty())
		{
			Toast.makeText(getApplicationContext(), "Por favor, preencha o endereço de origem", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(addressDestStreetValue == null || addressDestStreetValue.isEmpty())
		{
			Toast.makeText(getApplicationContext(), "Por favor, preencha o endereço de destino", Toast.LENGTH_SHORT).show();
			return;
		}
		
		System.out.println("sourceAddress: "+ addressSourceStreetValue);
		System.out.println("sourceLatLng: "+ addressSourceLatLng);
		System.out.println("destinationAddress: "+ addressDestStreetValue);
		System.out.println("destinationLatLng: "+ addressDestLatLng);
		
		String[] origin = new String[3];
		String[] destination = new String [3];
		String aux = "";
		
		if (addressSourceStreetValue.contains(",")) aux = addressSourceStreetValue.substring(0,addressSourceStreetValue.indexOf(","));
		else aux = addressSourceStreetValue;
		origin[0] = aux;
		
		if (addressSourceStreetValue.contains(","))
		{
			aux = addressSourceStreetValue.substring(addressSourceStreetValue.indexOf(",")+2);
			if (aux.contains(" - "))
			{
				aux = aux.substring(0,aux.indexOf(" - "));
			}
			if (aux.contains("-"))
			{
				int aux1 = Integer.parseInt(aux.substring(0,aux.indexOf("-")));
				int aux2 = Integer.parseInt(aux.substring(aux.indexOf("-")+1));
				aux1 = (aux1+aux2)/2;
				aux = ""+aux1;
			}
		}
		else aux = "";
		origin[1] = aux;
		origin[2] = "";
		
		if (addressDestStreetValue.contains(",")) aux = addressDestStreetValue.substring(0,addressDestStreetValue.indexOf(","));
		else aux = addressDestStreetValue;
		destination[0] = aux;
		
		if (addressDestStreetValue.contains(","))
		{
			aux = addressDestStreetValue.substring(addressDestStreetValue.indexOf(",")+2);
			if (aux.contains(" - "))
			{
				aux = aux.substring(0,aux.indexOf(" - "));
				
			}
			if (aux.contains("-"))
			{
				int aux1 = Integer.parseInt(aux.substring(0,aux.indexOf("-")));
				int aux2 = Integer.parseInt(aux.substring(aux.indexOf("-")+1));
				aux1 = (aux1+aux2)/2;
				aux = ""+aux1;
			}
		}
		else aux = "";
		destination[1] = aux;
		destination[2] = "";
		
		String initialize = RouteGetter.getData(origin, destination);
		String routeValue = "name"+"\n"+addressSourceStreetValue+"\n"+addressSourceLatLng+"\n"+addressDestStreetValue+"\n"+addressDestLatLng+"\n"+initialize;
		
		if(checkBoxSave.isChecked())
		{
			String newRouteName = editTextRouteName.getText().toString();
			if(newRouteName.isEmpty())
			{
				Toast.makeText(getApplicationContext(), "Por favor, preencha o nome para a nova rota", Toast.LENGTH_SHORT).show();
				return;
			}
			
			String routeSaveValue = routeValue.replaceFirst("name", newRouteName);
			if(!RouteManager.addRoute(newRouteName, routeSaveValue))
			{
				Toast.makeText(getApplicationContext(), "Erro - o nome da nova rota pode já ter sido usado!", Toast.LENGTH_SHORT).show();
				return;
			}
			Toast.makeText(getApplicationContext(), "Nova rota salva com sucesso!", Toast.LENGTH_SHORT).show();
		}
		
		Intent intent = new Intent(getApplicationContext(), _23_Route.class);
		intent.putExtra("route", routeValue);
		startActivity(intent);
		finish();
	}
	
}
