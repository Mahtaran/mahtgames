package com.amuzil.mahtaran.mahtgames.skript.effect.team;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGames;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Name("Create Team for game")
@Description("Create a team for a game")
@Examples({
	"command create <text>:",
	"\ttrigger:",
	"\t\tcreate team \"red\" for game \"Test\""
})
@Since("0.0.1-alpha")
public class CreateTeamEffect extends Effect {
	public static void register() {
		Skript.registerEffect(CreateTeamEffect.class,
			"create [(the|a)] team %string% (for|of|from|in) %game%"
		);
	}

	public static Optional<Team> getLastCreatedTeam() {
		return Optional.ofNullable(lastCreatedTeam);
	}

	public static void setLastCreatedTeam(Team lastCreatedTeam) {
		CreateTeamEffect.lastCreatedTeam = lastCreatedTeam;
	}

	private static Team lastCreatedTeam;

	private Expression<String> teamExpression;
	private Expression<Game> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		teamExpression = (Expression<String>) expressions[0];
		gameExpression = (Expression<Game>) expressions[1];
		return true;
	}

	@Override
	protected void execute(@NotNull Event event) {
		if (teamExpression.getSingle(event) == null) {
			MahtGames.error("Can't create a team \"null\"");
			return;
		}
		String teamName = teamExpression.getSingle(event);
		Game currentGame = gameExpression.getSingle(event);
		if (teamName != null && currentGame != null) {
			if (!teamName.replaceAll("\\s", "").isEmpty()) {
				if (currentGame.teamExists(teamExpression.getSingle(event))) {
					MahtGames.error("The team " + teamName + " already exist in the game " + currentGame.getInternalName());
				} else {
					setLastCreatedTeam(currentGame.createTeam(teamExpression.getSingle(event)));
				}
			} else {
				MahtGames.error("A team can't have a empty name (Current name: " + teamExpression.getSingle(event) + ")");
			}
		} else {
			MahtGames.error("A team can't be null (Current name: " + teamName + ")");
		}
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		Game game = gameExpression.getSingle(event);
		String gameName = game != null ? game.getInternalName() : "null";
		String teamName = teamExpression.getSingle(event) != null ? teamExpression.getSingle(event) : "null";
		return "Create the team \"" + teamName + "\" in the game \"" + gameName + "\"";
	}
}