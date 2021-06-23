package dev.array21.respawn.listeners;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import dev.array21.respawn.ReSpawn;

public class PlayerRespawnEventListener implements Listener {

	private ReSpawn module;
	
	public PlayerRespawnEventListener(ReSpawn module) {
		this.module = module;
	}
	
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent event) {		
		String spawnPos = (String) this.module.getModuleFileHandler().getModuleConfiguration().getValue("spawnPos"); 
		if(spawnPos == null) {
			return;
		}
		
		String[] parts = spawnPos.split(Pattern.quote("<-->"));
		World w = Bukkit.getWorld(parts[0]);
		int x = Integer.valueOf(parts[1]);
		int y = Integer.valueOf(parts[2]);
		int z = Integer.valueOf(parts[3]);
		
		Location l = new Location(w, x, y, z);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				event.getPlayer().teleport(l);
			}
		}.runTaskLater(this.module.getCore(), 1);
	}
}
