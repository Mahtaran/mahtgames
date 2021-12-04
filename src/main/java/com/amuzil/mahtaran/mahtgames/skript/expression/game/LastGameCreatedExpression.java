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
import com.amuzil.mahtaran.mahtgames.skript.effect.game.CreateGameEffect;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Last game created")
@Description("Returns the last game created.")
@Examples({
	"command game:",
	"\ttrigger:",
	"\t\tcreate game \"Test\"",
	"\t\tset {_game} to last game created",
	"\t\tbroadcast \"You created a new game named %name of {_game}%\""
})
@Since("0.0.1-alpha")
public class LastGameCreatedExpression extends SimpleExpression<Game> {
	public static void register() {
		Skript.registerExpression(LastGameCreatedExpression.class, Game.class, ExpressionType.SIMPLE,
			"[the] last [mini(-| )]game created"
		);
	}

	@Override
	public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected Game[] get(@NotNull Event event) {
		return CreateGameEffect.getLastCreatedGame().map(game -> new Game[] { game }).orElse(new Game[0]);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	@NotNull
	public Class<? extends Game> getReturnType() {
		return Game.class;
	}

	@Override
	@NotNull
	public String toString(Event e, boolean debug) {
		return "the last game created \"" + CreateGameEffect.getLastCreatedGame().map(Game::getInternalName).orElse("null") + "\"";
	}

}
