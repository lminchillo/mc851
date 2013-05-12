package lais.mc851;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AddressDialog extends DialogFragment
{
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
	{
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Question goes here")
               .setPositiveButton(R.string.app_name, new DialogInterface.OnClickListener()
               {
                   public void onClick(DialogInterface dialog, int id)
                   {
                       // FIRE ZE MISSILES!
                   }
               })
               .setNegativeButton(R.string.startscreen_my_coupons, new DialogInterface.OnClickListener()
               {
                   public void onClick(DialogInterface dialog, int id)
                   {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
