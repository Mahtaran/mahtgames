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

import java.util.Optional;

@Name("Create Game")
@Description("Create a game.")
@Examples({
	"command create <text>:",
	"\ttrigger:",
	"\t\tcreate game arg-1",
	"\t\tbroadcast \"Game %arg-1% has been created!\""
})
@Since("0.0.1-alpha")

public class CreateGameEffect extends Effect {
	public static void register() {
		Skript.registerEffect(CreateGameEffect.class,
			"create [(the|a)] [mini[(-| )]]game %string%"
		);
	}

	private static Game lastCreatedGame;

	public static Optional<Game> getLastCreatedGame() {
		return Optional.ofNullable(lastCreatedGame);
	}

	public static void setLastCreatedGame(Game game) {
		lastCreatedGame = game;
	}

	private Expression<String> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		gameExpression = (Expression<String>) expressions[0];
		return true;
	}

	@Override
	protected void execute(@NotNull Event event) {
		if (gameExpression.getSingle(event) == null) {
			MahtGames.error("Can't create a game \"null\"");
			return;
		}
		String gameName = gameExpression.getSingle(event);
		if (gameName != null) {
			if (!gameName.replaceAll("\\s", "").isEmpty()) {
				Game game = MahtGames.create(gameName);
				if (game != null) {
					setLastCreatedGame(game);
				}
			} else {
				MahtGames.error("A game can't have a empty name (Current name: " + gameExpression.getSingle(event) + ")");
			}
		}
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		String gameName = gameExpression.getSingle(event) != null ? gameExpression.getSingle(event) : "null";
		return "Create the game \"" + gameName + "\"";
	}

}
