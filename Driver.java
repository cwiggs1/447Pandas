package scheduler;

import java.util.*;
import java.text.*;
import java.time.*;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Welcome to Scheduler");

		/*
		Instant now = Instant.now();
		ZoneId zoneId = ZoneId.of("America/New_York");
		ZonedDateTime dateAndTimeInLA = ZonedDateTime.ofInstant(now, zoneId);
		Date startDate = Date.from(ZonedDateTime.ofInstant(now, zoneId));
		*/
		LocalDateTime startDate = LocalDateTime.now();

		//make Schedule
		Schedule testSchedule = new Schedule("Test", startDate, startDate);

	}

}
