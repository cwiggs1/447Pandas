import java.util.ArrayList;

public class Priority {

	public static void main(String[] args) {
		
		ArrayList<Shift> shifts = new ArrayList<Shift>();
		
		String[] weekdays = {"mo", "tu", "we", "th", "fr", "sa", "su"};
		
		int i = 1;
		
		for (String day: weekdays) {
			
			if (!day.equals("sa") && !day.equals("su")) {
				
	            shifts.add(new Shift(day, 1, i));
	            i += 1;
	            shifts.add(new Shift(day, 2, i));
	            i += 1;
	            shifts.add(new Shift(day, 3, i));
	            i += 1;
			}
	        else {
	        	
	            shifts.add(new Shift(day, 4, i));
	            i += 1;
	            shifts.add(new Shift(day, 5, i));
	            i += 1;
	        }
		}
	}
}
