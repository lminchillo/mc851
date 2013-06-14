package lais.mc851;

import android.app.IntentService;
import android.util.Log;
import android.content.Intent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CouponService extends IntentService {
	private final String url = "http://www.students.ic.unicamp.br/~ra102986/MC851/";
	private final int tempoEspera = 30*1000;
	
	public CouponService() {
		super("CouponService");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		boolean firstTime = true;
		while(true) {
			if (!firstTime) {
				long endTime = System.currentTimeMillis() + this.tempoEspera;
	    
				while (System.currentTimeMillis() < endTime) {
					synchronized (this) {
						try {
							wait(endTime - System.currentTimeMillis());
						} catch (Exception e) {}
					}
				}
			}
			
			try {
				Document document = Jsoup.connect(url).get();
				CouponManager.updateCoupons(document);
				Log.d("CouponService", "Coupons fetched!");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			firstTime = false;
		}
	}
}