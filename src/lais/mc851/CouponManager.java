package lais.mc851;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import java.util.ArrayList;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CouponManager {
	public static final String COUPON_KEY = "COUPONS_PREFS_HTML";
	
	public CouponManager() {}
	
	public static void updateCoupons(Document d) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(QualBusao.getAppContext());
		pref.edit().putString(COUPON_KEY, d.html()).commit();
	}
	
	public static ArrayList<ArrayList<String>> getAllCoupons() {
		try {		
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(QualBusao.getAppContext());
			Document document = new Document(pref.getString(COUPON_KEY, ""));
			ArrayList<ArrayList<String>> coupons = new ArrayList<ArrayList<String>>();
			
			int i = 0;
			for (Node n : document.getElementsByTag("a")) {
	    		String[] aux = n.attr("item").toString().split("@");
	    		coupons.add(new ArrayList<String>(4));
	    		
	    		for (String s : aux) {
	    			coupons.get(i).add(s);
	    		}
	    		
	    		i++;
	    	}
			
			return coupons;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static ArrayList<String> getCoupon(String name) {
		try {
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(QualBusao.getAppContext());
			Document document = new Document(pref.getString(COUPON_KEY, ""));
	    	ArrayList<String> coupon = new ArrayList<String>(4);
			
			for (Node n : document.getElementsByTag("a")) {
	    		String[] aux = n.attr("item").toString().split("@");
	    		
	    		if (aux[0] == name) {
	    			for (String s : aux) {
		    			coupon.add(s);
		    		}
	    			
	    			return coupon;
	    		}
	    	}
			
			return coupon;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}