package com.amuzil.mahtaran.mahtgames.skript.expression.game;

import ch.njol.skript.Skript;
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
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("All Games")
@Description("Return all games created.")
@Examples({
		"command list:",
		"\ttrigger:",
		"\t\tbroadcast \"List of all games created: %all games%\""
})
@Since("0.0.1-alpha")
public class AllGamesExpression extends SimpleExpression<Game> {
	public static void register() {
		Skript.registerExpression(AllGamesExpression.class, Game.class, ExpressionType.SIMPLE, "all [minis(-| )]games");
	}

	@Override
	public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected Game[] get(@NotNull Event event) {
		if (MahtGames.allGames().isEmpty()) return null;
		return MahtGames.allGames().toArray(new Game[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	@NotNull
	public Class<? extends Game> getReturnType() {
		return Game.class;
	}

	@Override
	@NotNull
	public String toString(Event e, boolean debug) {
		return "all games";
	}
}
