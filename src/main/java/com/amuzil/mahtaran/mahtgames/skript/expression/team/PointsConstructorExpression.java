package com.amuzil.mahtaran.mahtgames.skript.expression.team;

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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

// TODO Weird stuff
@Name("")
@Description("")
@Examples({})
@Since("0.0.1-alpha")
@NonNullByDefault
public class PointsConstructorExpression extends SimpleExpression<Points> {
	public static void register() {
		Skript.registerExpression(PointsConstructorExpression.class, Points.class, ExpressionType.SIMPLE,
			"%integer% point[s] [(from|of) %-player%]"
		);
	}

	@Nullable
	private Expression<Integer> pointsExpression;
	@Nullable
	private Expression<Player> playerExpression;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		pointsExpression = (Expression<Integer>) expr[0];
		playerExpression = (Expression<Player>) expr[1];
		return true;
	}

	@Override
	protected Points[] get(Event event) {
		if (pointsExpression != null) {
			Integer rawPoints = pointsExpression.getSingle(event);
			if (rawPoints != null) {
				if (playerExpression != null) {
					return new Points[] { new Points(rawPoints, playerExpression.getSingle(event)) };
				} else {
					return new Points[] { new Points(rawPoints) };
				}
			}
		}
		return new Points[] {};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Points> getReturnType() {
		return Points.class;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event != null && pointsExpression != null) {
			String result = pointsExpression.getSingle(event) + " points";
			if (playerExpression != null) {
				Player player = playerExpression.getSingle(event);
				if (player != null) {
					result += " from player \"" + player.getDisplayName() + "\"";
				}
			}
			return result;
		}
		return "\"null\" points from player \"null\"";
	}
}
