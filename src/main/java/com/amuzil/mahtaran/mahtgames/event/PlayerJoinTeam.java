package com.amuzil.mahtaran.mahtgames.event;

import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGamesConfig;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import com.amuzil.mahtaran.mahtgames.Team;
import com.amuzil.mahtaran.mahtgames.event.bukkit.PlayerJoinTeamEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinTeam implements Listener {
	public PlayerJoinTeam(MahtGamesPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoinTeam(PlayerJoinTeamEvent event) {
		Player player = event.getPlayer();
		Team team = event.getTeam();
		Game game = team.getGame();

		if (MahtGamesConfig.SEND_MESSAGES) {
			game.sendMessage(Game.MessageKey.JOIN_TEAM, player);
		}

		if (MahtGamesConfig.AUTOMATICALLY_MANAGE) {
			// TODO per team lobby
			player.teleport(game.getLobby());
			if (game.canStart()) {
				// TODO autostart config
				game.start();
			}
		}
	}

}
