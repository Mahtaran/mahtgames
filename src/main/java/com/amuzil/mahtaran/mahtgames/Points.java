package com.amuzil.mahtaran.mahtgames;

import org.bukkit.entity.Player;

public class Points {
	private int points;
	private int advantage;
	// TODO remove?
	private Game game;
	private Team team;
	private Player author;

	public Points(int points) {
		this.points = points;
		this.advantage = points;
	}

	public Points(int points, Player author) {
		this.points = points;
		this.advantage = points;
		this.author = author;
		this.game = MahtGames.getGameOfPlayer(author);
		if (game != null) {
			this.team = game.getTeamOfPlayer(author);
		}
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getAdvantage() {
		return advantage;
	}

	public void setAdvantage(int advantage) {
		this.advantage = advantage;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Player getAuthor() {
		return author;
	}

	public void setAuthor(Player author) {
		this.author = author;
	}
}
