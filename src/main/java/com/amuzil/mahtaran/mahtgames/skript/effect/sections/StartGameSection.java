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

@Name("Start Game")
@Description("Start a game.")
@Examples({
		"command start:",
		"\ttrigger:",
		"\t\tstart game \"test\":",
		"\t\t\tgive sword to player",
		"\t\t\tsend \"Start fight!\""
})
@Since("0.0.1-alpha")
public class StartGameSection extends EffectSection {
	public static void register() {
		Skript.registerCondition(StartGameSection.class, "start %game%");
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
	protected void execute(Event event) {
		if (gameExpression.getSingle(event) == null) {
			MahtGames.error("Can't start a game \"null\"");
			return;
		}
		Game currentGame = gameExpression.getSingle(event);
		if (currentGame != null) {
			GameExpression.setLastGame(currentGame);
			if (currentGame.getTeams().size() > 0) {
				if (currentGame.getSpawn() != null) {
					if (currentGame.getLobby() != null) {
						currentGame.start();
						runSection(event);
					} else {
						MahtGames.error("Can't start the game " + currentGame.getInternalName() + ": lobby is not set.");
					}
				} else {
					MahtGames.error("Can't start the game " + currentGame.getInternalName() + ": spawn is not set.");
				}
			} else {
				MahtGames.error("Can't start the game " + currentGame.getInternalName() + ": you don't have any team created.");
			}
		}
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		Game game = this.gameExpression.getSingle(event);
		String gameName = game != null ? game.getInternalName() : "null";
		return "Scope start game \"" + gameName + "\"";
	}
}
