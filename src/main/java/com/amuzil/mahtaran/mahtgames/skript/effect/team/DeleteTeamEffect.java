package com.amuzil.mahtaran.mahtgames.skript.effect.team;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.MahtGames;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DeleteTeamEffect extends Effect {
	public static void register() {
		Skript.registerEffect(DeleteTeamEffect.class, "delete %team%");
	}

	public static Optional<Team> getLastDeletedTeam() {
		return Optional.ofNullable(lastDeletedTeam);
	}

	public static void setLastDeletedTeam(Team lastDeletedTeam) {
		DeleteTeamEffect.lastDeletedTeam = lastDeletedTeam;
	}

	private static Team lastDeletedTeam;

	private Expression<Team> teamExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		teamExpression = (Expression<Team>) expressions[0];
		return true;
	}

	@Override
	protected void execute(@NotNull Event event) {
		Team team = teamExpression.getSingle(event);
		if (team == null) {
			MahtGames.error("Can't delete a team \"null\"");
		} else {
			setLastDeletedTeam(team.delete());
		}
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		Team team = teamExpression.getSingle(event);
		String teamName = team != null ? team.getInternalName() : "null";
		return "Delete the team \"" + teamName + "\"";
	}
}
