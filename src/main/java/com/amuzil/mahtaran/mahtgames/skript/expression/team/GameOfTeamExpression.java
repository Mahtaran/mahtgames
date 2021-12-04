package com.amuzil.mahtaran.mahtgames.skript.expression.team;

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
import org.jetbrains.annotations.NotNull;

@Name("Game of team")
@Description({ "Returns the game of team." })
@Examples({
	"command /test:\n",
	"\ttrigger:\n",
	"\t\tcreate game \"test\"\n",
	"\t\tcreate team \"red\" in game \"test\"\n",
	"\t\tset {_team} to game of team \"red\" in game \"test\"\n",
	"\t\tbroadcast \"%{_team}%\""
})
@Since("0.0.1-alpha")
public class GameOfTeamExpression extends SimplePropertyExpression<Team, Game> {
	public static void register() {
		Skript.registerExpression(GameOfTeamExpression.class, Game.class, ExpressionType.PROPERTY,
			"[the] [mini[(-| )]]game of %team%",
			"%team%'s [mini[(-| )]]game"
		);
	}

	@Override
	public Game convert(Team team) {
		return team.getGame();
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
