package me.Latestion.CustomWeapons.MyVoids;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.Latestion.CustomWeapons.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class NetheriteBowTargetUtil {
	
	private Main plugin;
	
	public NetheriteBowTargetUtil(Main plugin) {
		this.plugin = plugin;
	}
	
	public void run() {
		BukkitScheduler scheduler = plugin.getServer().getScheduler();
       	scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
            public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (isBow(player)) {
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							TextComponent.fromLegacyText(ChatColor.GOLD + "Targeted Player: " 
						+ ChatColor.RESET + getTarget(player)));
					}
				}
			}
        }, 0L, 20L);
	}
	
	private boolean isBow(Player player) {
		
		if (player.getInventory().getItemInMainHand() != null) {
			if (player.getInventory().getItemInMainHand().getType() == Material.BOW) {
				if (player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
					if (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData()
							== plugin.netherite.getConfig().getInt("Bow.Model")) {
						return true;
					}
				}
			}
		}
		
		else if (player.getInventory().getItemInOffHand() != null) {
			if (player.getInventory().getItemInOffHand().getType() == Material.BOW) {
				if (player.getInventory().getItemInOffHand().getItemMeta().hasCustomModelData()) {
					if ( player.getInventory().getItemInOffHand().getItemMeta().getCustomModelData()
					== plugin.netherite.getConfig().getInt("Bow.Model")) {
						return true;
					}
				}	 
			}
		}	
		else {
			return false;
		}
		return false;
	}
	
	private String getTarget(Player player) {
		if (plugin.target.containsKey(player)) {
			if (plugin.target.get(player) != null) {
				return plugin.target.get(player).getName();
			}
			else {
				return "None";
			}
		}
		else {
			return "None";
		}
	}
	
}
