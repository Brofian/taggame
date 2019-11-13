package de.Brogamer5000.tagGame;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Brogamer5000.tagGame.util.tagStorage;

public class CommandKit implements CommandExecutor{

	public tagStorage taggedPlayer;
	public String pluginPraefix = "";
	
	public CommandKit(String pluginFolder, String pluginPraefix) {
		
		String HistoryTagFile = pluginFolder + File.separator + "taggedHistory.txt";
		String TabooListFile = pluginFolder + File.separator + "tabooList.txt";
		
		this.taggedPlayer = new tagStorage(new File(HistoryTagFile), new File(TabooListFile));
		this.taggedPlayer.load();
		
		this.pluginPraefix = pluginPraefix;
		
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player player = (Player) sender;
		
		String action = "help";
		String arg = "";
		
		if(args.length > 0) { 
			
			if(!args[0].equalsIgnoreCase("")) {
				action = args[0];
			}
			
			if(args.length > 1) {
				if(!args[1].equalsIgnoreCase("")) {
					arg = args[1];
				}
			}
		}

		
/////////////////////////////////////////
//......help Befehl....................//
/////////////////////////////////////////		
		if(action.equalsIgnoreCase("help") || action.equalsIgnoreCase("?")) {
			
			player.sendMessage("-------------- " + this.pluginPraefix + "--------------");
			player.sendMessage(				"§6/taggame get §fNennt den aktuell getaggten Spieler");
			player.sendMessage(				"§6/taggame get taboo §fListet alle Spieler aus der Tabu-Liste auf");
			player.sendMessage(				"§6/taggame help §fZeigt diese Liste");
			
			if(player.hasPermission("§6taggame.reset")) {
				player.sendMessage(			"§6/taggame reset §fSetzt das Spiel zurück. Die Tabu Liste bleibt erhalten!");
			}
			
			if(player.hasPermission("§6taggame.forceset")) {
				player.sendMessage(			"§6/taggame forceset <player> §fÄndert den aktuell getaggten Spieler zum angegebenen Spieler");
			}
			
			player.sendMessage("-------------- " + this.pluginPraefix + "--------------");
			
			return true;
		}
		
		
/////////////////////////////////////////
//......get Befehl.....................//
/////////////////////////////////////////
		
		
		else if(action.equalsIgnoreCase("get")) {
			
			if(arg.equalsIgnoreCase("taboo")) { 
				player.sendMessage(this.pluginPraefix + "Tabu sind folgende Spieler: " + this.taggedPlayer.getTabooList());
			}
			else {
				player.sendMessage(this.pluginPraefix + "Aktuell getaggt ist: " + this.taggedPlayer.get());	
			}
			
			return true;
		
		}
		
		
		
/////////////////////////////////////////
//......reset Befehl...................//
/////////////////////////////////////////
		
		else if(action.equalsIgnoreCase("reset")) {
			
			if(player.hasPermission("taggame.reset")) {
			
				String playername = player.getName();

				taggedPlayer.resetAll(playername);
				player.sendMessage(this.pluginPraefix + "Das Tag Spiel wurde zurückgesetzt. Du bist wurdest automatisch getaggt!");
				
				return true;
				
			}
			else {
				
				return false;
			
			}
			
		}

		
		
/////////////////////////////////////////
//......forceset Befehl................//
/////////////////////////////////////////
		
		else if(action.equalsIgnoreCase("forceset")) {
			//only allow access, if user has the permission
			if(player.hasPermission("taggame.forceset")) {
			
				//return error, when no arg is given
				if(arg.equalsIgnoreCase("") == true) {
					
					return false;
				
				}
				else {
				
					taggedPlayer.forceSet(arg);
					player.sendMessage(this.pluginPraefix + "Du hast " + arg + " zwangsweise als Tag markiert!");
					
					return true;	
			
				}
				
			}
			
		}
		
		
/////////////////////////////////////////
//......taboo Befehl...................//
/////////////////////////////////////////
		
		else if(action.equalsIgnoreCase("taboo")) {
			
			String playername = player.getName();

			boolean wasSetToTaboo = this.taggedPlayer.toggleTaboo(playername);

			if(wasSetToTaboo) {
				player.sendMessage(this.pluginPraefix + "Du wurdest zur Tabu-Liste hinzugefügt!");
			}
			else {
				player.sendMessage(this.pluginPraefix + "Du wurdest von der Tabu-Liste entfernt!");	
			}
			
			return true;
			
		}
		
					
		//false, wenn syntax verbesserung angezeigt werden soll
		return false;
	}

}

