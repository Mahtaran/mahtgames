package com.amuzil.mahtaran.mahtgames;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface PlayerManager {
	int getMinPlayers();

	void setMinPlayers(int minPlayers);

	int getMaxPlayers();

	void setMaxPlayers(int maxPlayers);

	Collection<Player> getPlayers();

	void join(Player player);

	void leave(Player player);

	boolean hasPlayer(Player player);

	boolean isFull();

	default void resetPlayers() {
		for (Player player : getPlayers()) {
			leave(player);
		}
	}
}
