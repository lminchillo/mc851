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
	
	public static boolean addCoupon(String couponName, String coupon)
	{
		if (findKey(couponName) == null)
		{
			editor.putString(findEmptyPosition(), coupon);
			editor.commit();
			return true;
		}
		return false;
	}
	
	public static boolean removeCoupon(String couponName)
	{
		String key = findKey(couponName);
		if (key != null)
		{
			editor.putString(key, "next");
			editor.commit();
			return true;
		}
		return false;
	}
	
	public static ArrayList<String> getCouponList()
	{
		ArrayList<String> res = new ArrayList<String>();
		String aux = "next";
		for (int i=1; !aux.equals("end"); i++)
		{
			aux = pref.getString("savedCoupon"+i, "end");
			if (!aux.equals("next") && !aux.equals("end"))
			{
				res.add(aux);
			}
		}
		return res;
	}
	
	private static String findKey(String couponName)
	{
		String res = null;
		String aux = " ";
		for (int i=1; (!aux.equals("end") && !aux.equals(couponName)); i++)
		{
			aux = pref.getString("savedCoupon"+i, "end");
			if (!aux.equals("end") && aux.contains("|"))
			{
				aux = aux.substring(0, aux.indexOf("|"));
			}
			if (aux.equals(couponName))
			{
				res = "savedCoupon"+i;
			}
		}
		return res;
	}
	
	private static String findEmptyPosition()
	{
		String res = null;
		String aux = " ";
		for (int i=1; !aux.equals("end") && !aux.equals("next"); i++)
		{
			aux = pref.getString("savedCoupon"+i, "end");
			if (aux.equals("end") || aux.equals("next"))
			{
				res = "savedCoupon"+i;
			}
		}
		return res;
	}
}