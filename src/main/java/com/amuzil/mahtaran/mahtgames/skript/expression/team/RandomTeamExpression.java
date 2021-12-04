package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.Team;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.Random;

@Name("Random Team")
@Description("Return a random team from a game")
@Examples({
	"command random:",
	"\ttrigger:",
	"\t\tset {_random} to random team of game \"test\"",
	"\t\tsend \"You joined the team %{_random}%\""
})
@NonNullByDefault
public class RandomTeamExpression extends SimplePropertyExpression<Game, Team> {
	private static final Random RANDOM = new Random();

	public static void register() {
		Skript.registerExpression(RandomTeamExpression.class, Team.class, ExpressionType.PROPERTY,
			"[a] random team (of|from|for|in) %game%"
		);
	}

	@Override
	public Team convert(Game game) {
		Team[] teams = game.getTeams().values().toArray(new Team[0]);
		int random = RANDOM.nextInt(teams.length);
		return teams[random];
	}

	@Override
	protected String getPropertyName() {
		return "random team";
	}

	@Override
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}
}
