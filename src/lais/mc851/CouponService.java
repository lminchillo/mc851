package lais.mc851;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class CouponService extends IntentService
{
	private final String url = "http://www.students.ic.unicamp.br/~ra102986/MC851/";
	private final int waitTime = 30*1000;
	
	public CouponService()
	{
		super("CouponService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent)
	{
		boolean firstTime = true;
		while(true)
		{
			if (!firstTime)
			{
				long endTime = System.currentTimeMillis() + this.waitTime;
	    
				while (System.currentTimeMillis() < endTime)
				{
					synchronized (this)
					{
						try
						{
							wait(endTime - System.currentTimeMillis());
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			
			try
			{
				Document document = Jsoup.connect(url).get();
				CouponManager.updateCouponDatabase(document);
				Log.d("CouponService", "Coupons fetched!");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			firstTime = false;
		}
	}
}