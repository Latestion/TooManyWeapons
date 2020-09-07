package me.Latestion.CustomWeapons.Totems.VoidTotem;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.Latestion.CustomWeapons.Main;

public class VoidEvents extends Void implements Listener {

	public VoidEvents(Main plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			spawnPortal(event.getPlayer().getEyeLocation(), event.getPlayer());
		}
	}
	
}
