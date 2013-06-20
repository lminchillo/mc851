package lais.mc851;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

public class GPSManager
{
	private static Context context = null;
	
	public GPSManager(Context ctx)
	{
		context = ctx;
	}
	
	public static List<String> getLocation(String address)
    {
    	try
    	{
    		Geocoder g = new Geocoder(context);
        	List<Address> a = g.getFromLocationName(address, 1);
        	List<String> ret = new ArrayList<String>(2);
        	
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
        	
        	ret.add(value.replaceFirst(" - ", ""));
        	ret.add(latlng);
        	
        	return ret;
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    }
	
	public static Location getLastBestLocation()
    {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                
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
	
	public static List<String> getAddress(Location l)
    {
    	try
    	{
    		Geocoder g = new Geocoder(context);
        	Address a = g.getFromLocation(l.getLatitude(), l.getLongitude(), 1).get(0);
        	
        	String value = "";
        	String latlng = "";
        	List<String> ret = new ArrayList<String>(2);
        	
        	for (int i = 0; i<a.getMaxAddressLineIndex(); i++)
        	{
        		 value += " - " + a.getAddressLine(i);
        	}
    		
        	latlng += a.getLatitude() + "," + a.getLongitude();
        	
        	ret.add(value.replaceFirst(" - ", ""));
        	ret.add(latlng);
        	
        	return ret;
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    }
	
	public static String getStreetAddress(String address)
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
	
	public static String getLatLng(String address)
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
}