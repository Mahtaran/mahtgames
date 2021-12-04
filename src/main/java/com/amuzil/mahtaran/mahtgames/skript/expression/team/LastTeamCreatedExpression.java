package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.Team;
import com.amuzil.mahtaran.mahtgames.skript.effect.team.CreateTeamEffect;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNullByDefault;

import javax.annotation.Nullable;

@Name("Last team created")
@Description("Returns the last team created.")
@Examples({
	"command team:",
	"\ttrigger:",
	"\t\tcreate team \"red\" for game \"Test\"",
	"\t\tset {_team} to last team created",
	"\t\tbroadcast \"You created a new team named %name of {_team}%\""
})
@Since("0.0.1-alpha")
// TODO use everywhere
@NonNullByDefault
public class LastTeamCreatedExpression extends SimpleExpression<Team> {
	public static void register() {
		Skript.registerExpression(LastTeamCreatedExpression.class, Team.class, ExpressionType.SIMPLE,
			"[the] last team created"
		);
	}

	@Override
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected Team[] get(Event event) {
		return CreateTeamEffect.getLastCreatedTeam().map(team -> new Team[] { team }).orElse(new Team[0]);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "the last team created \"" + CreateTeamEffect.getLastCreatedTeam().map(Team::getInternalName).orElse("null") + "\"";
	}

}
