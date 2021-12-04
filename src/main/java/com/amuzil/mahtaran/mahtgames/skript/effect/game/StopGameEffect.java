package com.amuzil.mahtaran.mahtgames.skript.effect.game;

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
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Stop Game")
@Description("Stop a game.")
@Examples({
		"command stop:",
		"\ttrigger:",
		"\t\tstop game \"test\"",
		"\t\tclear inventory of player",
		"\t\tsend \"Stop fight!\""
})
@Since("0.0.1-alpha")
public class StopGameEffect extends Effect {
	public static void register() {
		Skript.registerEffect(StopGameEffect.class, "stop %game%");
	}

	private Expression<Game> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		gameExpression = (Expression<Game>) expressions[0];
		return true;
	}

	@Override
	protected void execute(@NotNull Event event) {
		if (gameExpression.getSingle(event) == null) {
			MahtGames.error("Can't stop a game \"null\"");
		}
		Game currentGame = gameExpression.getSingle(event);
		if (currentGame != null) {
			if (currentGame.getTeams().size() > 0) {
				if (currentGame.getLobby() != null) {
					currentGame.stop();
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
		return "Stop game \"" + gameName + "\"";
	}
}
