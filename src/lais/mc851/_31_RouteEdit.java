package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class _31_RouteEdit extends Activity
{
	private EditText routeNameEdit = null;
	private TextView routeViewSource = null;
	private TextView routeViewDest = null;
	
	private Button buttonSave;
	private Button buttonCancel;
	private Button buttonDelete;

	private String routeName = null;
	private String routeSourceValue = null;
	private String routeDestValue = null;
	private String routeValue = null;
	
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
		
		initialize();
		initializeButtons();
	}
	
	private void initialize()
	{
		Bundle b = getIntent().getExtras();
		
		routeName = b.getString("routeName", "");
		routeSourceValue = b.getString("routeSourceValue", "");
		routeDestValue = b.getString("routeDestValue", "");
		routeValue = b.getString("routeValue","");

		routeNameEdit = (EditText) findViewById(R.id.route_edit_edit_text_name);
		routeNameEdit.setText(routeName);
		routeViewSource = (TextView) findViewById(R.id.route_edit_text_view_address_source);
		routeViewSource.setText(GPSManager.getStreetAddress(routeSourceValue));
		routeViewDest = (TextView) findViewById(R.id.route_edit_text_view_address_destination);
		routeViewDest.setText(GPSManager.getStreetAddress(routeDestValue));
	}
	
	private void initializeButtons()
	{
		buttonSave = (Button) findViewById(R.id.route_edit_button_save);
		buttonCancel = (Button) findViewById(R.id.route_edit_button_cancel);
		buttonDelete = (Button) findViewById(R.id.route_edit_button_delete);
		
		buttonSave.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String newRouteName = routeNameEdit.getText().toString().trim();
				String newRouteValue = routeValue.replace(routeName, newRouteName);
				if (RouteManager.removeRoute(routeName))
				{
					if (RouteManager.addRoute(newRouteName, newRouteValue))
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
				if (RouteManager.removeRoute(routeName))
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
		});
	}
}
