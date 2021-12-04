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
import com.amuzil.mahtaran.mahtgames.MahtGames;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Game exists?")
@Description("Check if a game exists")
@Examples({
		"command check:",
		"\ttrigger:",
		"\t\tif game \"test\" doesn't exist:",
		"\t\t\tcreate the game \"test\""
})
@Since("0.0.1-alpha")
public class GameExistsCondition extends Condition {
	public static void register() {
		Skript.registerCondition(GameExistsCondition.class,
				"[mini[(-| )]]game %string% exists",
				"[mini[(-| )]]game %string% does(n't| not) exist"
		);
	}

	private Expression<String> gameExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		gameExpression = (Expression<String>) expressions[0];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(@NotNull Event event) {
		boolean exists = gameExpression.getSingle(event) != null && MahtGames.exists(gameExpression.getSingle(event));
		return isNegated() != exists;
	}

	@Override
	@NotNull
	public String toString(Event e, boolean debug) {
		String gameName = gameExpression.getSingle(e) != null ? gameExpression.getSingle(e) : "null";
		return "Game \"" + gameName + "\" existence";
	}
}
