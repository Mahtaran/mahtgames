package com.amuzil.mahtaran.mahtgames.event;

import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGamesConfig;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import com.amuzil.mahtaran.mahtgames.Team;
import com.amuzil.mahtaran.mahtgames.event.bukkit.PlayerLeaveTeamEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerLeaveTeam implements Listener {
	public PlayerLeaveTeam(MahtGamesPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerLeaveTeam(PlayerLeaveTeamEvent event) {
		Player player = event.getPlayer();
		Team team = event.getTeam();
		Game game = team.getGame();

		if (MahtGamesConfig.SEND_MESSAGES) {
			game.sendMessage(Game.MessageKey.LEAVE_TEAM, player);
		}

		if (MahtGamesConfig.AUTOMATICALLY_MANAGE) {
			if (game.getSpawn() != null) {
				player.teleport(game.getSpawn());
			}

			if (team.getPlayers().size() < team.getMinPlayers()) {
				game.stop();
			}
		}
	}
}
