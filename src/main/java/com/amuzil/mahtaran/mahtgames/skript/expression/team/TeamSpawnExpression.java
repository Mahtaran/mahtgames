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
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Spawn of team")
@Description("Returns spawn of a team. Can be set.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_team} to team \"red\" from \"test\"",
	"\t\tset spawn of {_team} to location of player",
	"\t\tbroadcast \"The team %{_team}% has spawn in: %spawn of {_team}%\" "
})
@Since("0.0.1-alpha")
public class TeamSpawnExpression extends SimplePropertyExpression<Team, Location> {
	public static void register() {
		Skript.registerExpression(TeamSpawnExpression.class, Location.class, ExpressionType.PROPERTY,
			"[the] [team] spawn of %team%",
			"%team%'[s] [team] spawn"
		);
	}

	@Override
	public Location convert(Team team) {
		return team.getSpawn();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
			return new Class[] { Location.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Team team : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				team.setSpawn((Location) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
				team.setSpawn(null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "team spawn";
	}

	@Override
	@NotNull
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
}
