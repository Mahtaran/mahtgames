package com.amuzil.mahtaran.mahtgames.event;

import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGamesConfig;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import com.amuzil.mahtaran.mahtgames.event.bukkit.PlayerLeaveGameEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeaveGame implements Listener {
	public PlayerLeaveGame(MahtGamesPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerLeaveGame(PlayerLeaveGameEvent event) {
		Player player = event.getPlayer();
		Game game = event.getGame();

		if (MahtGamesConfig.SEND_MESSAGES) {
			game.sendMessage(Game.MessageKey.LEAVE_GAME, player);
		}
	}
}
