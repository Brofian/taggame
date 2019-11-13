package de.Brogamer5000.tagGame;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.sk89q.worldguard.bukkit.protection.events.DisallowedPVPEvent;

import de.Brogamer5000.tagGame.util.tagStorage;


public class CustomEventHandler implements Listener{
	
	tagGame plugin;
	
	
	
	public CustomEventHandler (tagGame plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	
	
    @EventHandler(priority = EventPriority.HIGHEST)
	public void onHit(DisallowedPVPEvent e) {
    	

    	//get target and attacker from DisallowedPVPEvent
    	Player target = (Player) e.getDefender();
    	Player attacker = (Player) e.getAttacker();
    	

    	System.out.println("test");

    	
        if (target instanceof Player && attacker instanceof Player) {
            
            
            //get current infos and methods of the tagged Player
            tagStorage taggedPlayer = tagGame.commandExecutor.taggedPlayer;
            List<String> tagHistory = taggedPlayer.historyList;
            
            String currentTag = 	(tagHistory.size() >= 1) ? tagHistory.get(tagHistory.size()-1) : "unset";
            String lastTag = 		(tagHistory.size() >= 2) ? tagHistory.get(tagHistory.size()-2) : "unset";
            
            
            
            //test, if the attacker is the tagged player
            if(currentTag.equalsIgnoreCase(attacker.getName())) {
            	
            	
            	//test, if the target is currently marked as afk (not used)
            	boolean targetIsAfk = false;
            	
            	/*
            	 
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
            	
            	//test, if target is listed in the taboo list
            	if(taggedPlayer.isTaboo(target.getName())) {
            		
            		attacker.sendMessage(tagGame.prefix + "§4" + target.getName() + " steht auf der Tabu Liste!");
            	
            	}
            	
            	//else: test, if the target was the one, that gave the tag to the attacker
            	else if(lastTag.equalsIgnoreCase(target.getName())) {
            		
            		attacker.sendMessage(tagGame.prefix + "§4Du kannst " + target.getName() + " nicht zurück taggen!");
            	
            	}
            	
            	//else: test, if target is afk at the moment
            	else if(targetIsAfk) {
            	
            		attacker.sendMessage(tagGame.prefix + "§4" + target.getName() + " ist Afk!");
            	
            	}
            	
            	//else: attacker is succesfully tagging the target!
            	else {
            		
            		//send Messages to both, the attacker and the target
            		attacker.sendMessage(tagGame.prefix + "Du hast " + target.getName() + " erfolgreich getaggt!");
            		target.sendMessage(tagGame.prefix + "Du wurdest von " + attacker.getName() + " getaggt!");
                	
            		//call the set function in the instance of taggedPlayer
            		taggedPlayer.set(target.getName());            		
                    
            	}
            }   
        }        
    }
}
