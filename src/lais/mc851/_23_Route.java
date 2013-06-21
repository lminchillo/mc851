package lais.mc851;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class _23_Route extends Activity implements OnInfoWindowClickListener
{
	GoogleMap map = null;
	String initialize = null;
	String route = null;

	String routeName = null;
	String sourceAddress = null;
	LatLng sourceLatLng = null;
	String destAddress = null;
	LatLng destLatLng = null;
	
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
		
		getRoute();
		checkMap();
	}
	
	private void getRoute()
	{
		Bundle b = getIntent().getExtras();
		route = b.getString("route","");
		
		String[] aux = route.split("\n");
		
		routeName = aux[0];
		sourceAddress = aux[1];
		sourceLatLng = new LatLng(Double.parseDouble(aux[2].substring(0, aux[2].indexOf(","))), Double.parseDouble(aux[2].substring(aux[2].indexOf(",")+1)));
		destAddress = aux[3];
		destLatLng = new LatLng(Double.parseDouble(aux[4].substring(0, aux[4].indexOf(","))), Double.parseDouble(aux[4].substring(aux[4].indexOf(",")+1)));
		
		if (!route.equals(""))
		{
			initialize = "";
			if (aux.length>5)
			{
				for (int i=5; i<aux.length; i++)
				{
					initialize += aux[i] + "\n";
				}
			}
		}
		
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
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(getMapInicialLatLng(), getMapInicialZoom()));
		map.setMyLocationEnabled(true);
		map.setOnInfoWindowClickListener(this);
		
		UiSettings mapSettings = map.getUiSettings();
        mapSettings.setTiltGesturesEnabled(false);
        mapSettings.setRotateGesturesEnabled(false);
        mapSettings.setCompassEnabled(true);

		addCoupons();
		
		if (initialize != null && !initialize.equals(""))
		{
			parseInitialize();
		}
		else
		{
			fakeInitialize();
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
		if (aux1==0 || aux2==0)
		{
			aux1 = (float) -22.81723403930664;
			aux2 = (float) -47.06974411010742;
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
			else if (init[i].contains("imagemA"))
			{
				String aux = init[i-1];
				String lat = aux.substring(aux.indexOf("(")+1,aux.indexOf(","));
				String lng = aux.substring(aux.indexOf(",")+1,aux.indexOf(")"));
				LatLng latlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
				BitmapDescriptor markerImage = BitmapDescriptorFactory.fromResource(R.drawable.point_a); 
				MarkerOptions marker = new MarkerOptions().position(latlng).icon(markerImage);
				String title = "Partida";
				String snippet = sourceAddress;
				marker.title(title);
				marker.snippet(snippet);
				map.addMarker(marker);
			}
			else if (init[i].contains("imagemB"))
			{
				String aux = init[i-1];
				String lat = aux.substring(aux.indexOf("(")+1,aux.indexOf(","));
				String lng = aux.substring(aux.indexOf(",")+1,aux.indexOf(")"));
				LatLng latlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
				BitmapDescriptor markerImage = BitmapDescriptorFactory.fromResource(R.drawable.point_b); 
				MarkerOptions marker = new MarkerOptions().position(latlng).icon(markerImage);
				String title = "Destino";
				String snippet = destAddress;
				marker.title(title);
				marker.snippet(snippet);
				map.addMarker(marker);
			}
			else if (init[i].contains("ImagemOnibus"))
			{
				String aux = init[i-1];
				String lat = aux.substring(aux.indexOf("(")+1,aux.indexOf(","));
				String lng = aux.substring(aux.indexOf(",")+1,aux.indexOf(")"));
				LatLng latlng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
				BitmapDescriptor markerImage = BitmapDescriptorFactory.fromResource(R.drawable.icon_bus); 
				MarkerOptions marker = new MarkerOptions().position(latlng).icon(markerImage);
				aux = init[i+2];
				String title = aux.substring(aux.indexOf("<strong>")+8,aux.indexOf("</strong>"));
				String snippet = "";
				if (aux.contains("Desembarque"))snippet = aux.substring(aux.indexOf("</strong>")+9,aux.indexOf("<tr>",aux.indexOf("</strong>")+9));
				else if (aux.contains("Linha")) snippet = aux.substring(aux.indexOf("</strong>")+9,aux.indexOf("</td>",aux.indexOf("</strong>")+9));
				if (snippet.contains("ALTURA"))	snippet = snippet.replace("ALTURA"," ");
				if (snippet.contains("REF.:"))	snippet = snippet.substring(0,snippet.indexOf("REF.:"));
				while (snippet.contains("  "))	snippet = snippet.replace("  "," ");
				marker.title(title);
				marker.snippet(snippet);
				map.addMarker(marker);
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
				lines[(i-1)/3].width(6);
				if (aux.contains("strokeColor:"))
				{
					aux = aux.substring(aux.indexOf("strokeColor: ")+15);
					aux = aux.substring(0, aux.indexOf("'"));
					aux = "#88"+aux;
					lines[i/3].color(Color.parseColor(aux));
				}
			}
			if (i%3==2)
			{
				map.addPolyline(lines[(i-2)/3]);
			}
		}
	}
	
	private void fakeInitialize()
	{
		PolylineOptions fakeLine = new PolylineOptions();
		fakeLine.color(Color.parseColor("#88FF0000"));
		fakeLine.width(6);
		map.addPolyline(fakeLine);
	}

	private void addCoupons()
	{
		System.out.println("Pegando cupons");
		ArrayList<ArrayList<String>> coupons = CouponManager.getActiveCoupons();
		for (int i=0; i<coupons.size(); i++)
		{
			//System.out.println(coupons.get(i).get(0));
			String latlngaux = coupons.get(i).get(2);
			LatLng latlng = new LatLng(Double.parseDouble(latlngaux.substring(0, latlngaux.indexOf(","))),Double.parseDouble(latlngaux.substring(latlngaux.indexOf(",")+1)));
			
			System.out.println(coupons.get(i).get(0)+", dist: "+distance(latlng, destLatLng));
			
			BitmapDescriptor markerImage = BitmapDescriptorFactory.fromResource(R.drawable.icon_coupon); 
			MarkerOptions aux = new MarkerOptions();
			aux.position(latlng);
			aux.snippet(coupons.get(i).get(1));
			aux.title(coupons.get(i).get(0));
			aux.icon(markerImage);
			map.addMarker(aux);
		}
	}
	
	private double distance(LatLng l1, LatLng l2)
	{
		double d = 0;
		
		d += Math.pow(l1.latitude-l2.latitude,2);
		d += Math.pow(l1.longitude-l2.longitude,2);
		
		d = Math.sqrt(d);
		
		return d;
	}

	@Override
	public void onInfoWindowClick(Marker marker)
	{
		System.out.println("Clicou - info");
		String title = marker.getTitle();
		String coupon = marker.getSnippet();
		if (CouponManager.addCoupon(title,title+"\n"+coupon))
		{
			Toast.makeText(getApplicationContext(), "Cupom salvo com sucesso", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Erro ao adicionar o cupom - pode já ter sido salvo", Toast.LENGTH_SHORT).show();
		}
	}
}
