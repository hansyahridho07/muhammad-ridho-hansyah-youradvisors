package com.example.multiform.config.timezone;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.ZoneId;
import java.util.Optional;

@Component
public class TimeZoneInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String clientTimeZone = Optional.ofNullable(request.getHeader("x-timezone")).orElse("Asia/Jakarta");
		TimeZoneContextHolder.setTimeZone(ZoneId.of(clientTimeZone));
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		TimeZoneContextHolder.clear();
	}
}
