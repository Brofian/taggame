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
		
		//create an empty List
		List<String> hints = new ArrayList<String>();

		
		
		
		//If user is writing the first param of the command
		if(params.size() == 1) {
			
			//add the basic suggestion
			hints.add("get");
			hints.add("taboo");
			hints.add("help");
			
			//add the following two suggestions only, if player has the permissions
			if(arg0.hasPermission("taggame.reset")) {
				hints.add("reset");
			}
			
			if(arg0.hasPermission("taggame.forceset")) {
				hints.add("forceset");
			}
			
		}
		
		
		
		
		//If user is writing the second param of the command
		else if(params.size() == 2) {
			
			//when first param was "get"
			if(params.get(0).equalsIgnoreCase("get")) {
				
				hints.add("taboo");
				hints.add("current"); //only used for completeness. Not used directly, but shows the player that there is more than one option
			
			}
			
			//if first param was "forceset"
			else if(params.get(0).equalsIgnoreCase("forceset")) {
		
				//show no hints, but instead the playerlist by returning null
				return null;
			
			}
			
		}
		
		
		
		
		//return the list of hints
        return hints;
        
	}
	
}