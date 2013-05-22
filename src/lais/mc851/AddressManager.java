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
				int j=1;
				while (pref.getString("savedAddress"+(i-j), "end").equals("next"))
				{
					j++;
					res = "savedAddress"+(i-j);
				}
			}
		}
		return res;
	}
	
	public static boolean addAddress(String addressName, String address)
	{
		if (findKey(addressName) == null)
		{
			editor.putString(findPosition(), address);
			editor.commit();
			return true;
		}
		return false;
	}
	
	public static boolean removeAddress(String addressName, String address)
	{
		if (findKey(addressName) != null)
		{
			editor.putString(findKey(address), "next");
			editor.commit();
			return true;
		}
		return false;
	}
	
	private static String findKey(String addressName)
	{
		String res = null;
		String aux = " ";
		for (int i=1; (!aux.equals("end") && !aux.equals(addressName)); i++)
		{
			aux = pref.getString("savedAddress"+i, "end");
			if (!aux.equals("end") && aux.contains("\n"))
			{
				aux = aux.substring(0, aux.indexOf("\n"));
			}
			if (aux.equals(addressName))
			{
				res = "savedAddress"+i;
			}
		}
		return res;
	}
}
