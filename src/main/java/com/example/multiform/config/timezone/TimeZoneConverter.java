package com.example.multiform.config.timezone;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Optional;

public class TimeZoneConverter {

	public static void convertFields(Object entity) {
		ZoneId clientTimeZone = Optional.ofNullable(TimeZoneContextHolder.getTimeZone()).orElse(ZoneId.of("Asia/Jakarta"));

		Field[] fields = entity.getClass().getDeclaredFields();
		Arrays.stream(fields)
				.filter(field -> field.isAnnotationPresent(ConvertToClientTimeZone.class))
				.forEach(field -> {
					try {
						field.setAccessible(true);
						LocalDateTime localDateTime = (LocalDateTime) field.get(entity);
						if (localDateTime != null) {
							ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("UTC"));
							ZonedDateTime clientZonedDateTime = zonedDateTime.withZoneSameInstant(clientTimeZone);
							field.set(entity, clientZonedDateTime.toLocalDateTime());
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				});
	}
}