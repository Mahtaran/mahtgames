package com.amuzil.mahtaran.mahtgames;

import com.amuzil.mahtaran.mahtgames.event.bukkit.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.eclipse.jdt.annotation.Nullable;

import java.util.*;

public class Game implements PlayerManager {
	private Team winner;
	private World world;

	public Team getWinner() {
		return winner;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
	}

	public boolean hasWinner() {
		return winner != null;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public enum MessageKey {
		JOIN_GAME("join"), LEAVE_GAME("leave"), JOIN_TEAM("join_team"), LEAVE_TEAM("leave_team"), LOSE_POINT("lose_point"), WIN_POINT("win_point");

		private final String value;

		MessageKey(String value) {
			this.value = value;
		}
	}

	public enum MessageContext {
		GLOBAL("global"), PLAYER("player");

		private final String value;

		MessageContext(String value) {
			this.value = value;
		}
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean canStart() {
		return players.size() >= minPlayers && teams.values().stream().allMatch(Team::enoughPlayers);
	}

	public enum State {
		WAITING, STARTING, RUNNING, ENDED
	}

	private final String internalName;
	private State state;
	private final Map<String, Team> teams;
	private final Set<Player> players;
	private Location lobby;
	private Location spawn;
	private String displayName;
	private int minPlayers;
	private int maxPlayers;
	private KeyedContextMessages messages;

	private void initialiseMessages() {
		messages = new KeyedContextMessages();
		setMessage(MessageKey.JOIN_GAME, MessageContext.GLOBAL, "{player} joined the game");
		setMessage(MessageKey.JOIN_GAME, MessageContext.PLAYER, "You joined the game ${game}");
		setMessage(MessageKey.LEAVE_GAME, MessageContext.GLOBAL, "{player} left the game");
		setMessage(MessageKey.LEAVE_GAME, MessageContext.PLAYER, "You left the game ${game}");
		setMessage(MessageKey.JOIN_TEAM, MessageContext.GLOBAL, "{player} joined the {team} team");
		setMessage(MessageKey.JOIN_TEAM, MessageContext.PLAYER, "You joined the ${team} team");
		setMessage(MessageKey.LEAVE_TEAM, MessageContext.GLOBAL, "{player} left the {team} team");
		setMessage(MessageKey.LEAVE_TEAM, MessageContext.PLAYER, "You left the ${team} team");
		setMessage(MessageKey.LOSE_POINT, MessageContext.GLOBAL, "{player} lost a point for the ${team} team");
		setMessage(MessageKey.LOSE_POINT, MessageContext.PLAYER, "You lost a point for the ${team} team");
		setMessage(MessageKey.WIN_POINT, MessageContext.GLOBAL, "{player} won a point for the ${team} team");
		setMessage(MessageKey.WIN_POINT, MessageContext.PLAYER, "You won a point for the ${team} team");
	}

	public void setMessage(MessageKey key, MessageContext context, String message) {
		messages.set(key.value, context.value, message);
	}

	@Nullable
	public String getMessage(MessageKey key, MessageContext context) {
		return messages.get(key.value, context.value);
	}

	@Nullable
	public String formatMessage(MessageKey key, MessageContext context, Player player) {
		String messageFormat = getMessage(key, context);
		if (messageFormat == null) {
			return null;
		}
		messageFormat = messageFormat.replace("${game}", getDisplayName());
		messageFormat = messageFormat.replace("${team}", Optional.ofNullable(getTeamOfPlayer(player)).map(Team::getDisplayName).orElse("(player not in any team)"));
		messageFormat = messageFormat.replace("${player}", Optional.ofNullable(player).map(Player::getDisplayName).orElse("(no player provided for message)"));
		return messageFormat;
	}

	public void sendMessage(MessageKey key, Player player) {
		String globalMessage = formatMessage(key, MessageContext.GLOBAL, player);
		String playerMessage = formatMessage(key, MessageContext.PLAYER, player);
		if (globalMessage != null) {
			getPlayers().forEach(p -> p.sendMessage(globalMessage));
		}
		if (playerMessage != null) {
			player.sendMessage(playerMessage);
		}
	}

	public Game(String internalName) {
		this.internalName = internalName;
		teams = new HashMap<>();
		players = new HashSet<>();
		state = State.WAITING;
		world = Bukkit.getWorlds().get(0);
		this.displayName = internalName;
		initialiseMessages();
	}

	public String getInternalName() {
		return internalName;
	}

	public void addTeam(Team team) {
		teams.put(team.getInternalName(), team);
	}

	public Team createTeam(String teamName) {
		Team team = new Team(this, teamName);
		addTeam(team);
		Bukkit.getPluginManager().callEvent(new TeamCreatedEvent(team));
		return team;
	}

	public Team getTeam(String teamName) {
		return teams.get(teamName);
	}

	public Team removeTeam(String teamName) {
		Team removedTeam = teams.remove(teamName);
		removedTeam.getPlayers().forEach(this::leave);
		Bukkit.getPluginManager().callEvent(new TeamDeletedEvent(removedTeam));
		return removedTeam;
	}

	public boolean teamExists(String teamName) {
		return teams.containsKey(teamName);
	}

	@Override
	public void join(Player player) {
		PlayerJoinGameEvent event = new PlayerJoinGameEvent(this, player);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			Game oldGame = MahtGames.getGameOfPlayer(player);
			if (oldGame != null) {
				oldGame.leave(player);
			}

			players.add(player);
		}
	}

	public void join(Player player, Team team) {
		PlayerJoinTeamEvent event = new PlayerJoinTeamEvent(team, player);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			join(player);

			if (hasPlayer(player)) {
				Team oldTeam = getTeamOfPlayer(player);
				if (oldTeam != null) {
					oldTeam.removePlayer(player);
				}

				team.addPlayer(player);
				player.teleport(lobby);
			}
		}
	}

	public void join(Player player, String teamName) {
		join(player, getTeam(teamName));
	}

	@Override
	public void leave(Player player) {
		Team team = getTeamOfPlayer(player);
		if (team != null) {
			PlayerLeaveTeamEvent event = new PlayerLeaveTeamEvent(team, player);
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				team.removePlayer(player);
			}
		}

		PlayerLeaveGameEvent event = new PlayerLeaveGameEvent(this, player);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			players.remove(player);
		}
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location location) {
		this.lobby = location;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void start() {
		setState(State.STARTING);
		GameStartedEvent event = new GameStartedEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			setState(State.WAITING);
		} else {
			players.forEach(player -> {
				Team team = getTeamOfPlayer(player);
				if (team != null) {
					player.teleport(team.getSpawn());
				} else {
					player.teleport(spawn);
				}
			});
			setState(State.RUNNING);
		}
	}

	public void stop() {
		GameStoppedEvent event = new GameStoppedEvent(this);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			setState(State.RUNNING);
		} else {
			teams.values().forEach(team -> team.getPlayers().forEach(player -> player.teleport(lobby)));
			setState(State.ENDED);
		}
	}

	public Team getTeamOfPlayer(Player player) {
		return teams.values().stream().filter(team -> team.hasPlayer(player)).findFirst().orElse(null);
	}

	public Map<String, Team> getTeams() {
		return teams;
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

	@Override
	public Collection<Player> getPlayers() {
		return players;
	}

	@Override
	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}

	@Override
	public boolean isFull() {
		return players.size() >= maxPlayers;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location location) {
		this.spawn = location;
	}
}
