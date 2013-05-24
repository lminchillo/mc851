package lais.mc851;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class CouponManager
{
	private static final String url = "http://www.students.ic.unicamp.br/~ra102986/MC851/";
	
	public CouponManager()
	{
		viewCoupons();
	}
	
	public static void viewCoupons()
	{
		try
		{
			Document document = Jsoup.connect(url).get();
			String[][] coupons = new String[document.getElementsByTag("a").size()][];
			int i=0,j=0;
	    	for (Node n : document.getElementsByTag("a"))
	    	{
	    		//System.out.println(n.attr("item").toString());
	    		String[] aux = n.attr("item").toString().split("@");
	    		coupons[i] = new String[4];
	    		for (String s : aux)
	    		{
	    			//System.out.println(s);
	    			coupons[i][j] = s;
	    			j++;
	    		}
	    		i++; j=0;
	    	}
	    	
	    	/*for (i=0;i<coupons.length;i++)
	    	{
	    		System.out.println(coupons[i][0]);
	    		System.out.println(coupons[i][1]);
	    		System.out.println(coupons[i][2]);
	    		System.out.println(coupons[i][3]);
	    	}*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
