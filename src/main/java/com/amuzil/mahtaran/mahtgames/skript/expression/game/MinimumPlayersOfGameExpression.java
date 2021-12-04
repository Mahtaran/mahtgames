package com.amuzil.mahtaran.mahtgames.skript.expression.game;

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

@Name("Minimum players of game")
@Description({ "Returns the minimum players of a game. Can be set." })
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate game \"Test\"",
	"\t\tset game minimum player count of last game created to 1",
	"\t\tbroadcast \"The game minimum player count is %game minimum players of last game created%\""
})
@Since("0.0.1-alpha")
public class MinimumPlayersOfGameExpression extends SimplePropertyExpression<Game, Integer> {
	public static void register() {
		Skript.registerExpression(MinimumPlayersOfGameExpression.class, Integer.class, ExpressionType.PROPERTY,
			"[the] game min[imum] player[s] [count] of %game%",
			"%game%'[s] game min[imum] player[s] [count]"
		);
	}

	@Override
	public Integer convert(Game game) {
		return game.getMinPlayers();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE)
			return new Class[] { Integer.class };
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Game game : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				game.setMinPlayers((int) delta[0]);
			} else if (mode == Changer.ChangeMode.ADD) {
				game.setMinPlayers(game.getMaxPlayers() + (int) delta[0]);
			} else if (mode == Changer.ChangeMode.REMOVE) {
				game.setMinPlayers(game.getMaxPlayers() - (int) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
				game.setMinPlayers(0);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "game minimum player count";
	}

	@Override
	@NotNull
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
}
