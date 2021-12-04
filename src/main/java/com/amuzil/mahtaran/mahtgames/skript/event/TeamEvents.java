package com.amuzil.mahtaran.mahtgames.skript.event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.Team;
import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamCreatedEvent;
import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamDeletedEvent;
import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamLosePointsEvent;
import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamWinPointsEvent;

public class TeamEvents {
	public static void register() {
		// Team Created Event
		Skript.registerEvent("Team Created Event", SimpleEvent.class, TeamCreatedEvent.class,
				"team created"
		);
		EventValues.registerEventValue(TeamCreatedEvent.class, Game.class, new Getter<Game, TeamCreatedEvent>() {
			@Override
			public Game get(TeamCreatedEvent event) {
				return event.getTeam().getGame();
			}
		}, 0);
		EventValues.registerEventValue(TeamCreatedEvent.class, Team.class, new Getter<Team, TeamCreatedEvent>() {

			@Override
			public Team get(TeamCreatedEvent event) {
				return event.getTeam();
			}
		}, 0);

		// Team Deleted Event
		Skript.registerEvent("Team Deleted Event", SimpleEvent.class, TeamDeletedEvent.class,
				"team deleted"
		);
		EventValues.registerEventValue(TeamDeletedEvent.class, Game.class, new Getter<Game, TeamDeletedEvent>() {
			@Override
			public Game get(TeamDeletedEvent event) {
				return event.getTeam().getGame();
			}
		}, 0);
		EventValues.registerEventValue(TeamDeletedEvent.class, Team.class, new Getter<Team, TeamDeletedEvent>() {

			@Override
			public Team get(TeamDeletedEvent event) {
				return event.getTeam();
			}
		}, 0);

		// Team Score Point Event
		Skript.registerEvent("Team Score Point Event", SimpleEvent.class, TeamWinPointsEvent.class,
				"team (win|score) [a] point[s]"
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
		EventValues.registerEventValue(TeamWinPointsEvent.class, Integer.class, new Getter<Integer, TeamWinPointsEvent>() {
			@Override
			public Integer get(TeamWinPointsEvent event) {
				return event.getPoints().getPoints();
			}
		}, 0);

		// Team Lose Point Event
		Skript.registerEvent("Team Lose Point Event", SimpleEvent.class, TeamLosePointsEvent.class,
				"team lose [a] point[s]"
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
		EventValues.registerEventValue(TeamLosePointsEvent.class, Integer.class, new Getter<Integer, TeamLosePointsEvent>() {
			@Override
			public Integer get(TeamLosePointsEvent event) {
				return event.getPoints().getPoints();
			}
		}, 0);
	}
}