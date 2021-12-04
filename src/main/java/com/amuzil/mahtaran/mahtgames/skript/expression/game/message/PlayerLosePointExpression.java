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

@Name("Player Lose Point Message of game")
@Description("Returns the player lose point message of a game. Can be set.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_game} to game \"test\"",
	"\t\tset player lose point message of {_game} to \"You lost the point for your team!\"",
	"\t\tbroadcast player lose point message of {_game}"
})
@Since("0.0.1-alpha")
public class PlayerLosePointExpression extends SimplePropertyExpression<Game, String> {
	public static void register() {
		Skript.registerExpression(PlayerLosePointExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] player lose point[s] message of %game%",
			"[the] %game%'[s] player lose point[s] message"
		);
	}

	@Override
	public String convert(Game game) {
		return game.getMessage(Game.MessageKey.LOSE_POINT, Game.MessageContext.PLAYER);
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
				game.setMessage(Game.MessageKey.LOSE_POINT, Game.MessageContext.PLAYER, (String) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET) {
				game.setMessage(Game.MessageKey.LOSE_POINT, Game.MessageContext.PLAYER, "${player} lost a point for the ${team} team!");
			} else if (mode == Changer.ChangeMode.DELETE) {
				game.setMessage(Game.MessageKey.LOSE_POINT, Game.MessageContext.PLAYER, null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "global lose point message";
	}


	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
