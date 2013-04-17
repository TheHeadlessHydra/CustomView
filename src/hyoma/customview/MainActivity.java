package hyoma.customview;

// local includes
import java.util.EmptyStackException;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;

public class MainActivity extends Activity {
	// private members
	private boolean m_firstRun = true;
	private Handler mhandler = new Handler();
	private DownloadPage m_downloadTask = new DownloadPage(this, mhandler);
	
	// A function that checks if the network is available or not. 
	public boolean checkNetwork(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		        // fetch data
		} else {
			return false;
		        // display error
		}
	}

	// An async task that will call the DownloadPage class and do all the downloading
	// on a separate thread. 
	public class DownloadChampions extends AsyncTask< Void, Void, Void > {

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try{
				m_downloadTask.downloadChampionPage();
			}
			catch(ParserException exc){
				final ProgressDialog dialog = exc.getDialog();
				// If the dialog is not null, dismiss it. 
				if(dialog != null){
					mhandler.post(new Runnable(){
						public void run(){
							dialog.dismiss();
						}
					});
				}
				// Display message to user to inform them that the app is currently not working. 
		        ParserAlertDialog pDialog = new ParserAlertDialog();
		        FragmentManager fragmentManager = getFragmentManager();
		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		        fragmentTransaction.add(pDialog, "@string/parser_error_dialog");
		        fragmentTransaction.commit();
		        pDialog.show(fragmentManager,"@string/parser_error_dialog");
			}
			catch(ConnectionException exc){
				final ProgressDialog dialog = exc.getDialog();
				// If the dialog is not null, dismiss it. 
				if(dialog != null){
					mhandler.post(new Runnable(){
						public void run(){
							dialog.dismiss();
						}
					});
				}
				// Display message to user to inform them that the app is currently not working. 
		        ConnectionAlertDialog pDialog = new ConnectionAlertDialog();
		        FragmentManager fragmentManager = getFragmentManager();
		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		        fragmentTransaction.add(pDialog, "@string/connection_error_dialog");
		        fragmentTransaction.commit();
		        pDialog.show(fragmentManager,"@string/connection_error_dialog");
			}
			
			return null;
	     }

		@Override
	     protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			// make sure the progress dialog is gone before continuing.
			if (DownloadPage.m_progressDialog != null){
				DownloadPage.m_progressDialog.dismiss();
			}
			
			// Once the async task is done, you can create the fragment with the downloaded elements and display it
			// on the app.
			/// **************************************************
			// HERE, this part may also need to be done on a different thread, since the ChampionFragment creation
			// may be costly. THINK ABOUT IT a bit then decide if this is needed. 
			/// **************************************************
			ChampionFragment championPage = new ChampionFragment();
        	getFragmentManager().beginTransaction().add(R.id.fragment_container, championPage).commit();
	     }

	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


	    // Create the warning dialog on startup
	    //NetworkAlertDialog dialog = new NetworkAlertDialog();
	    //FragmentManager fragmentManager = getFragmentManager();
	    //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    //fragmentTransaction.add(dialog, "@string/network_warning_dialog");
	    //fragmentTransaction.commit();
	    //dialog.show(fragmentManager,"@string/network_warning_dialog");
        
        setContentView(R.layout.activity_main);   
    }
    
    @Override
    public void onPause() {
        super.onPause();
        
        NetworkAlertDialog diag = (NetworkAlertDialog) getFragmentManager().findFragmentByTag("@string/network_warning_dialog");
        if(diag != null){
        	getFragmentManager().beginTransaction().remove(diag).commit();
        }
        	
    }
    
    public void onStop() {
        super.onStop();
        
        NetworkAlertDialog diag = (NetworkAlertDialog) getFragmentManager().findFragmentByTag("@string/network_warning_dialog");
        if(diag != null){
        	getFragmentManager().beginTransaction().remove(diag).commit();
        }
        	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.champions_button:
            	// If the champions button is selected, create the download champions task
            	// and execute it in another thread. 
            	DownloadChampions championAsync = new DownloadChampions();
            	championAsync.execute();
            	Toast.makeText(this, "Tapped champions", Toast.LENGTH_SHORT).show();   
                break;

            case R.id.items_button:
                Toast.makeText(this, "Tapped items", Toast.LENGTH_SHORT).show();
                break;

            case R.id.shop_button:
                Toast.makeText(this, "Tapped shop", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}

