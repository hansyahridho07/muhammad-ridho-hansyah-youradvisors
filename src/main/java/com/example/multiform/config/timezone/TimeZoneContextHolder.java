package com.example.multiform.config.timezone;

import java.time.ZoneId;

// Context holder to store the client time zone during the request lifecycle
public class TimeZoneContextHolder {

	private static final ThreadLocal<ZoneId> contextHolder = new ThreadLocal<>();

	// setter
	public static void setTimeZone(ZoneId zoneId) {
		contextHolder.set(zoneId);
	}

	// getter
	public static ZoneId getTimeZone() {
		return contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}
}
