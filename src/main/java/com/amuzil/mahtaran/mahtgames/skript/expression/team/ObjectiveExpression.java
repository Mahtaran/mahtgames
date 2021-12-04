package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@Name("Objective of Team")
@Description("Return the objective of the team (how many point the team must to have to win). Can be set.")
@Examples({
	"command objective:",
	"\ttrigger:",
	"\t\tset objective of team of player to 3",
	"\t\tsend \"The new objective of the %team of player% team is 3!\""
})
@Since("0.0.1-alpha")
@NonNullByDefault
public class ObjectiveExpression extends SimplePropertyExpression<Team, Integer> {
	public static void register() {
		register(ObjectiveExpression.class, Integer.class,
			"objective", "team"
		);
	}

	@Override
	public Integer convert(Team team) {
		return team.getObjective();
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
			return new Class[] { Integer.class };
		}
		return null;
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
		for (Team team : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET && delta != null && delta.length > 0) {
				team.setObjective((Integer) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET) {
				team.setObjective(5);
			} else if (mode == Changer.ChangeMode.DELETE) {
				team.setObjective(0);
			}
		}
	}

	@Override
	protected String getPropertyName() {
		return "objective";
	}

	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
}
