package lais.mc851;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

public class AddressEdit extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_edit);
	}
	
	private void showDialog()
	{
		LayoutInflater factory = LayoutInflater.from(this);
	    final View textEntryView = factory.inflate(R.layout.alert_dialog_address, null);
		Dialog d = new AlertDialog.Builder(AddressEdit.this)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("title")
        .setView(textEntryView)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                EditText et = (EditText) textEntryView.findViewById(R.id.username_edit);
                Toast.makeText(getApplicationContext(), et.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
            	
            }
        })
        .create();
		d.show();
	}
}
