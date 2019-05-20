import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Instant now = Instant.now();
		ZoneId zoneId = ZoneId.of("America/New_York");
		ZonedDateTime dateAndTimeInLA = ZonedDateTime.ofInstant(now, zoneId);
		Date startDate = Date.from(ZonedDateTime.now().toInstant());
		
		//make Schedule
		Schedule testSchedule = new Schedule("Test", startDate, startDate);

	}

}
