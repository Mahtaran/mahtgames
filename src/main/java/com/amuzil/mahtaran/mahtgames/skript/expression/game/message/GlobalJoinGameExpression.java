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

@Name("Global Join Message of game")
@Description("Returns the global join message of a game. Can be set.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_game} to game \"test\"",
	"\t\tset global join game message of {_game} to \"You joined the game %{_game}%!\"",
	"\t\tbroadcast global join game message of {_game}"
})
@Since("0.0.1-alpha")
public class GlobalJoinGameExpression extends SimplePropertyExpression<Game, String> {
	public static void register() {
		Skript.registerExpression(GlobalJoinGameExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] global (enter|join) [game] message of %game%",
			"[the] %game%'[s] global (enter|join) [game] message"
		);
	}

	@Override
	public String convert(Game game) {
		return game.getMessage(Game.MessageKey.JOIN_GAME, Game.MessageContext.GLOBAL);
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
			mode == Changer.ChangeMode.DELETE) {
			return new Class[] { String.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Game game : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				game.setMessage(Game.MessageKey.JOIN_GAME, Game.MessageContext.GLOBAL, (String) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET) {
				game.setMessage(Game.MessageKey.JOIN_GAME, Game.MessageContext.GLOBAL, "${player} joined the game");
			} else if (mode == Changer.ChangeMode.DELETE) {
				game.setMessage(Game.MessageKey.JOIN_GAME, Game.MessageContext.GLOBAL, null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "global join game message";
	}

	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
