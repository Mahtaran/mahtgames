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
import org.jetbrains.annotations.NotNull;

@Name("Name of game")
@Description("Returns the name of a game.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate game \"Test\"",
	"\t\tbroadcast \"The name of game %last game created% is %name of last game created%\""
})
@Since("0.0.1-alpha")
public class GameNameExpression extends SimplePropertyExpression<Game, String> {
	public static void register() {
		Skript.registerExpression(GameNameExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] [game] name of %game%",
			"%game%'[s] [game] name"
		);
	}

	@Override
	public String convert(Game game) {
		return game.getInternalName();

	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		return null;
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "game name";
	}


	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
