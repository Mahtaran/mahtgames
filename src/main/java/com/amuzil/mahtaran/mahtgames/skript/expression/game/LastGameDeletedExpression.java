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
import com.amuzil.mahtaran.mahtgames.skript.effect.game.DeleteGameEffect;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Last game deleted")
@Description("Returns the last game deleted.")
@Examples({
	"command game:",
	"\ttrigger:",
	"\t\tdelete game \"Test\"",
	"\t\tset {_game} to last game deleted",
	"\t\tbroadcast \"You deleted the game named %name of {_game}%\""
})
@Since("0.0.1-alpha")
public class LastGameDeletedExpression extends SimpleExpression<Game> {
	public static void register() {
		Skript.registerExpression(LastGameDeletedExpression.class, Game.class, ExpressionType.SIMPLE,
			"[the] last [mini(-| )]game deleted"
		);
	}

	@Override
	public boolean init(Expression<?> @NotNull [] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	protected Game[] get(@NotNull Event event) {
		return DeleteGameEffect.getLastDeletedGame().map(game -> new Game[] { game }).orElse(new Game[0]);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Game> getReturnType() {
		return Game.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "the last game deleted \"" + DeleteGameEffect.getLastDeletedGame().map(Game::getInternalName).orElse("null") + "\"";
	}

}
