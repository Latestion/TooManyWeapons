package me.Latestion.CustomWeapons.Executor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabC implements TabCompleter {

	List<String> arguments = new ArrayList<String>();
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (arguments.isEmpty()) {
			arguments.add("give");
			
			List<String> result = new ArrayList<String>();
			if (args.length == 1) {
				for (String s : arguments) {
					if (s.toLowerCase().startsWith(args[0].toLowerCase()))
						result.add(s);
				}
				return result;
			}
			
		}
		
		return null;
	}

}
