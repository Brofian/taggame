package de.Brogamer5000.tagGame.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class tagStorage {

	private File historyFile;
	private File tabooFile;
	public List<String> tabooList = new ArrayList<String> ();
	public List<String> historyList = new ArrayList<String> ();
	
///////////////////////////////////////////////////////////////////////
////....................Konstruktor Funktion.......................////
///////////////////////////////////////////////////////////////////////
	
	
	public tagStorage(File history, File taboo) {
		
		this.historyFile = history;
		this.tabooFile = taboo;
		
	
		//test if file exists
		if(this.historyFile.exists() == false || this.tabooFile.exists() == false) {
			//create the File
			try {
				this.historyFile.createNewFile();
				this.tabooFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
///////////////////////////////////////////////////////////////////////
////....................Save und Load Funktionen...................////
///////////////////////////////////////////////////////////////////////
	
	public void load() {
		
		try {
			//load current tagged player
			DataInputStream input = new DataInputStream(new FileInputStream(this.historyFile));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			String line;
			
			while((line = reader.readLine()) != null) {
					this.historyList.add(line);
			}
			
			reader.close();
			input.close();
			
			
			//if no player was ever tagged, add a dummy player
			if(this.historyList.size() == 0) {
				this.historyList.add("dummyPlayer - Please use /taggame reset");
			}
		
			
			//load taboo Listed Players
			DataInputStream tabooInput = new DataInputStream(new FileInputStream(this.tabooFile));
			BufferedReader tabooReader = new BufferedReader(new InputStreamReader(tabooInput));
			
			line = null;
			
			while((line = tabooReader.readLine()) != null) {
					this.tabooList.add(line);
			}
			
			tabooReader.close();
			tabooInput.close();
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public void save() {
		
		try {
			//save the history to the history-file
			FileWriter stream = new FileWriter(this.historyFile, false);
			BufferedWriter out = new BufferedWriter(stream);
			
			for(int i = 0; i < this.historyList.size(); i++) {
				if(i > 0) {
					out.write("\n");
				}
				out.write(this.historyList.get(i).replace("\\", ""));
			}
						
			out.close();
			stream.close();

			
			//save the taboo list to the taboo file
			FileWriter streamTaboo = new FileWriter(this.tabooFile, false);
			BufferedWriter outTaboo = new BufferedWriter(streamTaboo);
			
			for(int i = 0; i < this.tabooList.size(); i++) {
				if(i > 0) {
					out.write("\n");
				}
				outTaboo.write(this.tabooList.get(i).replace("\\", ""));	
			}
			
			outTaboo.close();
			streamTaboo.close();
			
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	

///////////////////////////////////////////////////////////////////////
////....................Sonstiges..................................////
///////////////////////////////////////////////////////////////////////	
	
	public boolean isequalto(String value) {
		
		String savedName = this.historyList.get(this.historyList.size()-1).trim();
		String givenName = value.trim();
		
		if(savedName.equalsIgnoreCase(givenName)) 	return true;
		else 										return false;
		
	}
	
	
///////////////////////////////////////////////////////////////////////
////....................Get und Set Funktionen.....................////
///////////////////////////////////////////////////////////////////////

	
	public void set(String playername) {
	
		this.historyList.add(playername);
		this.save();
	
	}
		
	public String get() {
		
		String value = (this.historyList.size() > 0) ? this.historyList.get(this.historyList.size()-1) : "unset";
		return value;
	
	}
	
	
	
///////////////////////////////////////////////////////////////////////
////....................Taboo Liste................................////
///////////////////////////////////////////////////////////////////////

	
	public boolean isTaboo(String value) {
		
		if(this.tabooList.contains(value)) return true;
		else							   return false;
		
	}
	
	public boolean toggleTaboo(String value) {
		
		if(this.tabooList.contains(value)) 	this.tabooList.remove(value);
		else 								this.tabooList.add(value);
		
		return isTaboo(value);
		
	}
	
	
	public String getTabooList() {
		
		
		
		String tabooString = "";
		for(int i = 0; i < this.tabooList.size(); i++) {
			if(i > 0) {
				tabooString += "\n";
			}
			tabooString += this.tabooList.get(i);
		}
		return tabooString;
	
	}
	
	
	
///////////////////////////////////////////////////////////////////////
////....................Admin-only Funktionen......................////
///////////////////////////////////////////////////////////////////////

	
	public void resetAll(String newTaggedPlayer) {
		
		//declare new tagged player: the user, that runs this
		this.historyList.clear();
		this.historyList.add(newTaggedPlayer);
		
	}

	
	
	public boolean forceSet(String newTaggedPlayer) {
		
	    if(this.historyList.size() > 0) {
	    	
		    int i = this.historyList.size()-1;
		    this.historyList.set(i, newTaggedPlayer);
		    
		    return true;
	    
	    }
	    else {
	    	
	    	return false;
	    	
	    }
	}
	
}
