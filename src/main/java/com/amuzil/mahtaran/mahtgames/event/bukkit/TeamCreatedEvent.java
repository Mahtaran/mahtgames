package com.amuzil.mahtaran.mahtgames.event.bukkit;

import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TeamCreatedEvent extends Event {
	public static final HandlerList handlers = new HandlerList();

	private final Team team;

	public TeamCreatedEvent(Team team) {
		this.team = team;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	@NotNull
	public HandlerList getHandlers() {
		return getHandlerList();
	}

	public Team getTeam() {
		return team;
	}
}
