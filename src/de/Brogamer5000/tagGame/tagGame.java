package de.Brogamer5000.tagGame;

import java.io.File;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class tagGame extends JavaPlugin implements Listener{	
	
	String pluginPraefix = "§5[tagGame] §f";
	CommandKit commandExecutor;
	
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

		String pluginFolder = this.getDataFolder().getAbsolutePath();
		(new File(pluginFolder)).mkdir();
		
		commandExecutor =  new CommandKit(pluginFolder, pluginPraefix);
		
		this.getCommand("taggame").setExecutor(commandExecutor);
		this.getCommand("taggame").setTabCompleter(new TabComplete());
		
		
		//events
		
		new CustomEventHandler(this);
		
		this.getLogger().info("Taggame-Plugin successfully enabled");
		
		
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("Taggame-Plugin successfully disabled");
	}

	
}


