package com.amuzil.mahtaran.mahtgames.event.bukkit;

import com.amuzil.mahtaran.bukkit.event.CancellableEvent;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerLeaveTeamEvent extends CancellableEvent {
	private static final HandlerList handlers = new HandlerList();

	private final Team team;
	private final Player player;

	public PlayerLeaveTeamEvent(Team team, Player player) {
		super();
		this.team = team;
		this.player = player;
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

	public Player getPlayer() {
		return player;
	}
}
