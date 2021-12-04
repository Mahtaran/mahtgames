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

@Name("Delete Game")
@Description("Delete a game.")
@Examples({
	"command delete <text>:",
	"\ttrigger:",
	"\t\tdelete game arg-1",
	"\t\tbroadcast \"Game %arg-1% has been deleted!\""
})
@Since("0.0.1-alpha")
public class DeleteGameEffect extends Effect {
	public static void register() {
		Skript.registerEffect(DeleteGameEffect.class, "delete %game%");
	}

	private static Game lastDeletedGame;

	public static Optional<Game> getLastDeletedGame() {
		return Optional.ofNullable(lastDeletedGame);
	}

	public static void setLastDeletedGame(Game game) {
		lastDeletedGame = game;
	}

	private Expression<Game> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expr, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		gameExpression = (Expression<Game>) expr[0];
		return true;
	}

	@Override
	protected void execute(@NotNull Event event) {
		if (gameExpression.getSingle(event) == null) {
			MahtGames.error("Can't delete a game \"null\"");
			return;
		}
		Game game = gameExpression.getSingle(event);
		if (game != null && MahtGames.delete(game.getInternalName())) {
			setLastDeletedGame(game);
		}
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		Game game = gameExpression.getSingle(event);
		String gameName = game != null ? game.getInternalName() : "null";
		return "Delete the game \"" + gameName + "\"";
	}

}
