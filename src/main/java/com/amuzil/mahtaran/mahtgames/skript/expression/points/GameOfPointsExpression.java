package com.amuzil.mahtaran.mahtgames.skript.expression.points;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.Points;
import org.jetbrains.annotations.NotNull;

@Name("Game of points")
@Description({ "Returns the game of points." })
@Examples({
	"command /test:\n",
	"\ttrigger:\n",
	"\t\tcreate game \"test\"\n",
	"\t\tcreate team \"red\" in game \"test\"\n",
	"\t\tadd 5 points to team \"red\" in game \"test\"\n",
	"\t\tset {_points} to game of points of team \"red\" in game \"test\"\n",
	"\t\tbroadcast \"%{_points}%\""
})
@Since("0.0.1-alpha")
public class GameOfPointsExpression extends SimplePropertyExpression<Points, Game> {
	public static void register() {
		Skript.registerExpression(GameOfPointsExpression.class, Game.class, ExpressionType.PROPERTY,
			"[the] [mini[(-| )]]game of %points%",
			"%points%'s [mini[(-| )]]game"
		);
	}

	@Override
	public Game convert(Points points) {
		return points.getGame();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		return null;
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "game";
	}

	@Override
	@NotNull
	public Class<? extends Game> getReturnType() {
		return Game.class;
	}
}
