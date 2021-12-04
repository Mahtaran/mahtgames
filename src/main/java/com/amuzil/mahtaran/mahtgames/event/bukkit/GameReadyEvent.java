package com.amuzil.mahtaran.mahtgames.event.bukkit;

import com.amuzil.mahtaran.mahtgames.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameReadyEvent extends Event {
	public static final HandlerList handlers = new HandlerList();

	private final Game game;

	public GameReadyEvent(Game game) {
		this.game = game;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	@NotNull
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public Game getGame() {
		return game;
	}
}
