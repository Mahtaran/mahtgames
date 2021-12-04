package com.amuzil.mahtaran.mahtgames;

import com.amuzil.mahtaran.mahtgames.event.bukkit.GameCreatedEvent;
import com.amuzil.mahtaran.mahtgames.event.bukkit.GameDeletedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MahtGames {
	private static final Map<String, Game> games = new HashMap<>();
	private static Logger logger;

	public static List<Game> allGames() {
		return (List<Game>) games.values();
	}

	/*@
	ensures exists(game.getInternalName()) == true <==> \result == false;
	@*/
	public static boolean register(Game game) {
		if (exists(game)) {
			return false;
		} else {
			games.put(game.getInternalName(), game);
			return true;
		}
	}

	/*@
	ensures exists(gameName) == true <==> \result == null;
	@*/
	public static Game create(String gameName) {
		Game game = new Game(gameName);
		if (register(game)) {
			Bukkit.getPluginManager().callEvent(new GameCreatedEvent(game));
			return game;
		} else {
			return null;
		}
	}

	public static boolean exists(String gameName) {
		return games.containsKey(gameName);
	}

	public static boolean exists(Game game) {
		return games.containsValue(game);
	}

	public static Game get(String gameName) {
		return games.get(gameName);
	}

	/*@
	ensures exists(gameName) == true <==> \result == true;
	@*/
	// TODO: allow player to be in multiple games at once?
	public static boolean join(String gameName, Player player, String teamName) {
		if (exists(gameName)) {
			get(gameName).join(player, teamName);
			return true;
		} else {
			return false;
		}
	}

	public static boolean leave(Player player) {
		Game game = getGameOfPlayer(player);
		if (game != null) {
			game.leave(player);
			return true;
		} else {
			return false;
		}
	}

	/*@
	ensures exists(gameName) == true <==> \result == true;
	@*/
	public static boolean start(String gameName) {
		if (exists(gameName)) {
			get(gameName).start();
			return true;
		} else {
			return false;
		}
	}

	/*@
	ensures exists(gameName) == true <==> \result == true;
	@*/
	public static boolean stop(String gameName) {
		if (exists(gameName)) {
			get(gameName).stop();
			return true;
		} else {
			return false;
		}
	}

	/*@
	ensures exists(gameName) == true <==> \result == true;
	@*/
	public static boolean delete(String gameName) {
		if (exists(gameName)) {
			Bukkit.getPluginManager().callEvent(new GameDeletedEvent(games.remove(gameName)));
			return true;
		} else {
			return false;
		}
	}

	public static Game getGameOfPlayer(Player player) {
		for (Game game : games.values()) {
			if (game.hasPlayer(player)) {
				return game;
			}
		}
		return null;
	}

	public static void info(String message) {
		logger.info(message);
	}

	public static void warning(String message) {
		logger.warning(message);
	}

	public static void error(String message) {
		logger.severe(message);
	}

	public static void setLogger(Logger logger) {
		MahtGames.logger = logger;
	}
}
