package me.Latestion.CustomWeapons.MyEvents;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.Latestion.CustomWeapons.Main;

public class Interact implements Listener {

	private Main plugin;
	
	public Interact(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void i(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (plugin.reUtil.isWand(player)) {
			if (event.getAction() == Action.LEFT_CLICK_AIR) {
				int sec = plugin.netherite.getConfig().getInt("Wand.Fireball-Cooldown");
				if (plugin.fireballCooldown.contains(player)) {
					return;
				}
				Fireball fire = player.launchProjectile(Fireball.class);
				fire.setFireTicks(0);
				fire.setIsIncendiary(false);
				plugin.dontExplode.add(fire);
				plugin.fireballCooldown.add(player);
		        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		            public void run() {
		            	if (plugin.fireballCooldown.contains(player)) {
		            		plugin.fireballCooldown.remove(player);
		            	}
		            }            
		        }, sec * 20);
			}
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				event.setCancelled(true);
				
				if (plugin.guage.get(player) == 0) {
					return;
				}
				int sec = plugin.netherite.getConfig().getInt("Wand.Flame-Damage");
				plugin.guage.put(player, (plugin.guage.get(player) - 1));
				spawnWaves(player.getEyeLocation(), player);
		        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		            public void run() {
		            	if (plugin.guage.get(player) == 10) {
		            		// Do nothing
		            	}
		            	else plugin.guage.put(player, (plugin.guage.get(player) + 1));
		            }            
		        }, sec * 20);
			}
			return;	
		}
		if (Material.matchMaterial(plugin.netherite.getConfig().getString("Scythe.Material")) 
				== player.getInventory().getItemInMainHand().getType()) {
			if (plugin.netherite.getConfig().getInt("Scythe.Model") 
					== player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData()) {
				if (Action.LEFT_CLICK_AIR == event.getAction() || Action.LEFT_CLICK_BLOCK == event.getAction()) {
					int range = plugin.netherite.getConfig().getInt("Scythe.Range");
					int damage = plugin.netherite.getConfig().getInt("Scythe.Damage");
					Location loc = run(player, range);
					player.getLocation().getWorld().spawnParticle(Particle.SWEEP_ATTACK, run1(player).add(0, 1, 0), 1);
					Collection<Entity> ens = loc.getWorld().getNearbyEntities(loc, range, range, range);
					for (Entity en : ens) {
						if (en instanceof LivingEntity) {
							if (!en.isDead()) {		
								if (en instanceof Player) {
									if (player.equals(((Player)en))) {
										continue;
									}
								}
								((LivingEntity) en).damage(damage);
							}
						}
					}
					return;
				}
			}
		}
		if (isBow(player)) {
			if (event.getAction() == Action.LEFT_CLICK_AIR) {
				player.sendMessage(ChatColor.RED + "Bow target set to None!");
				if (plugin.target.containsKey(player)) {
					plugin.target.remove(player);
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
	
	
	public Location run(Player player, int range) {
		Location twoBlocksAway = player.getLocation().add(player.getLocation().getDirection().multiply(range));
		return twoBlocksAway;
	}
	
	public Location run1(Player player) {
		Location twoBlocksAway = player.getLocation().add(player.getLocation().getDirection().multiply(1.5));
		return twoBlocksAway;
	}
	
	public void spawnWaves(Location location, Player player) {
		int damage = plugin.netherite.getConfig().getInt("Wand.Flame-Damage");
		int rad = plugin.netherite.getConfig().getInt("Wand.Flame-Damage-Radius");
		int ii = plugin.netherite.getConfig().getInt("Wand.Range");
		location = location.add(location.getDirection().multiply(1));
		
		int start = 0, end = 0;
		if (getCardinalDirection(player).equalsIgnoreCase("N")) {
			start = 180;
			end = 360;
		}
		if (getCardinalDirection(player).equalsIgnoreCase("S")) {
			start = 0;
			end = 180;
		}		
		if (getCardinalDirection(player).equalsIgnoreCase("W")) {
			start = 90;
			end = 270;
		}		
		if (getCardinalDirection(player).equalsIgnoreCase("E")) {
			start = 270;
			end = 450;
		}
		for (int i = 1; i <= ii; i++) {
			for (int degree = start; degree < end; degree++) {
			    double radians = Math.toRadians(degree);
			    double x = Math.cos(radians) * i;
			    double z = Math.sin(radians) * i;
			    location.add(x, 0, z);
			    location.getWorld().spawnParticle(Particle.DRIP_LAVA, location, 1);
			    for (Entity en : location.getWorld().getNearbyEntities(location, rad, rad, rad)) {
			    	if (en instanceof LivingEntity) {
			    		if (!en.equals(player)) {
				    		((LivingEntity) en).damage(damage);
				    		if (plugin.netherite.getConfig().getBoolean("Wand.Set-On-Fire")) {
				    			en.setFireTicks((plugin.netherite.getConfig().getInt("Wand.Fire-Duration") * 20));
				    		}
			    		}
			    	}
			    }
			    location.subtract(x, 0, z);
			}
		}
	}
	
	public String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw()) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 45.0 || 315.0 <= rotation) {
        	 return "S";
         } else if (45.0 <= rotation && rotation < 135.0) {
        	 return "W";
         } else if (135.0 <= rotation && rotation < 225.0) {
        	 return "N";
         } else if (225.0 <= rotation && rotation < 315.0) {
        	 return "E";
         }
         return null;
	}
}
