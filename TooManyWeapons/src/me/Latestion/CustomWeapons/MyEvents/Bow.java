package me.Latestion.CustomWeapons.MyEvents;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import me.Latestion.CustomWeapons.Main;
import me.Latestion.CustomWeapons.MyVoids.HomingTask;

public class Bow implements Listener {

	private Main plugin;
	public Bow(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void diid(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player && event.getProjectile() instanceof Arrow) {
			if (event.getBow().getItemMeta().hasCustomModelData())
			if (plugin.netherite.getConfig().getInt("Bow.Model") == event.getBow().getItemMeta().getCustomModelData()) {
				LivingEntity player = event.getEntity();
				
				Entity minEntity = null;
				List<Entity> ens = player.getNearbyEntities(64, 64, 64);
				
				if (plugin.target.containsKey(player)) {
					if (ens.contains(plugin.target.get(player))) {
						Entity entity = plugin.target.get(player);				
						if ((player.hasLineOfSight(entity)) && ((entity instanceof LivingEntity)) && (!entity.isDead())) {
							minEntity = entity;
						}	
						else {
							player.sendMessage(ChatColor.RED + plugin.target.get(player).getName() + " is dead!");
						}
					}
					else {
						player.sendMessage(ChatColor.RED + plugin.target.get(player).getName() + " is too far from you!");
					}
				}
				else {
					for (Entity entity : ens) {
						if ((player.hasLineOfSight(entity)) && ((entity instanceof LivingEntity)) && (!entity.isDead())) {
							minEntity = entity;
						}
					}
				}
				if (minEntity != null) {
					new HomingTask((Arrow)event.getProjectile(), (LivingEntity)minEntity, plugin);
				}
			}
		}
	}	
}
