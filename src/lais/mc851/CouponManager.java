package lais.mc851;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class CouponManager
{
	private static SharedPreferences pref = null;
	private static Editor editor = null;
	public static final String COUPON_KEY = "COUPONS_PREFS_HTML";
	
	public CouponManager(SharedPreferences sp, Editor e)
	{
		pref = sp;
		editor = e;
	}
	
	public static void updateCouponDatabase(Document d)
	{
		editor.putString(COUPON_KEY, d.html());
		editor.commit();
	}
	
	public static ArrayList<ArrayList<String>> getActiveCoupons()
	{
		try
		{
			Document document = new Document(pref.getString(COUPON_KEY, ""));
			ArrayList<ArrayList<String>> coupons = new ArrayList<ArrayList<String>>();
			
			int i = 0;
			for (Node n : document.getElementsByTag("a"))
			{
	    		String[] aux = n.attr("item").toString().split("@");
	    		coupons.add(new ArrayList<String>(4));
	    		
	    		for (String s : aux)
	    		{
	    			coupons.get(i).add(s);
	    		}
	    		
	    		i++;
	    	}
			
			return coupons;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<String> getActiveCoupon(String name)
	{
		try
		{
			Document document = new Document(pref.getString(COUPON_KEY, ""));
	    	ArrayList<String> coupon = new ArrayList<String>(4);
			
			for (Node n : document.getElementsByTag("a"))
			{
	    		String[] aux = n.attr("item").toString().split("@");
	    		
	    		if (aux[0] == name)
	    		{
	    			for (String s : aux)
	    			{
		    			coupon.add(s);
		    		}
	    			
	    			return coupon;
	    		}
	    	}
			
			return coupon;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}