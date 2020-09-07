package me.Latestion.CustomWeapons.Totems.TimeTotem;

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

public class Time extends Totems {

	public Main plugin;
	Inventory inv;
	
	public Time(Main plugin) {
		this.plugin = plugin;
		astral();
		if (!plugin.allTotems.contains(getTotem())) plugin.allTotems.add(getTotem());
	}
	
	public ItemStack getTotem() {
		return totem("Time", plugin.totem);
	}
	
	public boolean isTime(ItemStack item) {
		if (item.isSimilar(getTotem())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private void astral() {
		inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Change Time");
		setPane();
		ItemStack item = new ItemStack(Material.CLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		for (int i = 1; i < 25; i++) {
			meta.setDisplayName("Hour: " + i);
			item.setItemMeta(meta);
			inv.addItem(item);
		}
	}
	
	private void setPane() {
		ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setDisplayName(" ");
		item.setItemMeta(meta);
		for (int i = 0; i < 10; i++) {
			inv.setItem(i, item);
		}
		inv.setItem(17, item);
		inv.setItem(18, item);
		inv.setItem(26, item);
		inv.setItem(27, item);
		inv.setItem(35, item);
		inv.setItem(36, item);
		inv.setItem(37, item);
		inv.setItem(38, item);
		inv.setItem(42, item);
		inv.setItem(43, item);
		for (int i = 44; i < 54; i++) {
			inv.setItem(i, item);
		}
	}
	
	public int getTick(int hour) {
		if (hour >= 1 && hour <= 6) {
			return (18000 + (hour * 1000));
		}
		if (hour > 6 && hour <= 24) {
			return ((hour - 6) * 1000);
		}
		else {
			return 0;
		}
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
	
	public int getAstralCooldown() {
		return plugin.totem.getConfig().getInt("Time.Astral-Cooldown");
	}
	
	public int getFreezeRadius() {
		return plugin.totem.getConfig().getInt("Time.Freeze-Radius");
	}
	
	public int getFreezeCooldown() {
		return plugin.totem.getConfig().getInt("Time.Freeze-Cooldown");
	}
	
	public int getFreezeDuration() {
		return plugin.totem.getConfig().getInt("Time.Freeze-Duration");
	}
	
	
	private void hideBar(Player player) {
		BossBar bar1 = getAstralBar(player);
		BossBar bar2 = getFreezeBar(player);
		bar1.setVisible(false);
		bar2.setVisible(false);
	}
	
	private void showBar(Player player) {
		BossBar bar1 = getAstralBar(player);
		BossBar bar2 = getFreezeBar(player);
		bar1.setVisible(true);
		bar2.setVisible(true);
	}
	
	private void setPogs(Player player) {
		if (plugin.astralCooldown.contains(player.getUniqueId())) {
			getAstralBar(player).setProgress(0D);
		}
		else {
			getAstralBar(player).setProgress(1D);
		}
		if (plugin.freezeCooldown.contains(player.getUniqueId())) {
			getFreezeBar(player).setProgress(0D);
		}
		else {
			getFreezeBar(player).setProgress(1D);
		}
	}
	
	private BossBar getAstralBar(Player player) {
		return plugin.astralBar.get(player.getUniqueId());
	}
	
	private BossBar getFreezeBar(Player player) {
		return plugin.freezeBar.get(player.getUniqueId());
	}
	
}
