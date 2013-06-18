package lais.mc851;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CouponView extends Activity 
{
	private Button buttonDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_view);

		initializeButtons();
	}
	
	private void initializeButtons()
	{
		buttonDelete = (Button) findViewById(R.id.coupons_details_button_delete);
		buttonDelete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				
			}
		});
	}

}
