package me.Latestion.CustomWeapons.Executor;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.Latestion.CustomWeapons.Main;
import me.Latestion.CustomWeapons.InfiniteItems.Items;
import me.Latestion.CustomWeapons.Totems.TotemCheck;
import me.Latestion.CustomWeapons.Totems.StormsTotem.Storm;
import me.Latestion.CustomWeapons.Totems.TimeTotem.Time;

public class Commands implements CommandExecutor {
	
	private Main plugin;
	public Commands(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("tmw")) {
			Items item = new Items(plugin);
			
			if (args.length == 0) {
				sender.sendMessage(ChatColor.RED + "/tmw give {player} {itemname}" 
					+ ChatColor.WHITE + ": Gives the mention playerd the item!");
			}
			else {
				if (sender.hasPermission("tmw.give")) {
					if (args[0].equalsIgnoreCase("give")) {
						if (args.length >= 3) {
							Player player;
							try {
								player = Bukkit.getPlayerExact(args[1]);
							}
							catch (Exception e) {
								sender.sendMessage(ChatColor.RED + "Invalid Player!");
								return false;
							}
							if (args[2].equalsIgnoreCase("torch")) {
								player.getInventory().addItem(item.torch());
								return false;
							}
							if (args[2].equalsIgnoreCase("rocket")) {
								player.getInventory().addItem(item.rocket());
								return false;
							}
							if (args[2].equalsIgnoreCase("manna")) {
								player.getInventory().addItem(item.manna());
								return false;
							}
							if (args[2].equalsIgnoreCase("water")) {
								player.getInventory().addItem(item.water());
								return false;
							}
							if (args[2].equalsIgnoreCase("dry")) {
								player.getInventory().addItem(item.dry());
								return false;
							}
							if (args[2].equalsIgnoreCase("arrow")) {
								String type = args[3].toUpperCase();
								List<String> types = plugin.infinite.getConfig().getStringList("Arrows.Type");
								if (types.contains(type)) {
									int index = types.indexOf(type);
									player.getInventory().addItem(item.arrows().get(index));
									return false;
								}
								else {
									sender.sendMessage(ChatColor.RED + "Invalid Arrow Type!");
									return false;
								}
							}
							if (args[2].equalsIgnoreCase("totem.time")) {
								Time time  = new Time(plugin);
								player.getInventory().addItem(time.getTotem());
							}
							if (args[2].equalsIgnoreCase("totem.storm")) {
								Storm storm = new Storm(plugin);
								player.getInventory().addItem(storm.getTotem());
							}
							if (args[2].equalsIgnoreCase("totem.belt")) {
								TotemCheck check = new TotemCheck(plugin);
								player.getInventory().addItem(check.belt());
							}
						}
					}
				}
			}
			
		}
		return false;
	}
}
