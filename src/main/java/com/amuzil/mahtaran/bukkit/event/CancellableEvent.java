package com.amuzil.mahtaran.bukkit.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class CancellableEvent extends Event implements Cancellable {
	private boolean cancelled;

	protected CancellableEvent() {
		cancelled = false;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
