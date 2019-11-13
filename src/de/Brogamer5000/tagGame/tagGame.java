package de.Brogamer5000.tagGame;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class tagGame extends JavaPlugin implements Listener{	
	
	public static CommandKit commandExecutor;
	public static tagGame instance;
	public static Logger logger;
	public static String prefix;
	
	//im Plugin genutzte Berechtigungen:
	// taggame.reset 
	//		-> Berechtigung zum Resetten des Verlaufs
	// taggame.forceset
	//		-> Berechtigung zum zwangsweise ändern der getaggten Person 
	//				- der alte tag wird überschrieben (!)
	//				- man muss nicht selbst getaggt sein
	//				- es kann selbst eine person aus der Tabu liste getaggt werden
	
	
	@Override
	public void onEnable() {

		//create information for the logger
		instance = this;
		prefix = "§5[tagGame] §f";
		logger = getLogger();
		
		//create folder for the stored Information
		String pluginFolder = this.getDataFolder().getAbsolutePath();
		new File(pluginFolder).mkdir();
		
		
		//save instance of the command-collection class
		commandExecutor =  new CommandKit(pluginFolder, prefix);
		
		//add the 'taggame' command and set its executor and tabCompleter
		this.getCommand("taggame").setExecutor(commandExecutor);
		this.getCommand("taggame").setTabCompleter(new TabComplete());
		
		
		//create the eventhandler
		new CustomEventHandler(this);
		
		
		this.getLogger().info("Taggame-Plugin successfully enabled");
		
	}
	
	
	
	
	
	@Override
	public void onDisable() {
		
		//save everything
		commandExecutor.taggedPlayer.save();
		
		this.getLogger().info("Taggame-Plugin successfully disabled");
	
	}

	
}


