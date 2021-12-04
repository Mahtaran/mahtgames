package com.amuzil.mahtaran.mahtgames.skript.expression.game;

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
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGames;
import com.amuzil.mahtaran.mahtgames.skript.effect.sections.StartGameSection;
import com.amuzil.mahtaran.mahtgames.skript.effect.sections.StopGameSection;
import com.amuzil.mahtaran.mahtgames.skript.util.EffectSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Game Expression")
@Description("Return a game from its name (the game must to exist)")
@Examples({
		"command game:",
		"\ttrigger:",
		"\t\tdelete game \"test\"",
		"\t\tsend \"Game \"\"test\"\" deleted!\""
})
@Since("0.0.1-alpha")
public class GameExpression extends SimpleExpression<Game> {
	public static void register() {
		Skript.registerExpression(GameExpression.class, Game.class, ExpressionType.SIMPLE, "[the] [mini[(-| )]]game [%-string%]");
	}

	private static Game lastGame;

	private boolean scope = false;
	private Expression<String> gameName;

	public static void setLastGame(Game game) {
		lastGame = game;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] expressions, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull SkriptParser.ParseResult parseResult) {
		gameName = (Expression<String>) expressions[0];
		scope = EffectSection.isCurrentSection(StartGameSection.class) || EffectSection.isCurrentSection(StopGameSection.class);
		if (gameName == null) {
			return scope;
		}
		return true;
	}

	@Override
	protected Game[] get(@NotNull Event event) {
		String currentGame = scope ? lastGame.getInternalName() : gameName.getSingle(event);
		if (currentGame != null) {
			if (!currentGame.replaceAll("\\s", "").isEmpty()) {
				if (MahtGames.exists(currentGame)) {
					return new Game[]{MahtGames.get(currentGame)};
				} else {
					return null;
				}
			} else {
				MahtGames.error("A game can't have a empty name (Current name: \"" + currentGame + "\")");
				return null;
			}
		}
		return null;
	}

	@Override
	public Class<?>[] acceptChange(@NotNull Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
			return new Class[]{Player.class};
		}
		return null;
	}

	@Override
	public void change(@NotNull Event event, Object[] delta, @NotNull Changer.ChangeMode mode) {
		String currentGame = scope ? lastGame.getInternalName() : gameName.getSingle(event);
		if (MahtGames.exists(currentGame)) {
			Game game = MahtGames.get(currentGame);
			for (Object object : delta) {
				if (object instanceof Player) {
					Player player = (Player) object;
					if (mode == Changer.ChangeMode.SET) {
						game.resetPlayers();
						if (!game.isFull()) {
							game.join(player);
						}
					} else if (mode == Changer.ChangeMode.ADD) {
						if (!game.isFull()) {
							if (!game.hasPlayer(player)) {
								game.join(player);
							}
						} else {
							MahtGames.error("The game " + game.getInternalName() + " can't add more players, you have already " + game.getPlayers().size() + " players in this game and the maximum of players is " + game.getMaxPlayers());
						}
					} else if (mode == Changer.ChangeMode.REMOVE) {
						game.leave(player);
					}
				} else {
					MahtGames.error("Only players can be added in a game, but you used a " + object.getClass());
				}
			}
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	@NotNull
	public Class<? extends Game> getReturnType() {
		return Game.class;
	}

	@Override
	@NotNull
	public String toString(Event e, boolean debug) {
		return "The game " + gameName.getSingle(e);
	}
}
