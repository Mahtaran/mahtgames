package com.amuzil.mahtaran.mahtgames.skript.event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.Team;
import com.amuzil.mahtaran.mahtgames.event.bukkit.*;
import org.bukkit.entity.Player;

public class PlayerEvents {
	public static void register() {
		// Player Join CommandGameSpigot Event
		Skript.registerEvent("Player Join Game Event", SimpleEvent.class, PlayerJoinGameEvent.class,
				"player join [a] [mini[(-| )]]game"
		);
		EventValues.registerEventValue(PlayerJoinGameEvent.class, Player.class, new Getter<Player, PlayerJoinGameEvent>() {
			@Override
			public Player get(PlayerJoinGameEvent event) {
				return event.getPlayer();
			}
		}, 0);
		EventValues.registerEventValue(PlayerJoinGameEvent.class, Game.class, new Getter<Game, PlayerJoinGameEvent>() {
			@Override
			public Game get(PlayerJoinGameEvent event) {
				return event.getGame();
			}
		}, 0);

		// Player Leave CommandGameSpigot Event
		Skript.registerEvent("Player Leave Game Event", SimpleEvent.class, PlayerLeaveGameEvent.class,
				"player leave [a] [mini[(-| )]]game"
		);
		EventValues.registerEventValue(PlayerLeaveGameEvent.class, Player.class, new Getter<Player, PlayerLeaveGameEvent>() {
			@Override
			public Player get(PlayerLeaveGameEvent event) {
				return event.getPlayer();
			}
		}, 0);
		EventValues.registerEventValue(PlayerLeaveGameEvent.class, Game.class, new Getter<Game, PlayerLeaveGameEvent>() {
			@Override
			public Game get(PlayerLeaveGameEvent event) {
				return event.getGame();
			}
		}, 0);

		// Player Join Team Event
		Skript.registerEvent("Player Join Team Event", SimpleEvent.class, PlayerJoinTeamEvent.class,
				"player join [a] team"
		);
		EventValues.registerEventValue(PlayerJoinTeamEvent.class, Player.class, new Getter<Player, PlayerJoinTeamEvent>() {
			@Override
			public Player get(PlayerJoinTeamEvent event) {
				return event.getPlayer();
			}
		}, 0);
		EventValues.registerEventValue(PlayerJoinTeamEvent.class, Game.class, new Getter<Game, PlayerJoinTeamEvent>() {
			@Override
			public Game get(PlayerJoinTeamEvent event) {
				return event.getTeam().getGame();
			}
		}, 0);
		EventValues.registerEventValue(PlayerJoinTeamEvent.class, Team.class, new Getter<Team, PlayerJoinTeamEvent>() {
			@Override
			public Team get(PlayerJoinTeamEvent event) {
				return event.getTeam();
			}
		}, 0);

		// Player Leave Team Event
		Skript.registerEvent("Player Leave Team Event", SimpleEvent.class, PlayerLeaveTeamEvent.class,
				"player leave [a] team"
		);
		EventValues.registerEventValue(PlayerLeaveTeamEvent.class, Player.class, new Getter<Player, PlayerLeaveTeamEvent>() {
			@Override
			public Player get(PlayerLeaveTeamEvent event) {
				return event.getPlayer();
			}
		}, 0);
		EventValues.registerEventValue(PlayerLeaveTeamEvent.class, Game.class, new Getter<Game, PlayerLeaveTeamEvent>() {
			@Override
			public Game get(PlayerLeaveTeamEvent event) {
				return event.getTeam().getGame();
			}
		}, 0);
		EventValues.registerEventValue(PlayerLeaveTeamEvent.class, Team.class, new Getter<Team, PlayerLeaveTeamEvent>() {
			@Override
			public Team get(PlayerLeaveTeamEvent event) {
				return event.getTeam();
			}
		}, 0);

		// Player Score Point Event
		Skript.registerEvent("Player Score Point Event", SimpleEvent.class, TeamWinPointsEvent.class,
				"player (win|score) [a] point[s]"
		);
		EventValues.registerEventValue(TeamWinPointsEvent.class, Game.class, new Getter<Game, TeamWinPointsEvent>() {
			@Override
			public Game get(TeamWinPointsEvent event) {
				return event.getPoints().getGame();
			}
		}, 0);
		EventValues.registerEventValue(TeamWinPointsEvent.class, Team.class, new Getter<Team, TeamWinPointsEvent>() {
			@Override
			public Team get(TeamWinPointsEvent event) {
				return event.getPoints().getTeam();
			}
		}, 0);
		EventValues.registerEventValue(TeamWinPointsEvent.class, Player.class, new Getter<Player, TeamWinPointsEvent>() {
			@Override
			public Player get(TeamWinPointsEvent event) {
				return event.getPoints().getAuthor();
			}
		}, 0);
		EventValues.registerEventValue(TeamWinPointsEvent.class, Integer.class, new Getter<Integer, TeamWinPointsEvent>() {
			@Override
			public Integer get(TeamWinPointsEvent event) {
				return event.getPoints().getPoints();
			}
		}, 0);

		// Player Lose Point Event
		Skript.registerEvent("Player Lose Point Event", SimpleEvent.class, TeamLosePointsEvent.class,
				"player lose [a] point[s]"
		);
		EventValues.registerEventValue(TeamLosePointsEvent.class, Game.class, new Getter<Game, TeamLosePointsEvent>() {
			@Override
			public Game get(TeamLosePointsEvent event) {
				return event.getPoints().getGame();
			}
		}, 0);
		EventValues.registerEventValue(TeamLosePointsEvent.class, Team.class, new Getter<Team, TeamLosePointsEvent>() {
			@Override
			public Team get(TeamLosePointsEvent event) {
				return event.getPoints().getTeam();
			}
		}, 0);
		EventValues.registerEventValue(TeamLosePointsEvent.class, Player.class, new Getter<Player, TeamLosePointsEvent>() {
			@Override
			public Player get(TeamLosePointsEvent event) {
				return event.getPoints().getAuthor();
			}
		}, 0);
		EventValues.registerEventValue(TeamLosePointsEvent.class, Integer.class, new Getter<Integer, TeamLosePointsEvent>() {
			@Override
			public Integer get(TeamLosePointsEvent event) {
				return event.getPoints().getPoints();
			}
		}, 0);

	}
}
