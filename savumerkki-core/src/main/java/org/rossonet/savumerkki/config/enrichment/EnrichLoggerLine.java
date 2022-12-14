package org.rossonet.savumerkki.config.enrichment;

import java.io.Serializable;

public final class EnrichLoggerLine implements Serializable {

	private static final long serialVersionUID = -2898753016501622062L;
	private transient final EnrichMap enrichMap;
	private final String key;
	private final long priority;
	/**
	 * in this record the field value is null because the EnrichMap have the
	 * dont-log-the-value flag switched to true.
	 */
	private final boolean secret;
	/**
	 * this replacement is used to change the configuration. May be that we have
	 * many EnrichMap whith the same key to replace, sorting by priority just one of
	 * them is used.
	 */
	private final boolean used;
	private final String value;

	public EnrichLoggerLine(final EnrichMap enrichMap, final String key, final long priority, final boolean secret,
			final boolean used, final String value) {
		this.enrichMap = enrichMap;
		this.key = key;
		this.priority = priority;
		this.secret = secret;
		this.used = used;
		this.value = value;
	}

	public EnrichMap getEnrichMap() {
		return enrichMap;
	}

	public String getKey() {
		return key;
	}

	public long getPriority() {
		return priority;
	}

	public String getValue() {
		return value;
	}

	public boolean isSecret() {
		return secret;
	}

	public boolean isUsed() {
		return used;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("EnrichLoggerLine [enrichMap=");
		builder.append(enrichMap);
		builder.append(", key=");
		builder.append(key);
		builder.append(", secret=");
		builder.append(secret);
		builder.append(", value=");
		builder.append(value);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", used=");
		builder.append(used);
		builder.append("]");
		return builder.toString();
	}
}
