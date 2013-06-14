package lais.mc851;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class Route extends Activity
{
	GoogleMap map = null;
	String initialize = null;
	
	@Override
	public void onResume()
	{
		super.onResume();
		checkMap();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route);
		
		checkMap();
	}
	
	private void checkMap()
	{
		if (map == null)
		{
	        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.route_map)).getMap();

	        if (map != null)
	        {
	            initializeMap();
	        }
	    }
	}
	
	private void initializeMap()
	{
		if (initialize == null)
		{
			final String[] origin = {"R DR  ALCIDES SOARES CUNHA","240"," "};
			final String[] destination = {"R DR  LAURO PIMENTEL","433"," "};
			
			initialize = RouteGetter.getData(origin, destination)[1];
		}
		
		if (initialize != null)
		{
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(getMapInicialLatLng(), getMapInicialZoom()));
			
			parseInitialize();
		}
	}
	
	private LatLng getMapInicialLatLng()
	{
		float aux1=0,aux2=0;
		String aux = initialize.split("\n")[3];
		if (aux.contains("LatLng("))
		{
			aux = aux.substring(aux.indexOf("LatLng(")+7);
			aux1 = Float.parseFloat(aux.substring(0,aux.indexOf(",")));
			aux2 = Float.parseFloat(aux.substring(aux.indexOf(",")+1,aux.indexOf(");")));
		}
		return new LatLng(aux1, aux2);
	}
	
	private float getMapInicialZoom()
	{
		float zoom = 14;
		String aux = initialize.split("\n")[4];
		if (aux.contains("zoom:"))
		{
			aux = aux.substring(aux.indexOf("zoom:")+6);
			aux = aux.substring(0,aux.indexOf(","));
			zoom = Float.parseFloat(aux);
			zoom = zoom * 7/6;
			return zoom;
		}
		else
		{
			return 14;
		}
	}
	
	private void parseInitialize()
	{
		String[] init = initialize.split("\n")[10].split(";");
		for (int i=0; i<init.length; i++)
		{
			while (init[i].toCharArray()[0]==' ')
			{
				init[i] = init[i].replaceFirst(" ","");
			}
		}
		
		int lineCount = 0;
		for (int i=0; i<init.length; i++)
		{
			if (init[i].contains("var l_"+(lineCount+1)))
			{
				lineCount++;
			}
			if (init[i].contains("var p_1"))
			{
				break;
			}
		}
		
		PolylineOptions[] lines = new PolylineOptions[lineCount];
		
		
		for (int i=0; i<3*lineCount; i++)
		{
			String aux = init[i];
			if (i%3==0)
			{
				lines[i/3] = new PolylineOptions();
				if (aux.contains("var l_"))
				{
					aux = aux.substring(aux.indexOf("[")+2);
				}
				while (aux.contains("new"))
				{
					aux = aux.substring(aux.indexOf("LatLng(")+7);
					String lat = aux.substring(0,aux.indexOf(","));
					String lng = aux.substring(aux.indexOf(",")+1,aux.indexOf(")"));
					lines[i/3].add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
				}
			}
			if (i%3==1)
			{
				System.out.println("i:"+i);
				System.out.println("i-1/3:"+((i-1)/3));
				lines[(i-1)/3].width(6);
				if (aux.contains("strokeColor:"))
				{
					aux = aux.substring(aux.indexOf("strokeColor: ")+15);
					aux = aux.substring(0, aux.indexOf("'"));
					aux = "#88"+aux;
					System.out.println(aux);
					lines[i/3].color(Color.parseColor(aux));
				}
			}
			if (i%3==2)
			{
				map.addPolyline(lines[(i-2)/3]);
			}
		}
	}
}
