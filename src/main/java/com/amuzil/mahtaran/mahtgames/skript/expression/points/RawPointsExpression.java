package com.amuzil.mahtaran.mahtgames.skript.expression.points;

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
import com.amuzil.mahtaran.mahtgames.Points;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

@Name("Raw Points")
@Description("Returns the number of points.")
@Examples({
	"command /points:\n" +
		"\ttrigger:\n" +
		"\t\tcreate game \"test\"\n" +
		"\t\tcreate team \"red\" in game \"test\"\n" +
		"\t\tadd 5 points to team \"red\" in game \"test\"\n" +
		"\t\tset {_points} to raw points of team \"red\" in game \"test\"\n" +
		"\t\tbroadcast \"%{_points}%\""
})
@Since("0.0.1-alpha")
public class RawPointsExpression extends SimpleExpression<Integer> {
	public static void register() {
		Skript.registerExpression(RawPointsExpression.class, Integer.class, ExpressionType.SIMPLE,
			"raw %points%"
		);
	}

	private Expression<Points> pointsExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expr, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		pointsExpression = (Expression<Points>) expr[0];
		return true;
	}

	@Override
	protected Integer[] get(@NotNull Event event) {
		Points points = pointsExpression.getSingle(event);
		if (points == null) {
			return null;
		} else {
			return new Integer[] { points.getPoints() };
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	@NotNull
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	@NotNull
	public String toString(@Nullable Event event, boolean debug) {
		if (event != null) {
			Points points = pointsExpression.getSingle(event);
			if (points != null) {
				return "Raw points of " + points.getPoints();
			}
		}
		return "Raw points of null";
	}
}
