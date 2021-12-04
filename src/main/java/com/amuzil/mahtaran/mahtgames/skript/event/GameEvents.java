package com.amuzil.mahtaran.mahtgames.skript.event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.event.bukkit.*;

public class GameEvents {
	public static void register() {
		Skript.registerEvent("Game Ready Event", SimpleEvent.class, GameReadyEvent.class,
			"[mini[(-| )]]game ready [to start]"
		);
		EventValues.registerEventValue(GameReadyEvent.class, Game.class, new Getter<Game, GameReadyEvent>() {
			@Override
			public Game get(GameReadyEvent event) {
				return event.getGame();
			}
		}, 0);

		Skript.registerEvent("Game Created Event", SimpleEvent.class, GameCreatedEvent.class,
			"[mini[(-| )]]game create[d]"
		);
		EventValues.registerEventValue(GameCreatedEvent.class, Game.class, new Getter<Game, GameCreatedEvent>() {
			@Override
			public Game get(GameCreatedEvent event) {
				return event.getGame();
			}
		}, 0);

		Skript.registerEvent("Game Deleted Event", SimpleEvent.class, GameDeletedEvent.class,
			"[mini[(-| )]]game delete[d]"
		);
		EventValues.registerEventValue(GameDeletedEvent.class, Game.class, new Getter<Game, GameDeletedEvent>() {
			@Override
			public Game get(GameDeletedEvent event) {
				return event.getGame();
			}
		}, 0);

		Skript.registerEvent("Game Started Event", SimpleEvent.class, GameStartedEvent.class,
			"[mini[(-| )]]game start[ed]"
		);
		EventValues.registerEventValue(GameStartedEvent.class, Game.class, new Getter<Game, GameStartedEvent>() {
			@Override
			public Game get(GameStartedEvent event) {
				return event.getGame();
			}
		}, 0);

		Skript.registerEvent("Game Stopped Event", SimpleEvent.class, GameStoppedEvent.class,
			"[mini[(-| )]]game stop[ped]"
		);
		EventValues.registerEventValue(GameStoppedEvent.class, Game.class, new Getter<Game, GameStoppedEvent>() {
			@Override
			public Game get(GameStoppedEvent event) {
				return event.getGame();
			}
		}, 0);
	}
}
