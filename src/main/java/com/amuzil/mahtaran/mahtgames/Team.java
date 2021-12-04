package com.amuzil.mahtaran.mahtgames;

import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamLosePointsEvent;
import com.amuzil.mahtaran.mahtgames.event.bukkit.TeamWinPointsEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Team implements PlayerManager {
	private final Game game;
	private final String internalName;
	private int minPlayers;
	private int maxPlayers;
	private Location spawn;
	private final Set<Player> players;
	private String displayName;
	private int objective;
	private Points points;

	public Team(Game game, String internalName) {
		this(game, internalName, 0, 0, null);
	}

	public Team(Game game, String internalName, int minPlayers, int maxPlayers, Location spawn) {
		this.game = game;
		this.internalName = internalName;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		this.spawn = spawn;
		this.players = new HashSet<>();
		this.displayName = internalName;
		this.objective = 5;
		this.points = new Points(0);
	}

	public Game getGame() {
		return game;
	}

	public String getInternalName() {
		return internalName;
	}

	@Override
	public int getMinPlayers() {
		return minPlayers;
	}

	@Override
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	@Override
	public int getMaxPlayers() {
		return maxPlayers;
	}

	@Override
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	@Override
	public Collection<Player> getPlayers() {
		return players;
	}

	public void addPlayer(Player player) {
		if (!isFull()) {
			this.players.add(player);
		}
	}

	public void removePlayer(Player player) {
		players.remove(player);
	}

	@Override
	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}

	@Override
	public boolean isFull() {
		return getPlayers().size() >= maxPlayers;
	}

	public boolean enoughPlayers() {
		return getPlayers().size() >= minPlayers;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void join(Player player) {
		game.join(player, this);
	}

	@Override
	public void leave(Player player) {
		game.leave(player);
	}

	public Team delete() {
		return game.removeTeam(getInternalName());
	}

	public int getObjective() {
		return objective;
	}

	public void setObjective(int objective) {
		this.objective = objective;
	}

	public Points getPoints() {
		return points;
	}

	public void setPoints(Points points) {
		this.points = points;
	}

	public void addPoints(Points points) {
		TeamWinPointsEvent event = new TeamWinPointsEvent(points);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			points.setPoints(this.points.getPoints() + points.getPoints());
			this.points = points;
		}
	}

	public void removePoints(Points points) {
		TeamLosePointsEvent event = new TeamLosePointsEvent(points);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			points.setPoints(this.points.getPoints() - points.getPoints());
			this.points = points;
		}
	}
}