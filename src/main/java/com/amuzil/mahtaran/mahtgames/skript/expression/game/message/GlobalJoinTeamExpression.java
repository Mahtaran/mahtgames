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
	"\t\tset global join team message of {_game} to \"You joined the ${team} team of the game %{_game}%!\"",
	"\t\tbroadcast global join team message of {_game}"
})
@Since("0.0.1-alpha")
public class GlobalJoinTeamExpression extends SimplePropertyExpression<Game, String> {
	public static void register() {
		Skript.registerExpression(GlobalJoinTeamExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] global (enter|join) team message of %game%",
			"[the] %game%'[s] global (enter|join) team message"
		);
	}

	@Override
	public String convert(Game game) {
		return game.getMessage(Game.MessageKey.JOIN_TEAM, Game.MessageContext.GLOBAL);
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
				game.setMessage(Game.MessageKey.JOIN_TEAM, Game.MessageContext.GLOBAL, (String) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET) {
				game.setMessage(Game.MessageKey.JOIN_TEAM, Game.MessageContext.GLOBAL, "${player} joined the ${team} team");
			} else if (mode == Changer.ChangeMode.DELETE) {
				game.setMessage(Game.MessageKey.JOIN_TEAM, Game.MessageContext.GLOBAL, null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "global join team message";
	}

	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
