package com.amuzil.mahtaran.mahtgames.skript.expression.team;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.util.Utils;
import com.amuzil.mahtaran.mahtgames.Team;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

@Name("Display name of team")
@Description({ "Returns the display name of a team. Can be set." })
@Examples({
	"command test:",
	"\ttrigger:",
	"\t\tcreate team \"red\" for game \"Test\"",
	"\t\tset display name of last team created to \"&c[Red]\"",
	"\t\tbroadcast \"The display name of team %last team created% is %display name of last team created%\""
})
@Since("0.0.1-alpha")
public class TeamDisplayNameExpression extends SimplePropertyExpression<Team, String> {
	public static void register() {
		Skript.registerExpression(TeamDisplayNameExpression.class, String.class, ExpressionType.PROPERTY,
			"[the] display name of %team%",
			"[the] %team%'[s] display name"
		);
	}

	public String convert(Team team) {
		return team.getDisplayName();
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
				for (Team team : this.getExpr().getArray(event)) {
					team.setDisplayName(Utils.replaceChatStyles(name));
				}
			}
		}
	}

	@Override
	@NotNull
	protected String getPropertyName() {
		return "team display name";
	}

	@Override
	@NotNull
	public Class<? extends String> getReturnType() {
		return String.class;
	}
}
