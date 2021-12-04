package com.amuzil.mahtaran.mahtgames.event;

import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGamesConfig;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import com.amuzil.mahtaran.mahtgames.event.bukkit.GameReadyEvent;
import com.amuzil.mahtaran.mahtgames.event.bukkit.PlayerJoinGameEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinGame implements Listener {
	public PlayerJoinGame(MahtGamesPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoinGame(PlayerJoinGameEvent event) {
		Player player = event.getPlayer();
		Game game = event.getGame();

		if (MahtGamesConfig.SEND_MESSAGES) {
			game.sendMessage(Game.MessageKey.JOIN_GAME, player);
		}
		if (game.getPlayers().size() == game.getMinPlayers()) {
			Bukkit.getServer().getPluginManager().callEvent(new GameReadyEvent(game));
		}
		if (game.getPlayers().size() == game.getMaxPlayers()) {
			Bukkit.getServer().getPluginManager().callEvent(new GameReadyEvent(game));
		}
	}
}
