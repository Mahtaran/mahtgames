package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.amuzil.mahtaran.mahtgames.Team;
import com.amuzil.mahtaran.mahtgames.skript.expression.game.GamePlayersExpression;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Players list of team")
@Description("Returns the players list of a team. Can be reset.")
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate team \"red\" for game \"Test\"",
	"\t\tadd player to last team created",
	"\t\tbroadcast \"All players of team %last team created%: %players of last team created%"
})
@Since("0.0.1-alpha")
public class TeamPlayersExpression extends SimpleExpression<Player> {
	public static void register() {
		Skript.registerExpression(GamePlayersExpression.class, Player.class, ExpressionType.SIMPLE,
			"[the] players (of|in|from) %team%",
			"[the] %team%'[s] players"
		);
	}

	private Expression<Team> teamExpression;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int i, @NotNull Kleenean kleenean, @NotNull SkriptParser.ParseResult parseResult) {
		this.teamExpression = (Expression<Team>) expressions[0];
		return true;
	}

	@Override
	protected Player[] get(@NotNull Event event) {
		Team team = teamExpression.getSingle(event);
		if (team != null) {
			return team.getPlayers().toArray(new Player[0]);
		} else {
			return null;
		}
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.RESET) {
			return new Class[] { Player.class };
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.RESET) {
			for (Object object : delta) {
				if (object instanceof Team) {
					((Team) object).resetPlayers();
				}
			}
		}
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	@NotNull
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	@NotNull
	public String toString(Event event, boolean debug) {
		if (teamExpression == null) {
			return "the team expression is null";
		}
		Team team = teamExpression.getSingle(event);
		if (team != null) {
			return "players of \"" + team.getInternalName() + "\"";
		} else {
			return "the team expression is null";
		}
	}
}