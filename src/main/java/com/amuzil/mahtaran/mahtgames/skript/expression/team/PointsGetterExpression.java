package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Points;
import com.amuzil.mahtaran.mahtgames.Team;
import org.eclipse.jdt.annotation.NonNullByDefault;

@Name("Points of team")
@Description("Returns points of a team.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_team} to team \"red\" from \"test\"",
	"\t\tadd 1 point to {_team}",
	"\t\tbroadcast \"The team %{_team}% has %points of {_team}% points!\" "
})
@Since("0.0.1-alpha")
@NonNullByDefault
public class PointsGetterExpression extends SimplePropertyExpression<Team, Points> {
	public static void register() {
		Skript.registerExpression(PointsGetterExpression.class, Points.class, ExpressionType.PROPERTY,
			"[the] point[s] of %team%",
			"[the] %team%'[s] point[s]"
		);
	}

	@Override
	public Points convert(Team team) {
		return team.getPoints();
	}

	@Override
	protected String getPropertyName() {
		return "points";
	}

	@Override
	public Class<? extends Points> getReturnType() {
		return Points.class;
	}
}
