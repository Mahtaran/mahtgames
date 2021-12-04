package com.amuzil.mahtaran.mahtgames;

import com.amuzil.mahtaran.mahtgames.skript.MahtGamesSkriptHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class MahtGamesPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		MahtGames.setLogger(getLogger());
		// getLogger().info("MahtGamesPlugin enabled!");
		if (Bukkit.getPluginManager().isPluginEnabled("Skript")) {
			new MahtGamesSkriptHook(this).register();
		}
	}

	@Override
	public void onDisable() {
		// getLogger().info("MahtGamesPlugin disabled!");
	}
}
