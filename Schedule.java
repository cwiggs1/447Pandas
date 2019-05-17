package scheduler;

import java.util.*;

public class Schedule {

	public int scheduleID;
	public String name;
	public Date startDate;
	public Date endDate;
	public ArrayList<Shift> shifts;
	public ArrayList<Employee> employees;
	
	Schedule(String name, Date start, Date end) {
		this.name = name;
		this.startDate = start;
		this.endDate = end;
	}
	
	
	int generate() {
		
		//algo goes here
		return 0;
	}
	
	
	void addEmployee(Employee empl) {
		employees.add(empl);
		
	}
	
}
