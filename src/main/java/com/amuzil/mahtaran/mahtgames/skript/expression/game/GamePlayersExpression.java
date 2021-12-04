package com.amuzil.mahtaran.mahtgames.skript.expression.game;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Players list of game")
@Description("Returns the players list of a game. Can be reset.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate game \"Test\"",
	"\t\tadd player to last game created",
	"\t\tbroadcast \"All players of game %last game created%: %players of last game created%"
})
@Since("0.0.1-alpha")
public class GamePlayersExpression extends SimpleExpression<Player> {
	public static void register() {
		Skript.registerExpression(GamePlayersExpression.class, Player.class, ExpressionType.SIMPLE,
			"[the] players (of|in|from) %game%",
			"[the] %game%'[s] players"
		);
	}

	private Expression<Game> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, @NotNull SkriptParser.ParseResult parseResult) {
		this.gameExpression = (Expression<Game>) expressions[0];
		return true;
	}

	@Override
	protected Player[] get(@NotNull Event event) {
		Game game = gameExpression.getSingle(event);
		if (game != null) {
			return game.getPlayers().toArray(new Player[0]);
		} else {
			return null;
		}
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.RESET) {
			return new Class[] { Player.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.RESET) {
			for (Object object : delta) {
				if (object instanceof Game) {
					((Game) object).resetPlayers();
				}
			}
		}
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	@NotNull
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		if (gameExpression == null) {
			return "the game expression is null";
		}
		Game game = gameExpression.getSingle(event);
		if (game != null) {
			return "players of \"" + game.getInternalName() + "\"";
		} else {
			return "the game expression is null";
		}
	}
}