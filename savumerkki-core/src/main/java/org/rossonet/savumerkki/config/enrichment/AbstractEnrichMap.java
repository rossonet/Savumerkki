package org.rossonet.savumerkki.config.enrichment;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEnrichMap implements EnrichMap {

	public static final String DONT_LOG_VALUE_FIELD = "dont-log-the-value";

	public static final String DRIVER_FIELD = "driver";
	private static final Set<Class<? extends EnrichMap>> enrichMaps = new HashSet<>();
	private final static Logger LOG = LoggerFactory.getLogger(AbstractEnrichMap.class);

	public static final String PRIORITY_FIELD = "priority";

	public static final String TIMEOUT_RESOLUTION_MS_FIELD = "timeout-resolution-ms";

	static EnrichMap fromJson(final JSONObject jsonConfig) {
		try {
			if (jsonConfig.has(DRIVER_FIELD) && !jsonConfig.getString(DRIVER_FIELD).isEmpty()) {
				for (final Class<? extends EnrichMap> e : enrichMaps) {
					if (e.getName().equals(jsonConfig.getString(DRIVER_FIELD))) {
						EnrichMap target;
						target = e.newInstance();
						target.configureFromJson(jsonConfig);
						return target;
					} else {
						LOG.debug(e.getClass().getName() + " != " + jsonConfig.getString(DRIVER_FIELD));
					}
				}
				LOG.error("driver for " + jsonConfig.getString(DRIVER_FIELD) + " not found. Registered drivers are "
						+ enrichMaps);
			} else {
				LOG.error("field " + DRIVER_FIELD + " not found in " + jsonConfig.toString(2));
			}
		} catch (final Exception e1) {
			LOG.error("creating enrich map from " + jsonConfig, e1);
		}
		return null;
	}

	static EnrichMap fromYaml(final String yamlConfig) {
		// TODO implementazione YAML
		return null;
	}

	static Set<Class<? extends EnrichMap>> getEnrichmaps() {
		return enrichMaps;
	}

	static void registerEnrichMap(final Class<? extends EnrichMap> enrichMap) {
		LOG.info(enrichMap.getName() + " registered as enrich map");
		enrichMaps.add(enrichMap);
	}

	private boolean dontLogTheValue = false;

	private int priority = 50;

	private long timeoutResolutionMs = 2000;

	@Override
	public void configureFromJson(final JSONObject jsonConfig) {
		priority = jsonConfig.getInt(PRIORITY_FIELD);
		timeoutResolutionMs = jsonConfig.getLong(TIMEOUT_RESOLUTION_MS_FIELD);
		dontLogTheValue = jsonConfig.getBoolean(DONT_LOG_VALUE_FIELD);
	}

	@Override
	public void configureFromYaml(final String yamlConfig) {
		// TODO implementazione YAML
	}

	@Override
	public boolean dontLogTheValue() {
		return dontLogTheValue;
	}

	@Override
	public JSONObject getEnrichMapAsJson() {
		final JSONObject json = new JSONObject();
		json.put(PRIORITY_FIELD, priority);
		json.put(TIMEOUT_RESOLUTION_MS_FIELD, timeoutResolutionMs);
		json.put(DONT_LOG_VALUE_FIELD, dontLogTheValue);
		json.put(DRIVER_FIELD, getClass().getName());
		return json;
	}

	@Override
	public String getEnrichMapAsYaml() {
		// TODO implementazione YAML
		return null;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public long getTimeoutResolutionMs() {

		return timeoutResolutionMs;
	}

	@Override
	public void resetConnection() {
		// do nothing
	}

	public void setDontLogTheValue(final boolean dontLogTheValue) {
		this.dontLogTheValue = dontLogTheValue;
	}

	public void setPriority(final int priority) {
		this.priority = priority;
	}

	public void setTimeoutResolutionMs(final long timeoutResolutionMs) {
		this.timeoutResolutionMs = timeoutResolutionMs;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AbstractEnrichMap [dontLogTheValue=");
		builder.append(dontLogTheValue);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", timeoutResolutionMs=");
		builder.append(timeoutResolutionMs);
		builder.append("]");
		return builder.toString();
	}

}
