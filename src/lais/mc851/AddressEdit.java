package lais.mc851;

import java.util.List;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

public class AddressEdit extends Activity
{
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
		startActivity(new Intent(getApplicationContext(),Addresses.class));
		finish();
		super.onBackPressed();
	}
	
	private void getSavedAddress()
	{
		Bundle b = getIntent().getExtras();
		if (!b.getString("addressName", "").equals("") && !b.getString("addressValue", "").equals(""))
		{
			savedAddress = true;
			savedAddressName = b.getString("addressName", "");
			savedAddressValue = b.getString("addressValue", "");
			
			addressNameEdit = (EditText) findViewById(R.id.address_edit_name_edit);
			addressNameEdit.setText(savedAddressName);
			addressView = (TextView) findViewById(R.id.address_edit_address_text);
			addressView.setText(getStreetAddress(savedAddressValue));
			
			addressName = savedAddressName;
			addressValue = savedAddressValue;
		}
	}
	
	private void initializeButtons()
	{
		gps = (Button) findViewById(R.id.address_edit_gps);
		gps.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
			}
		});
		type = (Button) findViewById(R.id.address_edit_type);
		type.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showDialog();
			}
		});
		save = (Button) findViewById(R.id.address_edit_save);
		save.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (saveChanges())
				{
					if (!savedAddress)
					{
						addressValue = addressName+"\n"+addressStreetValue+"\n"+addressLatLng;
						if (AddressManager.addAddress(addressName, addressValue))
						{
							Toast.makeText(getApplicationContext(), "Endere�o salvo", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(),Addresses.class));
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro - o nome pode j� ter sido usado", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						addressValue = addressName+"\n"+addressStreetValue+"\n"+addressLatLng;
						if (AddressManager.removeAddress(addressName))
						{
							if (AddressManager.addAddress(addressName, addressValue))
							{
								Toast.makeText(getApplicationContext(), "Endere�o salvo", Toast.LENGTH_SHORT).show();
								startActivity(new Intent(getApplicationContext(),Addresses.class));
								finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Erro ao atualizar o endere�o", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							if (AddressManager.addAddress(addressName, addressValue))
							{
								Toast.makeText(getApplicationContext(), "Endere�o salvo", Toast.LENGTH_SHORT).show();
								startActivity(new Intent(getApplicationContext(),Addresses.class));
								finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Erro ao atualizar o endere�o", Toast.LENGTH_SHORT).show();
							}
						}
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Erro - verifique o nome ou endere�o digitados", Toast.LENGTH_SHORT).show();
				}
			}
		});
		delete = (Button) findViewById(R.id.address_edit_delete);
		delete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (AddressManager.removeAddress(addressName))
				{
					Toast.makeText(getApplicationContext(), "Endere�o deletado", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getApplicationContext(),Addresses.class));
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Erro ao deletar o endere�o", Toast.LENGTH_SHORT).show();
				}
			}
		});
		cancel = (Button) findViewById(R.id.address_edit_cancel);
		cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
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
    			System.out.println("=Address= Lat "+a.get(i).getLatitude()+", Lng "+a.get(i).getLongitude());
    			latlng += a.get(i).getLatitude() + "," + a.get(i).getLongitude();
    		}
    		value = value.replaceFirst(" - ", "");
    		addressStreetValue = value;
    		addressView = (TextView) findViewById(R.id.address_edit_address_text);
    		addressView.setText(addressStreetValue);
    		System.out.println(addressView.getText());
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
		Dialog dialog = new AlertDialog.Builder(AddressEdit.this)
	        //.setIconAttribute(android.R.attr.alertDialogIcon)
	        .setTitle(getResources().getString(R.string.address_edit_address_new))
	        .setView(textEntryView)
	        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog, int whichButton)
	            {
	                
	                addressValueTemp = editText.getText().toString().trim();
	                getLocation(addressValueTemp);
	            }
	        })
	        .setNegativeButton(getResources().getString(R.string.address_edit_cancel), new DialogInterface.OnClickListener()
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
		addressNameEdit = (EditText) findViewById(R.id.address_edit_name_edit);
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
