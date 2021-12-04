package com.amuzil.mahtaran.mahtgames.event.bukkit;

import com.amuzil.mahtaran.bukkit.event.CancellableEvent;
import com.amuzil.mahtaran.mahtgames.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinGameEvent extends CancellableEvent {
	private static final HandlerList handlers = new HandlerList();

	private final Game game;
	private final Player player;

	public PlayerJoinGameEvent(Game game, Player player) {
		super();
		this.player = player;
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

	public Player getPlayer() {
		return player;
	}
}