package me.Latestion.CustomWeapons.Totems.StormsTotem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Latestion.CustomWeapons.Main;
import me.Latestion.CustomWeapons.Totems.Totems;

public class Storm extends Totems {

	public Main plugin;
	Inventory inv;
	
	public Storm(Main plugin) {
		this.plugin = plugin;
		createInv();
		if (!plugin.allTotems.contains(getTotem())) plugin.allTotems.add(getTotem());
	}
	
	public ItemStack getTotem() {
		return totem("Storms", plugin.totem);
	}
	
	public boolean isStorm(ItemStack item) {
		if (item.isSimilar(getTotem())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	private void createInv() {
		inv = Bukkit.createInventory(null, 9, ChatColor.GOLD + "Change Weather");
		createInvGlass();
		createInvItems();
	}
	 
	private void createInvGlass() {
		ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		inv.setItem(0, item);
		inv.setItem(1, item);
		inv.setItem(3, item);
		inv.setItem(5, item);
		inv.setItem(7, item);
		inv.setItem(8, item);
	}
	
	private void createInvItems() {
		ItemStack item = new ItemStack(Material.WATER_BUCKET);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName(ChatColor.BLUE + "Rain");
		item.setItemMeta(meta);
		inv.addItem(item);
		
		ItemStack item2 = new ItemStack(Material.BUCKET);
		ItemMeta meta2 = item2.getItemMeta();
		meta2.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta2.setDisplayName(ChatColor.BLUE + "Clear");
		item2.setItemMeta(meta2);
		inv.addItem(item2);
		
		ItemStack item3 = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta3 = item3.getItemMeta();
		meta3.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta3.setDisplayName(ChatColor.BLUE + "Thunder Storn");
		item3.setItemMeta(meta3);
		inv.addItem(item3);
	}
	
	public void cast() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (hasTotem(player)) {
						ItemStack item = getTotem(player);
						if (item.isSimilar(getTotem())) {
							setPogs(player);
							showBar(player);
						}
					}
					else hideBar(player);
				}
			}
		}, 0, 5L);
	}
	

	private void hideBar(Player player) {
		BossBar bar1 = getWeatherBar(player);
		BossBar bar2 = getStrikeBar(player);
		bar1.setVisible(false);
		bar2.setVisible(false);
	}
	
	private void showBar(Player player) {
		BossBar bar1 = getWeatherBar(player);
		BossBar bar2 = getStrikeBar(player);
		bar1.setVisible(true);
		bar2.setVisible(true);
	}
	
	private void setPogs(Player player) {
		if (plugin.weatherCooldown.contains(player.getUniqueId())) {
			getWeatherBar(player).setProgress(0D);
		}
		else {
			getWeatherBar(player).setProgress(1D);
		}
		if (plugin.strikeCooldown.contains(player.getUniqueId())) {
			getStrikeBar(player).setProgress(0D);
		}
		else {
			getStrikeBar(player).setProgress(1D);
		}
	}
	
	private BossBar getWeatherBar(Player player) {
		return plugin.weatherBar.get(player.getUniqueId());
	}
	
	private BossBar getStrikeBar(Player player) {
		return plugin.strikeBar.get(player.getUniqueId());
	}
	
}
