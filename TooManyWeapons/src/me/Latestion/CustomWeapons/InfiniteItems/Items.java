package me.Latestion.CustomWeapons.InfiniteItems;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.Latestion.CustomWeapons.Main;

public class Items implements Listener {
	
	
	private Main plugin;
	public Items(Main plugin) {
		this.plugin = plugin;
	}
	
	public ItemStack torch() {
		ItemStack item = new ItemStack(Material.TORCH);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(format(plugin.infinite.getConfig().getString("Torch.Name")));
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore(plugin.infinite.getConfig().getStringList("Torch.Lore")));
		meta.setCustomModelData(plugin.getConfig().getInt("Torch.Model"));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack rocket() {
		ItemStack item = new ItemStack(Material.FIREWORK_ROCKET);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(format(plugin.infinite.getConfig().getString("Rocket.Name")));
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore(plugin.infinite.getConfig().getStringList("Rocket.Lore")));
		meta.setCustomModelData(plugin.getConfig().getInt("Rocket.Model"));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack water() {
		ItemStack item = new ItemStack(Material.WATER_BUCKET);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(format(plugin.infinite.getConfig().getString("Water.Name")));
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore(plugin.infinite.getConfig().getStringList("Water.Lore")));
		meta.setCustomModelData(plugin.getConfig().getInt("Water.Model"));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack dry() {
		ItemStack item = new ItemStack(Material.BUCKET);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(format(plugin.infinite.getConfig().getString("Dry.Name")));
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore(plugin.infinite.getConfig().getStringList("Dry.Lore")));
		meta.setCustomModelData(plugin.getConfig().getInt("Dry.Model"));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack manna() {
		ItemStack item = new ItemStack(Material.BREAD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(format(plugin.infinite.getConfig().getString("Manna.Name")));
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);	meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore(plugin.infinite.getConfig().getStringList("Manna.Lore")));
		meta.setCustomModelData(plugin.getConfig().getInt("Manna.Model"));
		item.setItemMeta(meta);
		return item;
	}
	
	public List<ItemStack> arrows() {
		List<ItemStack> arrows = new ArrayList<ItemStack>();
		for (String arrow : plugin.infinite.getConfig().getStringList("Arrows.Type")) {
			ItemStack item = new ItemStack(Material.TIPPED_ARROW);
			PotionMeta potMeta = (PotionMeta) item.getItemMeta();
			potMeta.setBasePotionData(new PotionData(PotionType.valueOf(arrow), false, false));
			potMeta.setDisplayName(format(plugin.infinite.getConfig().getString("Arrows.Name").replace("%type%",
					StringUtils.capitalize((arrow.toLowerCase())))));
			potMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);	potMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			potMeta.setLore(lore(plugin.infinite.getConfig().getStringList("Arrows.Lore"), arrow));
			potMeta.setCustomModelData(plugin.getConfig().getInt("Arrows.Model"));
			item.setItemMeta(potMeta);
			arrows.add(item);
		}
		return arrows;
	}
	
	private String format(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	private List<String> lore(List<String> set) {
		List<String> lore = new ArrayList<String>();
		for (String s : set) {
			lore.add(format(s));
		}
		return lore;
	}
	
	private List<String> lore(List<String> set, String type) {
		List<String> lore = new ArrayList<String>();
		for (String s : set) {
			lore.add(format((s.replace("%type%", type))));
		}
		return lore;
	}
	
	@EventHandler
	public void place(BlockPlaceEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(this.torch())) {
			event.setCancelled(false);
			event.getPlayer().getInventory().setItemInMainHand(this.torch());
			event.getPlayer().updateInventory();
		}
		else if (event.getPlayer().getInventory().getItemInOffHand().isSimilar(this.torch())) {
			event.setCancelled(false);
			event.getPlayer().getInventory().setItemInOffHand(this.torch());
			event.getPlayer().updateInventory();
		}
	}
	
	@EventHandler
	public void rocket(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(this.rocket())) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				event.setCancelled(false);
		        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		            public void run() {
						event.getPlayer().getInventory().setItemInMainHand(rocket());
						event.getPlayer().updateInventory();
						return;
		            }            
		        }, 0);
			}
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		            public void run() {
						event.getPlayer().getInventory().setItemInMainHand(rocket());
						event.getPlayer().updateInventory();
						return;
		            }            
		        }, 0);
			}
		}
		
		else if (event.getPlayer().getInventory().getItemInOffHand().isSimilar(this.rocket())) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				event.setCancelled(false);
		        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		            public void run() {
						event.getPlayer().getInventory().setItemInOffHand(rocket());
						event.getPlayer().updateInventory();
						return;
		            }            
		        }, 0);
			}
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(false);
		        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		            public void run() {
						event.getPlayer().getInventory().setItemInOffHand(rocket());
						event.getPlayer().updateInventory();
						return;
		            }            
		        }, 0);
			}
		}
	}
	
	@EventHandler
	public void food(PlayerItemConsumeEvent event) {
		if (event.getItem().isSimilar(this.manna())) {
			event.setCancelled(true);
			event.getPlayer().setFoodLevel(event.getPlayer().getFoodLevel() + 6);
		}

	}
	
	@EventHandler
	public void take(PlayerBucketEmptyEvent event) {
		if (event.getBucket() == Material.WATER_BUCKET) {
			Player player = event.getPlayer();
			if (player.getInventory().getItemInMainHand().isSimilar(this.water())) {
				Location loc = event.getBlock().getLocation();
				event.setCancelled(true);
				loc.getWorld().getBlockAt(loc).setType(Material.WATER);
			}
			else if (player.getInventory().getItemInOffHand().isSimilar(this.water())) {
				Location loc = event.getBlock().getLocation();
				event.setCancelled(true);
				loc.getWorld().getBlockAt(loc).setType(Material.WATER);
			}
		}
	}
	
	@EventHandler
	public void give(PlayerBucketFillEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(this.dry())) {
			Location loc = event.getBlock().getLocation();
			event.setCancelled(true);
			loc.getWorld().getBlockAt(loc).setType(Material.AIR);
		}
		if (event.getPlayer().getInventory().getItemInOffHand().isSimilar(this.dry())) {
			Location loc = event.getBlock().getLocation();
			event.setCancelled(true);
			loc.getWorld().getBlockAt(loc).setType(Material.AIR);
		}
	}
	
	@EventHandler
	public void bow(EntityShootBowEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if (!(event.getProjectile() instanceof Arrow)) {
			return;
		}
		Player player = (Player) event.getEntity();
		ItemStack item = getMainArrow(player);
		if (arrows().contains(item)) {
			event.setConsumeItem(false);
			player.updateInventory();
		}
	}
	
	private ItemStack getMainArrow(Player player) {
		if (player.getInventory().getItemInMainHand() != null) {
			if (player.getInventory().getItemInMainHand().getType() == Material.ARROW || 
					player.getInventory().getItemInMainHand().getType() == Material.TIPPED_ARROW) {
					return player.getInventory().getItemInMainHand();
				}
		}
		if (player.getInventory().getItemInOffHand() != null) {
			if (player.getInventory().getItemInOffHand().getType() == Material.ARROW || 
					player.getInventory().getItemInOffHand().getType() == Material.TIPPED_ARROW) {
					return player.getInventory().getItemInOffHand();
			}
		}
		for (ItemStack item : player.getInventory()) {
			if (item != null) {
				if (item.getType() == Material.ARROW || item.getType() == Material.TIPPED_ARROW) {
					return item;
				}
			}
		}
		return null;
	}
}
