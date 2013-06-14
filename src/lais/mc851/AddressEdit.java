package lais.mc851;

import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddressEdit extends Activity
{
	private final static String TAG = "AddressEdit";
	
	Button gps = null;
	Button type = null;
	Button save = null;
	Button cancel = null;
	Button delete = null;
	
	EditText addressNameEdit = null;
	
	TextView addressView = null;
	
	String addressName = null;
	String addressStreetValue = null;
	String addressLatLng = null;
	String addressValueTemp = null;
	String addressValue = null;
	
	boolean savedAddress = false;
	String savedAddressName = null;
	String savedAddressValue = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_edit);
		
		getSavedAddress();
		initializeButtons();
	}
	
	@Override
	public void onBackPressed()
	{
		System.gc();
		startActivity(new Intent(getApplicationContext(),Addresses.class));
		finish();
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	private void getSavedAddress()
	{
		Bundle b = getIntent().getExtras();
		if (!b.getString("addressName", "").equals("") && !b.getString("addressValue", "").equals(""))
		{
			savedAddress = true;
			savedAddressName = b.getString("addressName", "");
			savedAddressValue = b.getString("addressValue", "");
			
			addressNameEdit = (EditText) findViewById(R.id.addressEdit_editTextName);
			addressNameEdit.setText(savedAddressName);
			addressView = (TextView) findViewById(R.id.addressEdit_address_text);
			addressView.setText(getStreetAddress(savedAddressValue));
			
			addressName = savedAddressName;
			addressValue = savedAddressValue;
			addressStreetValue = getStreetAddress(addressValue);
			//System.out.println("street value saved: "+addressStreetValue);
			addressLatLng = getLatLng(addressValue);
			//System.out.println("latlng value saved: "+addressLatLng);
		}
	}
	
	private void initializeButtons()
	{
		gps = (Button) findViewById(R.id.addressEdit_buttonGps);
		gps.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "clicked on GPS");
				getAddress(getLastBestLocation());
				System.out.println("Location: "+addressStreetValue);
			}
		});
		type = (Button) findViewById(R.id.addressEdit_buttonType);
		type.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "clicked on Type");
				showDialog();
			}
		});
		save = (Button) findViewById(R.id.addressEdit_buttonSave);
		save.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "clicked on Save");
				
				if (saveChanges())
				{
					if (!savedAddress)
					{
						addressValue = addressName+"\n"+addressStreetValue+"\n"+addressLatLng;
						if (AddressManager.addAddress(addressName, addressValue))
						{
							Toast.makeText(getApplicationContext(), "Endereço salvo", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(),Addresses.class));
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro - o nome pode já ter sido usado", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						addressValue = addressName+"\n"+addressStreetValue+"\n"+addressLatLng;
						if (AddressManager.removeAddress(savedAddressName))
						{
							if (AddressManager.addAddress(addressName, addressValue))
							{
								Toast.makeText(getApplicationContext(), "Endereço salvo", Toast.LENGTH_SHORT).show();
								startActivity(new Intent(getApplicationContext(),Addresses.class));
								finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Erro ao atualizar o endereço", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro ao atualizar o endereço", Toast.LENGTH_SHORT).show();
						}
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Erro - verifique o nome ou endereço digitados", Toast.LENGTH_SHORT).show();
				}
			}
		});
		delete = (Button) findViewById(R.id.addressEdit_buttonDelete);
		delete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "clicked on Delete");
				
				if (saveChanges())
				{
					if (savedAddress)
					{
						if (AddressManager.removeAddress(savedAddressName))
						{
							Toast.makeText(getApplicationContext(), "Endereço deletado", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(),Addresses.class));
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro ao deletar o endereço", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Esse endereço não foi salvo ainda", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Erro - verifique o nome ou endereço digitados", Toast.LENGTH_SHORT).show();
				}
			}
		});
		cancel = (Button) findViewById(R.id.addressEdit_buttonCancel);
		cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.d(TAG, "clicked on Cancel");
				startActivity(new Intent(getApplicationContext(),Addresses.class));
				finish();
			}
		});
	}
	
	private void getLocation(String address)
    {
    	try
    	{
    		Geocoder g = new Geocoder(getApplicationContext());
    		List<Address> a = g.getFromLocationName(address, 1);
    		String value = "";
    		String latlng = "";
    		for (int i=0; i<a.size(); i++)
    		{
    			for (int j=0; j< a.get(i).getMaxAddressLineIndex(); j++)
        		{
        			 value += " - "+a.get(i).getAddressLine(j);
        		}
    			latlng += a.get(i).getLatitude() + "," + a.get(i).getLongitude();
    		}
    		value = value.replaceFirst(" - ", "");
    		addressStreetValue = value;
    		addressView = (TextView) findViewById(R.id.addressEdit_address_text);
    		addressView.setText(addressStreetValue);
    		addressLatLng = latlng;
		}
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    }
	
	private Location getLastBestLocation()
    {
        LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                
		Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
 
        long GPSLocationTime = 0;
        if (null != locationGPS)
        {
        	GPSLocationTime = locationGPS.getTime();
        }
 
        long NetLocationTime = 0;
 
        if (null != locationNet)
        {
            NetLocationTime = locationNet.getTime();
        }
 
        if ( 0 < GPSLocationTime - NetLocationTime )
        {
            return locationGPS;
        }
        else
        {
            return locationNet;
        }
    }
	
	private void getAddress(Location l)
    {
    	try
    	{
    		Geocoder g = new Geocoder(getApplicationContext());
    		Address a = g.getFromLocation(l.getLatitude(), l.getLongitude(), 1).get(0);
    		
    		String value = "";
    		String latlng = "";
    		for (int i = 0; i<a.getMaxAddressLineIndex(); i++)
    		{
    			 value += " - " + a.getAddressLine(i);
    		}
			latlng += a.getLatitude() + "," + a.getLongitude();
    		value = value.replaceFirst(" - ", "");
    		addressStreetValue = value;
    		addressView = (TextView) findViewById(R.id.addressEdit_address_text);
    		addressView.setText(addressStreetValue);
    		addressLatLng = latlng;
		}
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    }
	
	private String getStreetAddress(String address)
	{
		if (address.contains("\n"))
		{
			String res = address.substring(address.indexOf("\n")+1);
			if (address.contains("\n"))
			{
				res = res.substring(0,res.indexOf("\n"));
				return res;
			}
			else return address;
		}
		else return address;
	}
	
	private String getLatLng(String address)
	{
		if (address.contains("\n"))
		{
			String res = address.substring(address.indexOf("\n")+1);
			if (address.contains("\n"))
			{
				res = res.substring(res.indexOf("\n")+1);
				return res;
			}
			else return address;
		}
		else return address;
	}
	
	private void showDialog()
	{
		LayoutInflater factory = LayoutInflater.from(this);
	    final View textEntryView = factory.inflate(R.layout.alert_dialog_simple_edittext, null);
	    final EditText editText = (EditText) textEntryView.findViewById(R.id.alert_dialog_edittext);
	    if (savedAddress)
	    {
	    	editText.setText(getStreetAddress(savedAddressValue));
	    }
	    else if (addressValueTemp != null)
	    {
	    	editText.setText(getStreetAddress(addressValueTemp));
	    }
	    else if (addressValue != null)
	    {
	    	editText.setText(addressValue);
	    }
	    
		Dialog dialog = new AlertDialog.Builder(AddressEdit.this)
	        //.setIconAttribute(android.R.attr.alertDialogIcon)
	        .setTitle(getResources().getString(R.string.addressEdit_addressNew))
	        .setView(textEntryView)
	        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog, int whichButton)
	            {
	                
	                addressValueTemp = editText.getText().toString().trim();
	                getLocation(addressValueTemp);
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
	
	private boolean saveChanges()
	{
		addressNameEdit = (EditText) findViewById(R.id.addressEdit_editTextName);
		addressName = addressNameEdit.getText().toString().trim();
		
		if (addressName.equals("") || addressName.length()<1)
		{
			return false;
		}
		
		if (addressStreetValue == null)
		{
			return false;
		}
		else if (addressStreetValue.equals("") || addressStreetValue.length()<1)
		{
			return false;
		}
		else if (addressLatLng.equals("") || addressLatLng.length()<1)
		{
			return false;
		}
		
		return true;
	}
}
