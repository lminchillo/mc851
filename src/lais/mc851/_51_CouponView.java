package lais.mc851;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class _51_CouponView extends Activity 
{
	private Button buttonDelete;
	
	private String couponName = null;
	private String couponDescription = null;
	private TextView textViewName = null;
	private TextView textViewDescription = null;
	private boolean savedCoupon = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_view);

		Bundle b = getIntent().getExtras();
		if (!b.getString("couponName", "").equals("") && !b.getString("couponDescription", "").equals(""))
		{
			savedCoupon = true;
			couponName = b.getString("couponName", "");
			couponDescription = b.getString("couponDescription", "");
			System.out.println("name: " + couponName);
			System.out.println("description: " + couponDescription);
		}
		
		initializeWidgets();
	}
	
	private void initializeWidgets()
	{
		textViewName = (TextView) findViewById(R.id.coupons_details_text_view_title);
		textViewName.setText(couponName);
		
		textViewDescription = (TextView) findViewById(R.id.coupons_details_text_view_content);
		textViewDescription.setText(couponDescription);
		
		buttonDelete = (Button) findViewById(R.id.coupons_details_button_delete);
		buttonDelete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(savedCoupon){
						
						if (CouponManager.removeCoupon(couponName))
						{
							Toast.makeText(getApplicationContext(), "Cupom deletado", Toast.LENGTH_SHORT).show();
							startActivity(new Intent(getApplicationContext(),_5_Coupons.class));		
							finish();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Erro ao deletar o cupom", Toast.LENGTH_SHORT).show();
						}
					}
				else
				{
					Toast.makeText(getApplicationContext(), "Esse cupom não foi salvo ainda", Toast.LENGTH_SHORT).show();
				}
					
			}
		});
	}

}
