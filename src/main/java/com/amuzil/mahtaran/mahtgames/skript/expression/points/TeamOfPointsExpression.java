package com.amuzil.mahtaran.mahtgames.skript.expression.points;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Points;
import com.amuzil.mahtaran.mahtgames.Team;
import org.jetbrains.annotations.NotNull;

@Name("Team of Points")
@Description("Returns the team of target points.")
@Examples({
	"command /points:\n" +
		"\ttrigger:\n" +
		"\t\tcreate game \"test\"\n" +
		"\t\tcreate team \"red\" in game \"test\"\n" +
		"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
		"\t\tset {_points} to team of points of team \"red\" in game \"test\"\n" +
		"\t\tbroadcast \"%{_points}%\""
})
@Since("0.0.1-alpha")
public class TeamOfPointsExpression extends SimplePropertyExpression<Points, Team> {
	public static void register() {
		Skript.registerExpression(TeamOfPointsExpression.class, Team.class, ExpressionType.PROPERTY,
			"[the] team of %points%",
			"[the] %points%'[s] team"
		);
	}

	@Override
	public Team convert(Points points) {
		return points.getTeam();
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "team";
	}

	@Override
	@NotNull
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}
}
