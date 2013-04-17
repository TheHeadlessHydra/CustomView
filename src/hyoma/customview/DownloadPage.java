package hyoma.customview;

//jsoup includes
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Handler;


//;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
// This class holds two main functions: downloadChampionPage() and downloadItemPage()
// These two functions will connect to the Internet, download the files required,
// show a progress bar, and create the list of all the champions/items.
//
// This is a lengthy procedure and should NOT be called on the main UI thread.
// You should make the call to this class outside the main UI thread.
//;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


public class DownloadPage {
	// Global variables
	public static ArrayList<ChampionInformation> g_champions = new ArrayList<ChampionInformation>(); // Holds the list of champions
	
	// Private variables
	public static ProgressDialog m_progressDialog = null;
	private int m_iMaxCount = 0;
	private Activity m_mainActivity = null;
	private Handler m_mainThreadHandler = null; 
	
	// Constructor
	public DownloadPage(Activity activity, Handler handler){
		m_mainActivity = activity;
		m_mainThreadHandler = handler;
	}
	
	// function to call if downloading the item pages.
	// *********************************************************************************
	public void downloadChampionPage() throws ConnectionException, ParserException{
		
		// Access the main UI thread and start the progress dialog.
		m_mainThreadHandler.post(new Runnable(){
			public void run(){
				m_progressDialog = ProgressDialog.show(m_mainActivity, "Downloading...","We are now downloading the champions... Please wait...", true);
			}
		});
		
		// Access the web page and try to download it. 
		Document doc = null;
		try{
				doc = Jsoup.connect("http://na.leagueoflegends.com/champions").get();
		}
		catch(IOException exc){
			// Could not connect to the page
			throw new ConnectionException(m_progressDialog);
		}
		
		// Check to see if the document was able to download anything.
		if(doc != null){
			// Parse through and find all the names of each champion. 
			Elements names = doc.select("div.champion_name");
			Elements descriptions = doc.select("p");
			Elements name_descs = doc.select("span.search_text");
			Elements image_links = doc.select("img[src]");			
			
			// Check if jsoup was able to parse anything from the page. If the website changes their
			// html layout, this exception may be thrown.
			if(names.size() == 0 || descriptions.size() == 0 || name_descs.size() == 0 ){
				throw new ParserException(m_progressDialog);
			}
			else{
				// Set the max size for the progress bar.
				m_iMaxCount = names.size();
				m_mainThreadHandler.post(new Runnable(){
					public void run(){
						m_progressDialog.setMax(m_iMaxCount);
					}
				});
				
				// Go through each champion in the parsed list and add them to the global champion list. 
				// Also, increment the progress. 
				for (int i = 0; i < m_iMaxCount; i++) {
					Element name = names.get(i);
					Element description = descriptions.get(i);
					Element name_desc = name_descs.get(i);
					String img_html = image_links.get(i).attr("src");
					
					// Download the image
					Bitmap img = DownloadBitmap.downloadBitmap(img_html);
					
					ChampionInformation nextChamp = new ChampionInformation(name.ownText(), name_desc.ownText(), description.ownText(), "", img);
					g_champions.add(nextChamp);
					
					m_mainThreadHandler.post(new Runnable(){
						public void run(){
							m_progressDialog.incrementProgressBy(1);
						}
					});
				}
			}
		}
		// If the doc is null, it was not able to download anything from the webpage
		else{
			throw new ConnectionException(m_progressDialog);
		}
		
		// Access the main UI thread and close the progress dialog. 
		m_mainThreadHandler.post(new Runnable(){
			public void run(){
				m_progressDialog.dismiss();
			}
		});

		// If this is empty, we downloaded and parsed nothing.
		if(g_champions.size() == 0){
			throw new ParserException();
		}
	}
	
	// function to call if downloading the item pages.
	public void downloadItemPage(){
		
	}
}
	