package hyoma.customview;

// local imports
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

// A class that will set up a warning dialogue to the user 
public class ConnectionAlertDialog extends DialogFragment {
	private DialogFragment m_thisFrag = this;
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.connection_parser)
               .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       dialog.dismiss();
                       getFragmentManager().beginTransaction().remove(m_thisFrag).commit();
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}