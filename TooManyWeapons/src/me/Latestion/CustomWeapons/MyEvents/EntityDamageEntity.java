package me.Latestion.CustomWeapons.MyEvents;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.Latestion.CustomWeapons.Main;

public class EntityDamageEntity implements Listener {

	private Main plugin;
	
	public EntityDamageEntity(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void i(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof LivingEntity && event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (isBow(player)) {
				if (player.isSneaking()) {
					player.sendMessage(ChatColor.GOLD + "Set " + ((LivingEntity)event.getEntity()).getName() + " your bow target!");
					plugin.target.put(player, ((LivingEntity)event.getEntity()));
				}
			}
		}
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
	
}
