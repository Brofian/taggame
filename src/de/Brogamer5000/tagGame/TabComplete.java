package de.Brogamer5000.tagGame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		//get a list of all given params
		List<String> params = new ArrayList<String>();
		for(int i = 0; i < arg3.length; i++) {
			params.add(arg3[i]);
		}
		
		
		List<String> hints = new ArrayList<String>();

		
		
		
		//Vorschläge für den ersten Eintrag
		if(params.size() == 1) {
			hints.add("get");
			hints.add("taboo");
			hints.add("help");
			if(arg0.hasPermission("taggame.reset")) {
				hints.add("reset");
			}
			if(arg0.hasPermission("taggame.forceset")) {
				hints.add("forceset");
			}
		}
		
		else if(params.size() == 2) {
			if(params.get(0).equalsIgnoreCase("get")) {
				hints.add("taboo");
				hints.add("current");
			}
			
			else if(params.get(0).equalsIgnoreCase("forceset")) {
				//show no hints, but instead the playerlist
				return null;
			}
			
		}
		
		
        return hints;
        
	}
	
}