package com.amuzil.mahtaran.mahtgames.skript.condition;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Team contains player?")
@Description("Check if a team contains a player")
@Examples({
		"command check:",
		"\ttrigger:",
		"\t\tif team \"red\" of game \"test\" contains player:",
		"\t\t\tsend \"You are in this team!\""
})
@Since("0.0.1-alpha")
public class TeamContainsPlayerCondition extends Condition {
	public static void register() {
		Skript.registerCondition(TeamContainsPlayerCondition.class,
				"%team% contains %player%",
				"%player% is in %team%",
				"%team% does('t| not) contain %player%",
				"%player% is(n't| not) in %team%"
		);
	}

	private Expression<Player> playerExpression;
	private Expression<Team> teamExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		teamExpression = matchedPattern == 0 || matchedPattern == 2 ?
				(Expression<Team>) expressions[0] : (Expression<Team>) expressions[1];
		playerExpression = matchedPattern == 0 || matchedPattern == 2 ?
				(Expression<Player>) expressions[1] : (Expression<Player>) expressions[0];
		setNegated(matchedPattern == 2 || matchedPattern == 3);
		return true;
	}

	@Override
	public boolean check(@NotNull Event event) {
		Team team = teamExpression.getSingle(event);
		Player player = playerExpression.getSingle(event);
		boolean hasPlayer = team != null && player != null && team.hasPlayer(player);
		return isNegated() != hasPlayer;
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		Team team = teamExpression.getSingle(event);
		String teamName = team != null ? team.getInternalName() : "null";
		Player player = playerExpression.getSingle(event);
		String playerName = player != null ? player.getName() : "null";
		return "\"" + playerName + "\"" + " is in team " + "\"" + teamName + "\"";
	}
}