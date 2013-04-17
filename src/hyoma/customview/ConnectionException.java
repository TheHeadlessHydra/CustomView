package hyoma.customview;

import android.app.ProgressDialog;


class ConnectionException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProgressDialog m_progressDialog = null;
	
	public ConnectionException() {}

      //Constructor that accepts a message
      public ConnectionException(String message){
         super(message);
      }
      
      public ConnectionException(ProgressDialog dialog){
    	  m_progressDialog = dialog;
       }
      
      public ProgressDialog getDialog(){
    	  return m_progressDialog;
      }
 }