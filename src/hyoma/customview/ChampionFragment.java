package hyoma.customview;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
// This class will create a fragment from the global g_champions ArrayList declared in
// DownloadPage. This means that DownloadPage MUST BE RUN AT LEAST ONCE before this code
// can function. 
//;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

public class ChampionFragment extends ListFragment {
	// private members
	
	// ******************************************************************************************
	// THIS IS A HACK - The array list for champion information is static in DownloadPage in order
	// for this file to have access to it WITHOUT sending it through the bundle. THIS MAY NOT WORK
	// AS EXPECTED. WATCH TO SEE WHAT HAPPENS! Problems may arise due to threading.
	private ArrayList<ChampionInformation> m_champs = DownloadPage.g_champions;
	
	public ChampionFragment(){}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        // Before continuing, check to see if m_champs has anything in it. If not,
        // throw an exception and do NOT allow rest of the code to run. 
        if(m_champs.size() == 0)
        {
        	// THROW PROPER EXCEPTION HERE
        }
        
        // Create a string array of the champion names
        String[] names = new String[m_champs.size()];
        for(int i = 0; i < m_champs.size(); i++){
        	names[i] = m_champs.get(i).getName();
        }
        // Set the list adapter to hold the strings.
        // *******************************************
        // This is what needs to be changed to allow customization of the layouts. 
        // *******************************************
        
        ChampionViewAdaptor mainAdaptor = new ChampionViewAdaptor(getActivity(), R.layout.champion_list, m_champs);
        setListAdapter(mainAdaptor);
        //setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.champion_list, names));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    	//Toast.makeText(this, "Tapped champions", Toast.LENGTH_SHORT).show();
    }
    
    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
    //{
    //    View v1 = inflater.inflate(R.layout.fragment_pager_list, container, false);
    //    TextView tv = (TextView) v1.findViewById(R.id.text);
    //    tv.setText("Fragment #" + mNum);
    //    mListView = (ListView) v1.findViewById(android.R.id.list);
    //    return v1;
    //}
	
}