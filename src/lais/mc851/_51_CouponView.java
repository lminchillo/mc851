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
	private Button delete;
	
	private String savedName = null;
	private String savedDescription = null;
	private TextView couponName = null;
	private TextView couponDescription = null;
	
	@Override
	public void onBackPressed()
	{
		startActivity(new Intent(getApplicationContext(),_5_Coupons.class));
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coupon_view);
		
		initialize();
		initializeButtons();
	}
	
	private void initialize()
	{
		Bundle b = getIntent().getExtras();
		
		savedName = b.getString("couponName", "");
		savedDescription = b.getString("couponDescription", "");
		
		couponName = (TextView) findViewById(R.id.coupons_details_text_view_title);
		couponName.setText(savedName);
		
		couponDescription = (TextView) findViewById(R.id.coupons_details_text_view_content);
		couponDescription.setText(savedDescription);
	}
	
	private void initializeButtons()
	{
		delete = (Button) findViewById(R.id.coupons_details_button_delete);
		delete.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (CouponManager.removeCoupon(savedName))
				{
					Toast.makeText(getApplicationContext(), "Cupom deletado", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(getApplicationContext(), _5_Coupons.class));		
					finish();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Erro ao deletar o cupom", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
}
