package hyoma.customview;

import android.app.ProgressDialog;


class ParserException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProgressDialog m_progressDialog = null;

	public ParserException() {}

      //Constructor that accepts a message
      public ParserException(String message){
         super(message);
      }
      public ParserException(ProgressDialog dialog){
    	  m_progressDialog = dialog;
       }
      
      public ProgressDialog getDialog(){
    	  return m_progressDialog;
      }
 }