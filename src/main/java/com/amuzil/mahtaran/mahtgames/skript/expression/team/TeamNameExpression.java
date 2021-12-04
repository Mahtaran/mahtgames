package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Team;
import org.jetbrains.annotations.NotNull;

@Name("Name of team")
@Description("Returns the name of a team.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate team \"red\" for game \"Test\"",
	"\t\tbroadcast \"The name of team %last team created% is %name of last team created%\""
})
@Since("0.0.1-alpha")
public class TeamNameExpression extends SimplePropertyExpression<Team, String> {
	public static void register() {
		Skript.registerExpression(TeamNameExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] [team] name of %team%",
			"%team%'[s] [team] name"
		);
	}

	@Override
	public String convert(Team team) {
		return team.getInternalName();

	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		return null;
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "team name";
	}


	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
