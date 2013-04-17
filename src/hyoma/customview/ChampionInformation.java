package hyoma.customview;

import java.util.ArrayList;

import android.graphics.Bitmap;

// Class to hold all the information needed of a champion in order
// to represent it later. 
public class ChampionInformation{
		
	// Private variables
	String m_sChampionName = "";
	String m_sChampionNameDescription = "";
	String m_sChampionDescription = "";
	String m_sChampionTags = "";
	Bitmap m_img = null;
		
	// Constructors
	public ChampionInformation(){}
	public ChampionInformation(String name, String nameDesc, String description, String tags, Bitmap img){
		m_sChampionName = name;
		m_sChampionNameDescription = nameDesc;
		m_sChampionDescription = description;
		m_sChampionTags = tags;
		m_img = img;
	}
		
	// Setters and getters
	// Name
	public String getName(){
		return m_sChampionName;
	}
	public void setName(String name){
		m_sChampionName = name;
	}
	// Name Description
	public String getNameDescription(){
		return m_sChampionNameDescription;
	}
	public void setNameDescription(String nameDesc){
		m_sChampionNameDescription = nameDesc;
	}
	// Description
	public String getDescription(){
		return m_sChampionDescription;
	}
	public void setDescription(String description){
		m_sChampionDescription = description;
	}
	// Tags
	public String getTags(){
		return m_sChampionTags;
	}
	public void setTags(String tags){
		m_sChampionTags = tags;
	}		
	// Image
	public Bitmap getBitmap(){
		return m_img;
	}
	public void setBitmap(Bitmap img){
		m_img = img;
	}	
}