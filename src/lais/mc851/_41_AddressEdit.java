package lais.mc851;

import java.io.IOException;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class _41_AddressEdit extends Activity
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
		System.gc();
		startActivity(new Intent(getApplicationContext(), _4_Addresses.class));
		finish();
	}
	
	private void getSavedAddress()
	{
		Bundle b = getIntent().getExtras();
		if (!b.getString("addressName", "").equals("") && !b.getString("addressValue", "").equals(""))
		{
			savedAddress = true;
			savedAddressName = b.getString("addressName", "");
			savedAddressValue = b.getString("addressValue", "");
			
			addressNameEdit = (EditText) findViewById(R.id.address_edit_edit_text_name);
			addressNameEdit.setText(savedAddressName);
			addressView = (TextView) findViewById(R.id.address_edit_address_text);
			addressView.setText(GPSManager.getStreetAddress(savedAddressValue));
			
			addressName = savedAddressName;
			addressValue = savedAddressValue;
			addressStreetValue = GPSManager.getStreetAddress(addressValue);
			//System.out.println("street value saved: "+addressStreetValue);
			addressLatLng = GPSManager.getLatLng(addressValue);
			//System.out.println("latlng value saved: "+addressLatLng);
		}
	}
	
	private void initializeButtons()
	{
		gps = (Button) findViewById(R.id.address_edit_button_gps);
		gps.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ArrayList<String> ret = (ArrayList<String>) GPSManager.getAddress(GPSManager.getLastBestLocation(_41_AddressEdit.this), _41_AddressEdit.this);
				addressStreetValue = ret.get(0);
	    		addressView = (TextView) findViewById(R.id.address_edit_address_text);
	    		addressView.setText(addressStreetValue);
	    		addressLatLng = ret.get(1);
				
				System.out.println("Location: "+addressStreetValue);
			}
		});
		type = (Button) findViewById(R.id.address_edit_button_type);
		type.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showDialog();
			}
		});
		save = (Button) findViewById(R.id.address_edit_button_save);
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
							Toast.makeText(getApplicationContext(), "Endereço salvo", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(),_4_Addresses.class));
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
								startActivity(new Intent(getApplicationContext(),_4_Addresses.class));
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
		delete = (Button) findViewById(R.id.address_edit_button_delete);
		delete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (saveChanges())
				{
					if (savedAddress)
					{
						if (AddressManager.removeAddress(savedAddressName))
						{
							Toast.makeText(getApplicationContext(), "Endereço deletado", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(),_4_Addresses.class));
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
		cancel = (Button) findViewById(R.id.address_edit_button_cancel);
		cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getApplicationContext(),_4_Addresses.class));
				finish();
			}
		});
	}
	
	private void showDialog()
	{
		LayoutInflater factory = LayoutInflater.from(this);
	    final View textEntryView = factory.inflate(R.layout.alert_dialog_simple_edittext, null);
	    final EditText editText = (EditText) textEntryView.findViewById(R.id.alert_dialog_edittext);
	    if (savedAddress)
	    {
	    	editText.setText(GPSManager.getStreetAddress(savedAddressValue));
	    }
	    else if (addressValueTemp != null)
	    {
	    	editText.setText(GPSManager.getStreetAddress(addressValueTemp));
	    }
	    else if (addressValue != null)
	    {
	    	editText.setText(addressValue);
	    }
	    
		Dialog dialog = new AlertDialog.Builder(_41_AddressEdit.this)
	        //.setIconAttribute(android.R.attr.alertDialogIcon)
	        .setTitle(getResources().getString(R.string.address_edit_address_new))
	        .setView(textEntryView)
	        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog, int whichButton)
	            {
	                addressValueTemp = editText.getText().toString().trim();

                	ArrayList<String> ret = (ArrayList<String>) GPSManager.getLocation(addressValueTemp, _41_AddressEdit.this);
                	addressStreetValue = ret.get(0);
                	addressView = (TextView) findViewById(R.id.address_edit_address_text);
                	addressView.setText(addressStreetValue);
                	addressLatLng = ret.get(1);
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
		addressNameEdit = (EditText) findViewById(R.id.address_edit_edit_text_name);
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
