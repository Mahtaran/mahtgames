package com.amuzil.mahtaran.mahtgames.skript.expression;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGames;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Game of player")
@Description({ "Returns the game of player. Can be set" })
@Examples({
	"command /test:\n",
	"\ttrigger:\n",
	"\t\tcreate game \"test\"\n",
	"\t\tadd player to game \"test\"\n",
	"\t\tset {_player} to game of player\n",
	"\t\tbroadcast \"%{_player}%\""
})
@Since("0.0.1-alpha")
public class GameOfPlayerExpression extends SimplePropertyExpression<Player, Game> {
	public static void register() {
		Skript.registerExpression(GameOfPlayerExpression.class, Game.class, ExpressionType.PROPERTY,
			"[the] [mini[(-| )]]game of %player%",
			"%player%'s [mini[(-| )]]game"
		);
	}

	@Override
	public Game convert(Player player) {
		return MahtGames.getGameOfPlayer(player);
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			return new Class[] { Player.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET && delta[0] instanceof Game) {
			for (Player player : this.getExpr().getArray(event)) {
				((Game) delta[0]).join(player);
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "game of player";
	}

	@Override
	@NotNull
	public Class<? extends Game> getReturnType() {
		return Game.class;
	}
}
