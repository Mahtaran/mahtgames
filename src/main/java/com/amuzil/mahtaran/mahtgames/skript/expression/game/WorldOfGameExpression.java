package com.amuzil.mahtaran.mahtgames.skript.expression.game;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Game;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("World of game")
@Description("Returns world of a game. Can be set.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_game} to game \"test\"",
	"\t\tset world of {_game} to world(\"world\")",
	"\t\tbroadcast \"The game %{_game}% is in world: %world of {_game}%\""
})
@Since("0.0.1-alpha")
public class WorldOfGameExpression extends SimplePropertyExpression<Game, World> {
	public static void register() {
		Skript.registerExpression(WorldOfGameExpression.class, World.class, ExpressionType.PROPERTY,
			"[the] world of %game%",
			"[the] %game%'[s] world"
		);
	}

	@Override
	public World convert(Game game) {
		return game.getWorld();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET ||
			mode == Changer.ChangeMode.DELETE) {
			return new Class[] { Game.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Game game : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				game.setWorld((World) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET) {
				game.setWorld(Bukkit.getWorlds().get(0));
			} else if (mode == Changer.ChangeMode.DELETE) {
				game.setWorld(null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "world";
	}

	@Override
	@NotNull
	public Class<? extends World> getReturnType() {
		return World.class;
	}
}
