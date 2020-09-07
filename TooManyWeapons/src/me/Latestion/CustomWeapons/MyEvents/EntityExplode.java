package me.Latestion.CustomWeapons.MyEvents;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import me.Latestion.CustomWeapons.Main;

public class EntityExplode implements Listener {

	private Main plugin;
	public EntityExplode(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onExplode(ExplosionPrimeEvent event) {
		if (event.getEntity() instanceof Fireball) {
			Fireball fire = (Fireball) event.getEntity();
			if (fire.getShooter() instanceof Player) {
				if (plugin.dontExplode.contains(fire)) {
					event.setFire(false);
					event.setRadius(0);
					Location loc = fire.getLocation();
					int rad = plugin.netherite.getConfig().getInt("Wand.FireBall-Radius");
					int damage = plugin.netherite.getConfig().getInt("Wand.FireBall-Damage");
					Collection<Entity> ens = loc.getWorld().getNearbyEntities(loc, rad, rad, rad);
					for (Entity en : ens) {
						if (en instanceof LivingEntity) {
							((LivingEntity) en).damage(damage);
						}
					}
					plugin.dontExplode.remove(fire);
				}
			}
		}
	}
	
}
