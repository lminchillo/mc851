package lais.mc851;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent callingIntent) {
		Intent intent = new Intent(context, CouponService.class);
		context.startService(intent);
		
		Log.d("BootReceiver", "onReceivedDone");
	}
}