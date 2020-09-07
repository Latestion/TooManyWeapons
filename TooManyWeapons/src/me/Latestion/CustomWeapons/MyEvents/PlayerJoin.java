package me.Latestion.CustomWeapons.MyEvents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.Latestion.CustomWeapons.Main;
import me.Latestion.CustomWeapons.MyVoids.NetheriteWandRechargeUtils;

public class PlayerJoin implements Listener {

	private Main plugin;
	
	public PlayerJoin(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void dd(PlayerJoinEvent event) {
		plugin.guage.put(event.getPlayer(), plugin.netherite.getConfig().getInt("Wand.Flame-Amount"));
		NetheriteWandRechargeUtils util = new NetheriteWandRechargeUtils(plugin);
		plugin.guageBar.put(event.getPlayer(), util.createBar(event.getPlayer()));
	}
	
}
