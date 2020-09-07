package me.Latestion.CustomWeapons.MyEvents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.Latestion.CustomWeapons.Main;

public class EntityDeath implements Listener {

	private Main plugin;
	
	public EntityDeath(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void death(EntityDeathEvent event) {
		if (plugin.target.containsValue(event.getEntity())) {
			if (getTargeter(event.getEntity()) == null) {
				return;
			}
			else {
				plugin.target.remove(getTargeter(event.getEntity()));
			}
		}
	}
	
	private Player getTargeter(Entity en) {
		for (Player player : plugin.target.keySet()) {
			if (plugin.target.get(player).equals(en)) {
				return player;
			}
		}
		return null;
	}
	
}
