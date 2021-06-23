package dev.array21.respawn.commands;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.array21.dutchycore.module.commands.ModuleCommand;
import dev.array21.dutchycore.module.commands.ModuleTabCompleter;
import dev.array21.dutchycore.module.file.ModuleConfiguration;
import dev.array21.respawn.ReSpawn;
import net.md_5.bungee.api.ChatColor;

public class SetRespawnCommandListener implements ModuleCommand, ModuleTabCompleter {

	private ReSpawn module;
	
	public SetRespawnCommandListener(ReSpawn module) {
		this.module = module;
	}
	
	@Override
	public String[] complete(CommandSender sender, String[] args) {
		return null;
	}

	@Override
	public boolean fire(CommandSender sender, String[] args) {
		if(!sender.hasPermission("respawn.setspawn")) {
			sender.sendMessage(String.format("%s[%sRS%s]%s You do not have permission to use this command!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.RED));
			return true;
		}
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(String.format("%s[%sRS%s]%s This command can only be used by in-game Players!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.RED));
			return true;
		}
		
		Location newSpawnLoc;
		if(args.length < 3) {
			newSpawnLoc = ((Player) sender).getLocation();
		} else {
			if(!isNumber(args[0])) {
				sender.sendMessage(String.format("%s[%sRS%s]%s The first argument provided is not a valid integer!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.RED));
				return true;
			}
			
			if(!isNumber(args[1])) {
				sender.sendMessage(String.format("%s[%sRS%s]%s The second argument provided is not a valid integer!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.RED));
				return true;
			}
			
			if(!isNumber(args[2])) {
				sender.sendMessage(String.format("%s[%sRS%s]%s The third argument provided is not a valid integer!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.RED));
				return true;
			}
			
			int x = Integer.valueOf(args[0]);
			int y = Integer.valueOf(args[1]);
			int z = Integer.valueOf(args[2]);
			World w = ((Player) sender).getWorld();
			
			newSpawnLoc = new Location(w, x, y, z);
		}
		
		String configSpawnPos = String.format("%s<-->%d<-->%d<-->%d", newSpawnLoc.getWorld().getName(), newSpawnLoc.getBlockX(), newSpawnLoc.getBlockY(), newSpawnLoc.getBlockZ());
		
		ModuleConfiguration mc = this.module.getModuleFileHandler().getModuleConfiguration();
		mc.setValue("spawnPos", configSpawnPos);
		mc.save();
		
		sender.sendMessage(String.format("%s[%sRS%s]%s Spawn updated!", ChatColor.GRAY, ChatColor.AQUA, ChatColor.GRAY, ChatColor.GOLD));
		return true;
	}
	
	private boolean isNumber(String s) {
		//Check if the provided String is a valid number
		Pattern p = Pattern.compile("^\\d+$");
		Matcher m = p.matcher(s);
		if(!m.matches()) {
			return false;
		}
		
		//Check if the provided String is less than Integer.MAX_VALUE
		BigInteger bi = new BigInteger(s);
		int comparison = bi.compareTo(BigInteger.valueOf(Integer.MAX_VALUE));
		if(comparison == 0 || comparison == 1) {
			return false;
		}
		
		return true;
	}

}
