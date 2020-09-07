package me.Latestion.CustomWeapons.Totems.VoidTotem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Latestion.CustomWeapons.Main;
import me.Latestion.CustomWeapons.Totems.Totems;

public class Void extends Totems {
	
	public Main plugin;
	Inventory inv;
	
	List<UUID> cooldown1 = new ArrayList<UUID>();
	List<UUID> cooldown2 = new ArrayList<UUID>();
	
	public Void(Main plugin) {
		this.plugin = plugin;
	}
	
	public ItemStack getTotem() {
		return totem("Void", plugin.totem);
	}
	
	public boolean isVoid(ItemStack item) {
		if (item.isSimilar(getTotem())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void spawnPortal(Location location, Player player) {
		location = get5Ahead(location);
		int i = 3;
		
		if (this.getCardinalDirection(player).equalsIgnoreCase("N") || this.getCardinalDirection(player).equalsIgnoreCase("S")) {
			location.multiply(Math.cos(90));
		}
		
		for (int degree = 0; degree < 360; degree++) {
		    double radians = Math.toRadians(degree);
		    double x = Math.cos(radians) * i;
		    double z = Math.sin(radians) * i;
		    location.add(0, x, z);
		    location.getWorld().spawnParticle(Particle.PORTAL, location, 1);
		    location.subtract(0, x, z);
		}
	}

	public Location get5Ahead(Location loc) {
		Location twoBlocksAway = loc.add(loc.getDirection().multiply(5));
		return twoBlocksAway;
	}
	
}
