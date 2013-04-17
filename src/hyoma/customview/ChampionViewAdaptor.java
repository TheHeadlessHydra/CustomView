package hyoma.customview;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChampionViewAdaptor extends ArrayAdapter<ChampionInformation> {

        private ArrayList<ChampionInformation> m_champs;

        public ChampionViewAdaptor(Context context, int textViewResourceId, ArrayList<ChampionInformation> champs) {
                super(context, textViewResourceId, champs);
                m_champs = champs;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                if (v == null) {
                    LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.champion_list, null);
                }
                ChampionInformation champ = m_champs.get(position);
                if (champ != null) {
                	String championName = champ.getName();
                	String championNameDesc = champ.getNameDescription();
                	
                	ImageView iv = (ImageView) v.findViewById(R.id.champion_image);
                	TextView tt = (TextView) v.findViewById(R.id.toptext);
                    TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                    if (tt != null) {
                    	// Here we set the name of the champion to be "NAME, NAME_DESC
                    	// where NAME_DESC is cleaned up html. 
                    	tt.setText(championName + ", " + championNameDesc.substring(0, championNameDesc.length()-championName.length() - 2) );
                    }
                    if(bt != null){
                    	bt.setText(champ.getDescription());
                    }
                    if(iv != null){
                    	iv.setImageBitmap( champ.getBitmap() );
                    }
                }
                return v;
        }
}