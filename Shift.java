import java.util.PriorityQueue;

public class Shift {

	private PriorityQueue shiftQueue;
	private String day;
	private int shiftNumber;
	private int id;
	
	public Shift(String d, int sn, int ID) {
		
		shiftQueue = new PriorityQueue<>();
		day = d;
		shiftNumber = sn;
		id = ID;
	}
	
	public boolean isEmpty() {
		return shiftQueue.isEmpty();
	}
	
	public void insert() {
		shiftQueue.add(null);
	}
	
	public void delete() {
		
	}
}
