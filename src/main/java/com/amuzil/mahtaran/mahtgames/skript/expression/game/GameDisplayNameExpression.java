package com.amuzil.mahtaran.mahtgames.skript.expression.game;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Utils;
import com.amuzil.mahtaran.mahtgames.Game;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Display name of game")
@Description({ "Returns the display name of a game. Can be set." })
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate game \"Test\"",
	"\t\tset display name of last game created to \"&c[Test]\"",
	"\t\tbroadcast \"The display name of game %last game created% is %display name of last game created%\""
})
@Since("0.0.1-alpha")
public class GameDisplayNameExpression extends SimplePropertyExpression<Game, String> {
	public static void register() {
		Skript.registerExpression(GameDisplayNameExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] display name of %game%",
			"[the] %game%'[s] display name"
		);
	}

	public String convert(Game game) {
		return game.getDisplayName();
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		return mode == Changer.ChangeMode.SET ? new Class[] { String.class } : null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			String name = (String) delta[0];
			if (!name.replaceAll("\\s", "").equals("")) {
				for (Game game : this.getExpr().getArray(event)) {
					game.setDisplayName(Utils.replaceChatStyles(name));
				}
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "game display name";
	}

	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
