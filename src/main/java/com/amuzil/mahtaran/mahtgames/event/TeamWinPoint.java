package com.amuzil.mahtaran.mahtgames.event;

import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGamesConfig;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import com.amuzil.mahtaran.mahtgames.Points;
import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamWinPointsEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamWinPoint implements Listener {
	public TeamWinPoint(MahtGamesPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onTeamWinPoint(TeamWinPointsEvent event) {
		Points points = event.getPoints();
		Player author = points.getAuthor();

		if (MahtGamesConfig.SEND_MESSAGES) {
			points.getGame().sendMessage(Game.MessageKey.WIN_POINT, author);
		}
	}
}