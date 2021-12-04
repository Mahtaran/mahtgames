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
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Lobby of game")
@Description("Returns lobby of a game. Can be set.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tset {_game} to game \"test\"",
	"\t\tset lobby of {_game} to location of player",
	"\t\tbroadcast \"The game %{_game}% has lobby in: %lobby of {_game}%\" "
})
@Since("0.0.1-alpha")
public class LobbyOfGameExpression extends SimplePropertyExpression<Game, Location> {
	public static void register() {
		Skript.registerExpression(LobbyOfGameExpression.class, Location.class, ExpressionType.PROPERTY,
			"[the] lobby of %game%",
			"[the] %game%'[s] lobby"
		);
	}

	@Override
	public Location convert(Game game) {
		return game.getLobby();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
			return new Class[] { Location.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		for (Game game : getExpr().getArray(event)) {
			if (mode == Changer.ChangeMode.SET) {
				game.setLobby((Location) delta[0]);
			} else if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.DELETE) {
				game.setLobby(null);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "lobby";
	}

	@Override
	@NotNull
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
}
