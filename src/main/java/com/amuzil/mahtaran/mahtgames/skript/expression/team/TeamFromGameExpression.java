package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGames;
import com.amuzil.mahtaran.mahtgames.Points;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@Name("Team from game")
@Description("Return a team of a game from its name")
@Examples({
	"set {_team} to team \"red\" of game \"Test\""
})
@Since("0.0.1-alpha")
@NonNullByDefault
public class TeamFromGameExpression extends SimpleExpression<Team> {
	public static void register() {
		Skript.registerExpression(TeamFromGameExpression.class, Team.class, ExpressionType.SIMPLE,
			"[the] team %string% (of|from|for|in) %game%"
		);
	}

	@Nullable
	private Expression<String> teamExpression;
	@Nullable
	private Expression<Game> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		teamExpression = (Expression<String>) expressions[0];
		gameExpression = (Expression<Game>) expressions[1];
		return true;
	}

	@Override
	@Nullable
	protected Team[] get(Event e) {
		if (teamExpression != null && gameExpression != null) {
			String teamName = teamExpression.getSingle(e);
			Game game = gameExpression.getSingle(e);
			if (game != null && MahtGames.exists(game) && game.teamExists(teamName)) {
				if (teamName != null && !teamName.replaceAll("\\s", "").equals("")) {
					return new Team[] { game.getTeam(teamName) };
				} else {
					MahtGames.error("A team can't have a empty name (Current name: \"" + teamName + "\")");
				}
			}
		}
		return null;
	}

	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
			return new Class[] { Object.class };
		}
		return null;
	}

	@Override
	public void change(Event event, @Nullable Object[] delta, Changer.ChangeMode mode) {
		if (teamExpression != null && gameExpression != null) {
			String teamName = teamExpression.getSingle(event);
			Game game = gameExpression.getSingle(event);
			if (game != null && MahtGames.exists(game) && game.teamExists(teamName) && delta != null) {
				Team team = game.getTeam(teamName);
				for (Object object : delta) {
					if (object instanceof Player) {
						Player player = (Player) object;
						if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD) {
							team.addPlayer(player);
						} else if (mode == Changer.ChangeMode.REMOVE) {
							team.removePlayer(player);
						}
					} else if (object instanceof Points) {
						// TODO figure out what the on earth these points are doing here
						Points points = (Points) object;
						points.setGame(game);
						points.setTeam(team);
						if (mode == Changer.ChangeMode.SET) {
							team.setPoints(points);
						} else if (mode == Changer.ChangeMode.ADD) {
							team.addPoints(points);
						} else if (mode == Changer.ChangeMode.REMOVE) {
							team.removePoints(points);
						}
					} else {
						MahtGames.error("The object \"" + object.getClass() + "\" is not a player or a points");
					}
				}
			}
		}
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
		if (event != null && gameExpression != null && teamExpression != null) {
			Game game = gameExpression.getSingle(event);
			String gameName = game != null ? game.getInternalName() : "null";
			return "The team \"" + teamExpression.getSingle(event) + "\" from the game \"" + gameName + "\"";
		}
		return "The team \"null\" from the game \"null\"";
	}
}
