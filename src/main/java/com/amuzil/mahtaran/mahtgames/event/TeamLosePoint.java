package com.amuzil.mahtaran.mahtgames.event;

import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGamesConfig;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import com.amuzil.mahtaran.mahtgames.Points;
import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamLosePointsEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TeamLosePoint implements Listener {
	public TeamLosePoint(MahtGamesPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onTeamLosePoint(TeamLosePointsEvent event) {
		Points points = event.getPoints();
		Player author = points.getAuthor();

		if (MahtGamesConfig.SEND_MESSAGES) {
			points.getGame().sendMessage(Game.MessageKey.LOSE_POINT, author);
		}
	}
}
