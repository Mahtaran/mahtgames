package com.amuzil.mahtaran.mahtgames;

import java.util.HashMap;
import java.util.Map;

public class KeyedContextMessages {
	public static class Message {
		private final Map<String, String> contextMessages = new HashMap<>();

		public void set(String context, String message) {
			contextMessages.put(context, message);
		}

		public String get(String context) {
			return contextMessages.get(context);
		}
	}

	private final Map<String, Message> keyedMessages = new HashMap<>();

	public void set(String key, String context, String message) {
		keyedMessages.computeIfAbsent(key, k -> new Message()).set(context, message);
	}

	public String get(String key, String context) {
		return keyedMessages.get(key).get(context);
	}
}
