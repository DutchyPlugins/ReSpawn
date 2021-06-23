package dev.array21.respawn;

import org.bukkit.permissions.PermissionDefault;

import dev.array21.dutchycore.DutchyCore;
import dev.array21.dutchycore.annotations.RegisterModule;
import dev.array21.dutchycore.module.PluginModule;
import dev.array21.respawn.commands.SetRespawnCommandListener;
import dev.array21.respawn.listeners.PlayerRespawnEventListener;

@RegisterModule(name = "ReSpawn", version = "@VERSION@", author = "Dutchy76", infoUrl = "https://github.com/DutchyPlugins/ReSpawn")
public class ReSpawn extends PluginModule {

	@Override
	public void enable(DutchyCore plugin) {
		super.logInfo("Loading ReSpawn...");
		
		super.registerCommand("setrespawn", new SetRespawnCommandListener(this), this);
		super.registerPermissionNode("respawn.setspawn", PermissionDefault.OP, null, null);
		
		super.registerEventListener(new PlayerRespawnEventListener(this));
		
		super.logInfo("Loaded.");
	}	
}
