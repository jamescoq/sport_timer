package com.timer.util;

import android.text.format.Time;

public class TimeUtils {

	private static final int MINUTE_FACTOR = 60;
	private static final int HOUR_FACTOR = 3600;

	public static Time getTime(final Integer time) {
		return getTime(getHoures(time), getMinutes(time), getSeconds(time));
	}

	public static Time getTime(final Integer hour, final Integer minute,
			final Integer second) {

		final Time time = new Time();
		time.second = second;
		time.minute = minute;
		time.hour = hour;

		return time;
	}

	public static Integer getSeconds(final Integer houres,
			final Integer minutes, final Integer seconds) {

		Integer value = seconds != null ? seconds : 0;

		if (minutes != null && minutes > 0) {
			value = value + (minutes * MINUTE_FACTOR);
		}

		if (houres != null && houres > 0) {
			value = value + (houres * HOUR_FACTOR);
		}

		return value;
	}

	private static Integer getSeconds(final Integer time) {
		return (time > 0) ? ((getHoures(time) > 0) ? ((getMinutes(time
				% HOUR_FACTOR) > 0) ? time % MINUTE_FACTOR : time % HOUR_FACTOR)
				: ((getMinutes(time) > 0) ? time % MINUTE_FACTOR : time))
				: 0;
	}

	private static Integer getMinutes(final Integer time) {
		return (time / MINUTE_FACTOR > 0) ? ((getHoures(time) > 0) ? (time % HOUR_FACTOR)
				/ MINUTE_FACTOR
				: time / MINUTE_FACTOR)
				: 0;
	}

	private static Integer getHoures(final Integer time) {
		return (time / HOUR_FACTOR > 0) ? time / HOUR_FACTOR : 0;
	}
}
