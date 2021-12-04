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
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Winner of game")
@Description("Returns winner of a game. Can be set.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_game} to game \"test\"",
	"\t\tset winner of {_game} to team \"red\" of {_game}",
	"\t\tbroadcast \"The winner of %{_game}% game is the red team!"
})
@Since("0.0.1-alpha")
public class WinnerOfGameExpression extends SimplePropertyExpression<Game, Team> {
	public static void register() {
		Skript.registerExpression(WinnerOfGameExpression.class, Team.class, ExpressionType.PROPERTY,
			"[the] winner of %game%",
			"[the] %game%'[s] winner"
		);
	}

	@Override
	public Team convert(Game game) {
		return game.getWinner();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
			return new Class[] { Team.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Game game : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				game.setWinner((Team) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
				game.setWinner(null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "winner";
	}


	@Override
	@NotNull
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}
}
