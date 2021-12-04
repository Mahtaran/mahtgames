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

@Name("Start Game")
@Description("Start a game.")
@Examples({
	"command start:",
	"\ttrigger:",
	"\t\tstart game \"test\":",
	"\t\tgive sword to player",
	"\t\tsend \"Start fight!\""
})
@Since("0.0.1-alpha")
public class StartGameEffect extends Effect {

	public static void register() {
		Skript.registerEffect(StartGameEffect.class, "start %game%");
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
			MahtGames.error("Can't start a game \"null\"");
			return;
		}
		Game currentGame = gameExpression.getSingle(event);
		if (currentGame != null) {
			MahtGames.info("Min players: " + currentGame.getMinPlayers());
			MahtGames.info("Max players: " + currentGame.getMaxPlayers());
			if (currentGame.getTeams().size() > 0) {
				if (currentGame.getSpawn() != null) {
					if (currentGame.getLobby() != null) {
						currentGame.start();
					} else {
						MahtGames.error("Can't start the game \"" + currentGame.getInternalName() + "\": lobby is not set.");
					}
				} else {
					MahtGames.error("Can't start the game \"" + currentGame.getInternalName() + "\": spawn is not set.");
				}
			} else {
				MahtGames.error("Can't start the game \"" + currentGame.getInternalName() + "\": you don't have any team created.");
			}
		}
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		Game game = gameExpression.getSingle(event);
		String gameName = game != null ? game.getInternalName() : "null";
		return "Start game \"" + gameName + "\"";
	}
}
