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
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Maximum players of team")
@Description({ "Returns the maximum players of a team. Can be set." })
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate team \"red\" for game \"Test\"",
	"\t\tset team maximum players count of last team created to 5",
	"\t\tbroadcast \"The team maximum player count is %team maximum players of last team created%\""
})
@Since("0.0.1-alpha")
public class MaximumPlayersOfTeamExpression extends SimplePropertyExpression<Team, Integer> {
	public static void register() {
		Skript.registerExpression(MaximumPlayersOfTeamExpression.class, Integer.class, ExpressionType.PROPERTY,
			"[the] team max[imum] player[s] [count] of %team%",
			"%team%'[s] team max[imum] player[s] [count]"
		);
	}

	@Override
	public Integer convert(Team team) {
		return team.getMaxPlayers();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE)
			return new Class[] { Integer.class };
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Team team : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				team.setMaxPlayers((int) delta[0]);
			} else if (mode == Changer.ChangeMode.ADD) {
				team.setMaxPlayers(team.getMaxPlayers() + (int) delta[0]);
			} else if (mode == Changer.ChangeMode.REMOVE) {
				team.setMaxPlayers(team.getMaxPlayers() - (int) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
				team.setMaxPlayers(0);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "team maximum player count";
	}

	@Override
	@NotNull
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
}
