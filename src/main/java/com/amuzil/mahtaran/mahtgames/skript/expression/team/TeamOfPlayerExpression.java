package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import com.amuzil.mahtaran.mahtgames.Game;
import com.amuzil.mahtaran.mahtgames.MahtGames;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.entity.Player;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

@Name("Team of Player")
@Description("Return the team of the player")
@Examples({
	"command team:",
	"\ttrigger:",
	"\t\tsend \"You are in the %team of player% team\""
})
@Since("1.2")
@NonNullByDefault
public class TeamOfPlayerExpression extends SimplePropertyExpression<Player, Team> {
	public static void register() {
		Skript.registerExpression(TeamOfPlayerExpression.class, Team.class, ExpressionType.PROPERTY,
			"[the] team of %player%",
			"[the] %player%'[s] team"
		);
	}

	@Override
	protected String getPropertyName() {
		return "team";
	}

	@Override
	@Nullable
	public Team convert(Player player) {
		Game game = MahtGames.getGameOfPlayer(player);
		return game != null ? game.getTeamOfPlayer(player) : null;
	}

	@Override
	public Class<? extends Team> getReturnType() {
		return Team.class;
	}
}
