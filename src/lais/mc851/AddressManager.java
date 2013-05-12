package lais.mc851;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AddressManager
{
	private static SharedPreferences pref = null;
	private static Editor editor = null;
	
	public AddressManager(SharedPreferences sp, Editor e)
	{
		pref = sp;
		editor = e;
	}
	
	public static ArrayList<String> getAddressList()
	{
		ArrayList<String> res = new ArrayList<String>();
		String aux = "next";
		for (int i=1; !aux.equals("end"); i++)
		{
			aux = pref.getString("savedAddress"+i, "end");
			if (!aux.equals("next") && !aux.equals("end"))
			{
				res.add(aux);
			}
		}
		System.out.println(res.toString());
		return res;
	}
	
	private static String findPosition()
	{
		String res = null;
		String aux = " ";
		for (int i=1; !aux.equals("end"); i++)
		{
			aux = pref.getString("savedAddress"+i, "end");
			if (aux.equals("end"))
			{
				res = "savedAddress"+i;
			}
		}
		return res;
	}
	
	public static void addAddress(String address)
	{
		editor.putString(findPosition(), address);
		editor.commit();
	}
	
	private static String findKey(String address)
	{
		String res = null;
		String aux = " ";
		for (int i=1; (!aux.equals("end") && !aux.equals(address)); i++)
		{
			aux = pref.getString("savedAddress"+i, "end");
			if (aux.equals(address))
			{
				res = "savedAddress"+i;
			}
		}
		return res;
	}
	
	public static void removeAddress(String address)
	{
		editor.putString(findKey(address), "next");
		editor.commit();
	}
}
