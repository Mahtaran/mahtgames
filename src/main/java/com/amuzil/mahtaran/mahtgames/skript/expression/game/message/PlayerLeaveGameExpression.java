package com.amuzil.mahtaran.mahtgames.skript.expression.game.message;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Game;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Player Leave Message of game")
@Description("Returns the player leave message of a game. Can be set.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_game} to game \"test\"",
	"\t\tset player leave game message of {_game} to \"You left the game %{_game}%!\"",
	"\t\tbroadcast player leave game message of {_game}"
})
@Since("0.0.1-alpha")
public class PlayerLeaveGameExpression extends SimplePropertyExpression<Game, String> {
	public static void register() {
		Skript.registerExpression(PlayerLeaveGameExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] player (exit|leave) [game] message of %game%",
			"[the] %game%'[s] player (exit|leave) [game] message"
		);
	}

	@Override
	public String convert(Game game) {
		return game.getMessage(Game.MessageKey.LEAVE_GAME, Game.MessageContext.PLAYER);
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
			return new Class[] { String.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Game game : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				game.setMessage(Game.MessageKey.LEAVE_GAME, Game.MessageContext.PLAYER, (String) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET) {
				game.setMessage(Game.MessageKey.LEAVE_GAME, Game.MessageContext.PLAYER, "You left the game ${game}");
			} else if (mode == Changer.ChangeMode.DELETE) {
				game.setMessage(Game.MessageKey.LEAVE_GAME, Game.MessageContext.PLAYER, null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "player leave game message";
	}

	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
