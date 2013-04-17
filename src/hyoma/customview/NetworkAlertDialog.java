package hyoma.customview;

// local imports
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

// A class that will set up a warning dialogue to the user 
public class NetworkAlertDialog extends DialogFragment {
	private DialogFragment m_fragment = this;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fire_missiles)
               .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                	   FragmentManager fragmentManager = getFragmentManager();
                	   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                	   fragmentTransaction.remove(m_fragment);
                	   fragmentTransaction.commit();
                   }
               }).setNegativeButton(R.string.alert_dialog_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	getActivity().finish();
                    }
                }
            );
        // Create the AlertDialog object and return it
        return builder.create();
    }
}