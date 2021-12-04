package com.amuzil.mahtaran.mahtgames.skript.effect.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGames;
import com.amuzil.mahtaran.mahtgames.skript.expression.game.GameExpression;
import com.amuzil.mahtaran.mahtgames.skript.util.EffectSection;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Stop Game as section")
@Description("Stop a game as section.")
@Examples({
		"command stop:",
		"\ttrigger:",
		"\t\tstop game \"test\":",
		"\t\t\tclear inventory of player",
		"\t\t\tsend \"Stop fight!\""
})
@Since("0.0.1-alpha")
public class StopGameSection extends EffectSection {
	public static void register() {
		Skript.registerCondition(StopGameSection.class, "stop %game%");
	}

	private Expression<Game> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		if (checkIfCondition())
			return false;
		if (!hasSection()) {
			Skript.error("A game start scope is useless without any content!");
			return false;
		}
		gameExpression = (Expression<Game>) expressions[0];
		loadSection(true);
		return true;
	}

	@Override
	protected void execute(Event e) {
		if (gameExpression.getSingle(e) == null) {
			MahtGames.error("Can't start a game \"null\"");
			return;
		}
		Game currentGame = gameExpression.getSingle(e);
		if (currentGame != null) {
			GameExpression.setLastGame(currentGame);
			if (currentGame.getTeams().size() > 0) {
				if (currentGame.getLobby() != null) {
					currentGame.stop();
					runSection(e);
				} else {
					MahtGames.error("Can't stop the game " + currentGame.getInternalName() + ": lobby is not set.");
				}
			} else {
				MahtGames.error("Can't stop the game " + currentGame.getInternalName() + ": you don't have any team created.");
			}
		}
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		Game game = gameExpression.getSingle(event);
		String gameName = game != null ? game.getInternalName() : "null";
		return "Scope stop game \"" + gameName + "\"";
	}
}
