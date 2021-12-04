package com.amuzil.mahtaran.mahtgames.event;

import com.amuzil.mahtaran.mahtgames.MahtGames;
import com.amuzil.mahtaran.mahtgames.MahtGamesConfig;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Signs implements Listener {
	public Signs(MahtGamesPlugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onGameSignClicked(PlayerInteractEvent event) {
		// TODO permissions
		if (MahtGamesConfig.AUTOMATICALLY_MANAGE && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if (block != null && block.getType().toString().contains("SIGN")) {
				Sign sign = (Sign) block.getState();
				String game = ChatColor.stripColor(sign.getLine(0)).replaceAll("([\\[\\]])*", "");
				if (sign.getLine(1).equals(ChatColor.DARK_AQUA + "Join")) {
					if (sign.getLine(2).replaceAll("\\s", "").isEmpty()) {
						if (event.getPlayer().hasPermission("game.join")) {
							MahtGames.get(game).join(event.getPlayer());
						} else {
							event.getPlayer().sendMessage(ChatColor.RED + "You don't have the permission to join this game.");
						}
					} else {
						String team = ChatColor.stripColor(sign.getLine(2)).replaceAll("\\s+\\S+$", "");
						MahtGames.join(game, event.getPlayer(), team);
					}
				} else if (sign.getLine(1).equals(ChatColor.DARK_AQUA + "Leave")) {
					if (sign.getLine(2).replaceAll("\\s", "").isEmpty()) {
						if (event.getPlayer().hasPermission("game.leave")) {
							MahtGames.leave(event.getPlayer());
						} else {
							event.getPlayer().sendMessage(ChatColor.RED + "You don't have the permission to leave this game.");
						}
					} else {
						String team = ChatColor.stripColor(sign.getLine(2)).replaceAll(" \\\\S*$", "");
						MahtGames.get(game).getTeam(team).removePlayer(event.getPlayer());
					}
				}
			}
		}
	}

	@EventHandler
	public void onGameSignPlaced(SignChangeEvent event) {
		if (MahtGamesConfig.AUTOMATICALLY_MANAGE && "[mahtgames]".equalsIgnoreCase(event.getLine(0))) {
			String game = event.getLine(2);
			if (!MahtGames.exists(game)) {
				event.getPlayer().sendMessage(ChatColor.RED + "[MahtGames] The game " + ChatColor.BLUE + game + ChatColor.RED + " doesn't exist. Remember that you need to create it first.");
			} else {
				event.setLine(0, ChatColor.GOLD + "[" + ChatColor.YELLOW + game + ChatColor.GOLD + "]");
				if ("join".equalsIgnoreCase(event.getLine(1))) {
					event.setLine(1, ChatColor.DARK_AQUA + "Join");
					setSignTeam(event);
				} else if ("leave".equalsIgnoreCase(event.getLine(1))) {
					event.setLine(1, ChatColor.DARK_AQUA + "Leave");
					setSignTeam(event);
				}
			}
		}
	}

	private void setSignTeam(SignChangeEvent event) {
		String teamName = event.getLine(3);
		if (teamName != null && teamName.isEmpty()) {
			event.setLine(2, " ");
		} else {
			event.setLine(2, ChatColor.DARK_PURPLE + teamName + " team");
		}
		event.setLine(3, " ");
	}

}
