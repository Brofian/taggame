package de.Brogamer5000.tagGame;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.Brogamer5000.tagGame.util.tagStorage;


public class CustomEventHandler implements Listener{
	
	tagGame plugin;
	
	public CustomEventHandler (tagGame plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
    @EventHandler(priority = EventPriority.MONITOR)
	public void onHit(EntityDamageByEntityEvent e) {
    	System.out.println("entity hit");
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player target = (Player) e.getEntity();
            Player attacker = (Player) e.getDamager();
            
            
            //get Infos and methods of the tagged Player
            tagStorage taggedPlayer = this.plugin.commandExecutor.taggedPlayer;
            
            
            List<String> tagHistory = taggedPlayer.historyList;
            String currentTag = "unset";
            String lastTag = "unset";
            if(tagHistory.size() > 0) {
            	currentTag = tagHistory.get(tagHistory.size()-1);
            }
            if(tagHistory.size() > 1) {
            	lastTag = tagHistory.get(tagHistory.size()-2);	
            }
            
            if(currentTag.equalsIgnoreCase(attacker.getName())) {
            	
            	boolean targetIsAfk = false;
            	
            	
            	/*
            	 * 
                //try to get Infos from the afk plugin
                try {
                	Plugin zw2afk = plugin.getServer().getPluginManager().getPlugin("Zw2Afk");
                	ArrayList<UUID> afkList = zw2afk.AFK_LIST; 
                	if(afkList.contains(target.getUniqueId())) {
                		targetIsAfk = true;
                	}
                }
                catch(Exception ex) {
                	ex.printStackTrace();
                }
                
                */
            	
            	
            	
            	
                
                
            	if(taggedPlayer.isTaboo(target.getName())) {
            		attacker.sendMessage(plugin.pluginPraefix + "§4" + target.getName() + " steht auf der Tabu Liste!");
            	}
            	else if(targetIsAfk) {
            		attacker.sendMessage(plugin.pluginPraefix + "§4" + target.getName() + " ist Afk!");
            	}
            	else if(lastTag.equalsIgnoreCase(target.getName())) {
            		attacker.sendMessage(plugin.pluginPraefix + "§4Du kannst " + target.getName() + " nicht zurück taggen!");
            	}
            	else {
            		attacker.sendMessage(plugin.pluginPraefix + "Du hast " + target.getName() + " erfolgreich getaggt!");
            		target.sendMessage(plugin.pluginPraefix + "Du wurdest von " + attacker.getName() + " getaggt!");
                	taggedPlayer.set(target.getName());            		
            	}
            }
            
            
        }
    }
	
}
