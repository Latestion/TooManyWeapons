package me.Latestion.CustomWeapons.MyVoids;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import me.Latestion.CustomWeapons.Main;

public class NetheriteWandRechargeUtils {
	
	private Main plugin;
	
	public NetheriteWandRechargeUtils(Main plugin) {
		this.plugin = plugin;
	}
	
	public BossBar createBar(Player player) {
		BossBar bar = Bukkit.createBossBar(format(ChatColor.WHITE + "Total Shots Remaining: " + getTotalRemainShot(player))
				, BarColor.RED, BarStyle.SOLID);
		bar.setVisible(true);
		return bar;
	}
	
	private int getTotalRemainShot(Player player) {
		return plugin.guage.get(player);
	}
	
	public BossBar getPlayerBar(Player player) {
		return (plugin.guageBar.get(player));
	}
	
	public void cast() {
		int totalShot = plugin.netherite.getConfig().getInt("Wand.Flame-Amount");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player player : plugin.guageBar.keySet()) {
					BossBar bar = getPlayerBar(player);
					if (isWand(player)) {
						bar.addPlayer(player);
						bar.setTitle(format(ChatColor.WHITE + "Total Shots Remaining: " + getTotalRemainShot(player)));
						bar.setProgress((((double)getTotalRemainShot(player)) / ((double)totalShot)));
					}
					else {
						if (bar.getPlayers().contains(player)) {
							bar.removePlayer(player);
						}
					}
				}
			}
		}, 0, 5L);
	}
	
	private String format(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	
	public boolean isWand(Player player) {
		if (player.getInventory().getItemInMainHand() != null) {
			if (Material.matchMaterial(plugin.netherite.getConfig().getString("Wand.Material"))
					== player.getInventory().getItemInMainHand().getType()) {
				if (plugin.netherite.getConfig().getInt("Wand.Model") 
						== player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData()) {
					return true;
				}
			}
		}
		else if (player.getInventory().getItemInOffHand() != null) {
			if (Material.matchMaterial(plugin.netherite.getConfig()
					.getString("Wand.Material")) == player.getInventory().getItemInOffHand().getType()) {
				if (plugin.netherite.getConfig().getInt("Wand.Model") 
					== player.getInventory().getItemInOffHand().getItemMeta().getCustomModelData()) {
					return true;
				}
			}
		}
		else {
			return false;
		}
		return false;
	}
	
}
