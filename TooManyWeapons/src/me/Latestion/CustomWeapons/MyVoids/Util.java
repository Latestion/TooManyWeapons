package me.Latestion.CustomWeapons.MyVoids;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import me.Latestion.CustomWeapons.Main;

public class Util {
	
	private Main plugin;
	
	public Util(Main plugin) {
		this.plugin = plugin;
	}
	
	public void run() { 
		BukkitScheduler scheduler = plugin.getServer().getScheduler();
       	scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
            public void run() {
				try {
					for (Player player : Bukkit.getOnlinePlayers()) {
						plugin.netherite.getConfig().getConfigurationSection("").getKeys(false).forEach(key -> {
							if (Material.matchMaterial(plugin.netherite.getConfig().getString(key + ".Material").toUpperCase()) 
									== player.getInventory().getItemInMainHand().getType()) {
								if (player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() 
										== plugin.netherite.getConfig().getInt(key + ".Model")) {
									if (key.equalsIgnoreCase("thorn")) {
										if (player.isSneaking()) {
											player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30, 0, false, false, false));	
										}
									}
									if (key.equalsIgnoreCase("axe")) {
										player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30, 1, false, false, false));
									}
								}
							}
						});
					}
				} catch (Exception e) {
				}
			}
        }, 0L, 20L);
	}
}
