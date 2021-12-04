package com.amuzil.mahtaran.mahtgames.event.bukkit;

import com.amuzil.mahtaran.bukkit.event.CancellableEvent;
import com.amuzil.mahtaran.mahtgames.Points;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TeamWinPointsEvent extends CancellableEvent {
	private static final HandlerList handlers = new HandlerList();

	private final Points points;

	public TeamWinPointsEvent(Points points) {
		super();
		this.points = points;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	@NotNull
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public Points getPoints() {
		return points;
	}
}
