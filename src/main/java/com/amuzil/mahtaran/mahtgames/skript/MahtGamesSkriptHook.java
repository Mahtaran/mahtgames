package com.amuzil.mahtaran.mahtgames.skript;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGamesPlugin;
import com.amuzil.mahtaran.mahtgames.Points;
import com.amuzil.mahtaran.mahtgames.Team;
import com.amuzil.mahtaran.mahtgames.skript.condition.GameExistsCondition;
import com.amuzil.mahtaran.mahtgames.skript.condition.TeamContainsPlayerCondition;
import com.amuzil.mahtaran.mahtgames.skript.effect.game.*;
import com.amuzil.mahtaran.mahtgames.skript.effect.sections.StartGameSection;
import com.amuzil.mahtaran.mahtgames.skript.effect.sections.StopGameSection;
import com.amuzil.mahtaran.mahtgames.skript.effect.team.CreateTeamEffect;
import com.amuzil.mahtaran.mahtgames.skript.effect.team.DeleteTeamEffect;
import com.amuzil.mahtaran.mahtgames.skript.event.GameEvents;
import com.amuzil.mahtaran.mahtgames.skript.event.PlayerEvents;
import com.amuzil.mahtaran.mahtgames.skript.event.TeamEvents;
import com.amuzil.mahtaran.mahtgames.skript.expression.GameOfPlayerExpression;
import com.amuzil.mahtaran.mahtgames.skript.expression.game.*;
import com.amuzil.mahtaran.mahtgames.skript.expression.game.message.*;
import com.amuzil.mahtaran.mahtgames.skript.expression.points.AuthorOfPointsExpression;
import com.amuzil.mahtaran.mahtgames.skript.expression.points.GameOfPointsExpression;
import com.amuzil.mahtaran.mahtgames.skript.expression.points.RawPointsExpression;
import com.amuzil.mahtaran.mahtgames.skript.expression.points.TeamOfPointsExpression;
import com.amuzil.mahtaran.mahtgames.skript.expression.team.*;
import org.jetbrains.annotations.NotNull;

public class MahtGamesSkriptHook {
	private final MahtGamesPlugin plugin;

	public MahtGamesSkriptHook(MahtGamesPlugin plugin) {
		this.plugin = plugin;
	}

	public void register() {
		Skript.registerAddon(plugin);
		registerTypes();
		registerConditions();
		registerEffects();
		registerEvents();
		registerExpressions();
	}


	private void registerTypes() {
		// TODO descriptions, examples
		Classes.registerClass(new ClassInfo<>(Game.class, "game").defaultExpression(new EventValueExpression<>(Game.class))
			.user("(mini(-| )?)?game").name("Game").since("0.0.1-alpha").parser(new Parser<Game>() {
				@Override
				@NotNull
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				public Game parse(@NotNull String input, @NotNull ParseContext context) {
					return null;
				}

				@Override
				public boolean canParse(@NotNull ParseContext context) {
					return false;
				}

				@Override
				@NotNull
				public String toString(Game game, int flags) {
					return game.getInternalName();
				}

				@Override
				@NotNull
				public String toVariableNameString(Game game) {
					return game.getInternalName();
				}
			}));

		Classes.registerClass(new ClassInfo<>(Team.class, "team").defaultExpression(new EventValueExpression<>(Team.class))
			.user("team").name("team").since("0.0.1-alpha").parser(new Parser<Team>() {
				@Override
				@NotNull
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				public Team parse(@NotNull String input, @NotNull ParseContext context) {
					return null;
				}

				@Override
				public boolean canParse(@NotNull ParseContext context) {
					return false;
				}

				@Override
				@NotNull
				public String toString(Team team, int flags) {
					return team.getInternalName();
				}

				@Override
				@NotNull
				public String toVariableNameString(Team team) {
					return team.getInternalName();
				}
			}));

		Classes.registerClass(new ClassInfo<>(Points.class, "point").defaultExpression(new EventValueExpression<>(Points.class))
			.user("points?").name("Points").since("0.0.1-alpha").parser(new Parser<Points>() {
				@Override
				@NotNull
				public String getVariableNamePattern() {
					return ".+";
				}

				@Override
				public Points parse(@NotNull String input, @NotNull ParseContext context) {
					try {
						return new Points(Integer.parseInt(input));
					} catch (NumberFormatException ignored) {
						return null;
					}
				}

				@Override
				public boolean canParse(@NotNull ParseContext context) {
					return true;
				}

				@Override
				@NotNull
				public String toString(Points points, int flags) {
					return Integer.toString(points.getPoints());
				}

				@Override
				@NotNull
				public String toVariableNameString(Points points) {
					return Integer.toString(points.getPoints());
				}
			}));
	}

	private void registerConditions() {
		GameExistsCondition.register();
		TeamContainsPlayerCondition.register();
	}

	private void registerEffects() {
		CancelStartGameEffect.register();
		CreateGameEffect.register();
		DeleteGameEffect.register();
		StartGameEffect.register();
		StopGameEffect.register();

		StartGameSection.register();
		StopGameSection.register();

		CreateTeamEffect.register();
		DeleteTeamEffect.register();
	}

	private void registerEvents() {
		GameEvents.register();
		PlayerEvents.register();
		TeamEvents.register();
	}

	private void registerExpressions() {
		// game
		AllGamesExpression.register();
		GameDisplayNameExpression.register();
		GameExpression.register();
		GameNameExpression.register();
		GamePlayersExpression.register();
		GameSpawnExpression.register();
		LastGameCreatedExpression.register();
		LastGameDeletedExpression.register();
		LobbyOfGameExpression.register();
		MaximumPlayersOfGameExpression.register();
		MinimumPlayersOfGameExpression.register();
		WinnerOfGameExpression.register();
		WorldOfGameExpression.register();

		// game.message
		GlobalJoinGameExpression.register();
		GlobalJoinTeamExpression.register();
		GlobalLeaveGameExpression.register();
		GlobalLeaveTeamExpression.register();
		GlobalLosePointExpression.register();
		GlobalWinPointExpression.register();
		PlayerJoinGameExpression.register();
		PlayerJoinTeamExpression.register();
		PlayerLeaveGameExpression.register();
		PlayerLeaveTeamExpression.register();
		PlayerLosePointExpression.register();
		PlayerWinPointExpression.register();

		// points
		AuthorOfPointsExpression.register();
		GameOfPointsExpression.register();
		RawPointsExpression.register();
		TeamOfPointsExpression.register();

		// team
		GameOfTeamExpression.register();
		LastTeamCreatedExpression.register();
		LastTeamDeletedExpression.register();
		MaximumPlayersOfTeamExpression.register();
		MinimumPlayersOfTeamExpression.register();
		ObjectiveExpression.register();
		PointsConstructorExpression.register();
		PointsGetterExpression.register();
		RandomTeamExpression.register();
		TeamDisplayNameExpression.register();
		TeamFromGameExpression.register();
		TeamNameExpression.register();
		TeamOfPlayerExpression.register();
		TeamPlayersExpression.register();
		TeamSpawnExpression.register();

		GameOfPlayerExpression.register();
	}
}
