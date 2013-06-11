package lais.mc851;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class RouteManager
{
	private static SharedPreferences pref = null;
	private static Editor editor = null;
	private static final String tag = "savedRoute";
	private static final String tagNext = "next";
	private static final String tagEnd = "end";
	
	public RouteManager(SharedPreferences sp, Editor e)
	{
		pref = sp;
		editor = e;
	}
	
	public static ArrayList<String> getRouteList()
	{
		ArrayList<String> res = new ArrayList<String>();
		String aux = tagNext;
		for (int i=1; !aux.equals(tagEnd); i++)
		{
			aux = pref.getString(tag+i, tagEnd);
			if (!aux.equals(tagNext) && !aux.equals(tagEnd))
			{
				res.add(aux);
			}
		}
		return res;
	}
	
	private static String findEmptyPosition()
	{
		String res = null;
		String aux = " ";
		for (int i=1; !aux.equals(tagEnd) && !aux.equals(tagNext); i++)
		{
			aux = pref.getString(tag+i, tagEnd);
			if (aux.equals(tagEnd) || aux.equals(tagNext))
			{
				res = tag+i;
			}
		}
		return res;
	}
	
	public static boolean addRoute(String routeName, String route)
	{
		if (findKey(routeName) == null)
		{
			editor.putString(findEmptyPosition(), route);
			editor.commit();
			return true;
		}
		return false;
	}
	
	public static boolean removeAddress(String addressName)
	{
		String key = findKey(addressName);
		if (key != null)
		{
			editor.putString(key, tagNext);
			editor.commit();
			return true;
		}
		return false;
	}
	
	private static String findKey(String addressName)
	{
		String res = null;
		String aux = " ";
		for (int i=1; (!aux.equals(tagEnd) && !aux.equals(addressName)); i++)
		{
			aux = pref.getString(tag+i, tagEnd);
			if (!aux.equals(tagEnd) && aux.contains("\n"))
			{
				aux = aux.substring(0, aux.indexOf("\n"));
			}
			if (aux.equals(addressName))
			{
				res = tag+i;
			}
		}
		return res;
	}
}
