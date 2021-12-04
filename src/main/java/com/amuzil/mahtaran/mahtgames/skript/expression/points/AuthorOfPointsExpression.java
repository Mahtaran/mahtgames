package com.amuzil.mahtaran.mahtgames.skript.expression.points;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Points;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Name("Author of Points")
@Description("Returns the author of target points.")
@Examples({
	"command /points:\n" +
		"\ttrigger:\n" +
		"\t\tcreate game \"test\"\n" +
		"\t\tcreate team \"red\" in game \"test\"\n" +
		"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
		"\t\tset {_points} to author of points of team \"red\" in game \"test\"\n" +
		"\t\tbroadcast \"%{_points}%\""
})
@Since("0.0.1-alpha")
public class AuthorOfPointsExpression extends SimplePropertyExpression<Points, Player> {
	public static void register() {
		Skript.registerExpression(AuthorOfPointsExpression.class, Player.class, ExpressionType.PROPERTY,
			"[the] author of %points%",
			"[the] %points%'[s] author"
		);
	}

	@Override
	public Player convert(Points points) {
		return points.getAuthor();
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "author";
	}

	@Override
	@NotNull
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}
}
