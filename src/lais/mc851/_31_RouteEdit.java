package lais.mc851;

import java.io.IOException;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class _31_RouteEdit extends Activity
{
	private final static String TAG = "RouteEdit";
	
	private EditText routeNameEdit = null;
	private TextView routeViewSource = null;
	private TextView routeViewDest = null;
	
	private Button buttonGpsSource;
	private Button buttonGpsDestination;
	private Button buttonTypeSource;
	private Button buttonTypeDestination;
	private Button buttonSave;
	private Button buttonCancel;
	private Button buttonDelete;

	private String routeName = null;
	
	private String routeSourceStreetValue = null;
	private String routeSourceLatLng = null;
	private String routeSourceValueTemp = null;
	private String routeSourceValue = null;
	
	private String routeDestStreetValue = null;
	private String routeDestLatLng = null;
	private String routeDestValueTemp = null;
	private String routeDestValue = null;
	
	private boolean savedRoute = false;
	private String savedRouteName = null;
	private String savedRouteSourceValue = null;
	private String savedRouteDestValue = null;
	
	@Override
	public void onBackPressed()
	{
		System.gc();
		startActivity(new Intent(getApplicationContext(), _3_Routes.class));
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_edit);
		
		getSavedRoute();
		initializeButtons();
	}
	
	private void getSavedRoute()
	{
		Bundle b = getIntent().getExtras();
		if (!b.getString("routeName", "").equals("") && !b.getString("routeSourceValue", "").equals("") && !b.getString("routeDestValue", "").equals(""))
		{
			savedRoute = true;
			savedRouteName = b.getString("routeName", "");
			savedRouteSourceValue = b.getString("routeSourceValue", "");
			savedRouteDestValue = b.getString("routeDestValue", "");

			routeNameEdit = (EditText) findViewById(R.id.route_edit_edit_text_name);
			routeNameEdit.setText(savedRouteName);
			routeViewSource = (TextView) findViewById(R.id.route_edit_text_view_address_source);
			routeViewSource.setText(GPSManager.getStreetAddress(savedRouteSourceValue));
			routeViewDest = (TextView) findViewById(R.id.route_edit_text_view_address_destination);
			routeViewDest.setText(GPSManager.getStreetAddress(savedRouteDestValue));
			
			routeName = savedRouteName;
			routeSourceValue = savedRouteSourceValue;
			routeDestValue = savedRouteDestValue;
			routeSourceStreetValue = GPSManager.getStreetAddress(routeSourceValue);
			routeDestStreetValue = GPSManager.getStreetAddress(routeDestValue);
			routeSourceLatLng = GPSManager.getLatLng(routeSourceValue);
			routeDestLatLng = GPSManager.getLatLng(routeDestValue);
		}
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
				try {
					ArrayList<String> list = (ArrayList<String>) GPSManager.getAddress(GPSManager.getLastBestLocation(_31_RouteEdit.this), _31_RouteEdit.this);
					routeSourceStreetValue = list.get(0);
					routeViewSource = (TextView) findViewById(R.id.route_edit_text_view_address_source);
					routeViewSource.setText(routeSourceStreetValue);
					routeSourceLatLng = list.get(1);
					Log.d(TAG, "Location: " + routeSourceStreetValue);
			
				}
				catch(IOException e) 
				{
					e.printStackTrace();
				}
			}
		});
		
		buttonGpsDestination.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				try {
					ArrayList<String> list = (ArrayList<String>) GPSManager.getAddress(GPSManager.getLastBestLocation(_31_RouteEdit.this), _31_RouteEdit.this);
					routeDestStreetValue = list.get(0);
					routeViewDest = (TextView) findViewById(R.id.route_edit_text_view_address_destination);
					routeViewDest.setText(routeDestStreetValue);
					routeDestLatLng = list.get(1);
					Log.d(TAG, "Location: " + routeDestStreetValue);
			
				}
				catch(IOException e) 
				{
					e.printStackTrace();
				}
			}
		});
		
		buttonTypeSource.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				LayoutInflater factory = LayoutInflater.from(_31_RouteEdit.this);
			    final View textEntryView = factory.inflate(R.layout.alert_dialog_simple_edittext, null);
			    final EditText editText = (EditText) textEntryView.findViewById(R.id.alert_dialog_edittext);
			    
			    if (savedRoute)
			    {
			    	editText.setText(GPSManager.getStreetAddress(savedRouteSourceValue));
			    }
			    else if (routeSourceValueTemp != null)
			    {
			    	editText.setText(GPSManager.getStreetAddress(routeSourceValueTemp));
			    }
			    else if (routeSourceValue != null)
			    {
			    	editText.setText(routeSourceValue);
			    }
			    
				Dialog dialog = new AlertDialog.Builder(_31_RouteEdit.this)			        
			        .setTitle(getResources().getString(R.string.address_edit_address_new))
			        .setView(textEntryView)
			        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int whichButton)
			            {
			                routeSourceValueTemp = editText.getText().toString().trim();
			                
			                try 
			                {
			                	GPSManager.getLocation(routeSourceValueTemp, _31_RouteEdit.this);
			                }
			                catch(IOException e) 
			                {
			                	e.printStackTrace();
			                }
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
				Log.d(TAG, "Clicked on Type Source");
			}
		});
		
		buttonTypeDestination.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				LayoutInflater factory = LayoutInflater.from(_31_RouteEdit.this);
			    final View textEntryView = factory.inflate(R.layout.alert_dialog_simple_edittext, null);
			    final EditText editText = (EditText) textEntryView.findViewById(R.id.alert_dialog_edittext);
			    
			    if (savedRoute)
			    {
			    	editText.setText(GPSManager.getStreetAddress(savedRouteDestValue));
			    }
			    else if (routeDestValueTemp != null)
			    {
			    	editText.setText(GPSManager.getStreetAddress(routeDestValueTemp));
			    }
			    else if (routeDestValue != null)
			    {
			    	editText.setText(routeDestValue);
			    }
			    
				Dialog dialog = new AlertDialog.Builder(_31_RouteEdit.this)			        
			        .setTitle(getResources().getString(R.string.address_edit_address_new))
			        .setView(textEntryView)
			        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int whichButton)
			            {
			                routeDestValueTemp = editText.getText().toString().trim();
			                
			                try 
			                {
			                	GPSManager.getLocation(routeDestValueTemp, _31_RouteEdit.this);
			                }
			                catch(IOException e) 
			                {
			                	e.printStackTrace();
			                }
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
				Log.d(TAG, "Clicked on Type Desination");
			}
		});
		
		buttonSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if (saveChanges())
				{
					if (!savedRoute)
					{
						String routeSaveValue = routeName+"\n"+routeSourceStreetValue+"\n"+routeSourceLatLng+"\n"+routeDestStreetValue+"\n"+routeDestLatLng;
						routeSourceValue = routeName+"\n"+routeSourceStreetValue+"\n"+routeSourceLatLng;
						routeDestValue = routeName+"\n"+routeDestStreetValue+"\n"+routeDestLatLng;
						if (RouteManager.addRoute(routeName, routeSaveValue))
						{
							Toast.makeText(getApplicationContext(), "Rota salva!", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(), _3_Routes.class));
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro - o nome pode já ter sido usado", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						String routeSaveValue = routeName+"\n"+routeSourceStreetValue+"\n"+routeSourceLatLng+"\n"+routeDestStreetValue+"\n"+routeDestLatLng;
						routeSourceValue = routeName+"\n"+routeSourceStreetValue+"\n"+routeSourceLatLng;
						routeDestValue = routeName+"\n"+routeDestStreetValue+"\n"+routeDestLatLng;
						if (RouteManager.removeRoute(savedRouteName))
						{
							if (RouteManager.addRoute(routeName, routeSaveValue))
							{
								Toast.makeText(getApplicationContext(), "Rota Salva!", Toast.LENGTH_SHORT).show();
								startActivity(new Intent(getApplicationContext(), _3_Routes.class));
								finish();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Erro ao atualizar a rota", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro ao atualizar a rota", Toast.LENGTH_SHORT).show();
						}
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Erro - verifique o nome ou endereços digitados", Toast.LENGTH_SHORT).show();
				}
				
				Log.d(TAG, "Clicked on Save");
			}
		});
		
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				onBackPressed();
				Log.d(TAG, "Clicked on Cancel");
			}
		});
		
		buttonDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				if (saveChanges())
				{
					if (savedRoute)
					{
						if (RouteManager.removeRoute(savedRouteName))
						{
							Toast.makeText(getApplicationContext(), "Rota deletada!", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(), _3_Routes.class));
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro ao deletar a rota", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Essa rota não foi salva ainda", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Erro - verifique o nome ou endereços digitados", Toast.LENGTH_SHORT).show();
				}

				Log.d(TAG, "Clicked on Delete");
			}
		});
		
	}
	
	private boolean saveChanges()
	{
		routeNameEdit = (EditText) findViewById(R.id.route_edit_edit_text_name);
		routeName = routeNameEdit.getText().toString().trim();
		
		if (routeName.equals("") || routeName.length() < 1)
		{
			return false;
		}
		
		if (routeSourceStreetValue == null || routeDestStreetValue == null)
		{
			return false;
		}
		else if (routeSourceStreetValue.equals("") || routeSourceStreetValue.length() < 1 || routeDestStreetValue.equals("") || routeDestStreetValue.length() < 1)
		{
			return false;
		}
		else if (routeSourceLatLng.equals("") || routeSourceLatLng.length() < 1 || routeDestLatLng.equals("") || routeDestLatLng.length() < 1)
		{
			return false;
		}
		
		return true;
	}
}
