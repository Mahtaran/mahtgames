package com.amuzil.mahtaran.mahtgames.event.bukkit;

import com.amuzil.mahtaran.bukkit.event.CancellableEvent;
import com.amuzil.mahtaran.mahtgames.Game;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameStartedEvent extends CancellableEvent {
	public static final HandlerList handlers = new HandlerList();

	private final Game game;

	public GameStartedEvent(Game game) {
		super();
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
