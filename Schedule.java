package scheduler;

import java.util.*;

public class Schedule {

	public int scheduleID;
	public String name;
	public Date startDate;
	public Date endDate;
	public ArrayList<Shift> shifts;
	public ArrayList<Employee> employees;
	public static int TOTALSHIFTS = 274;
	public static int WEEKSPERPERIOD = 13;

	Schedule(String name, Date start, Date end) {
		this.name = name;
		this.startDate = start;
		this.endDate = end;
	}
	
	
	int generate() {
		
		//algo goes here
		int[] employeeSchedule = new int[TOTALSHIFTS];  //employee ID for each shift of the entire period (essentially the schedule)

		//do attending week shifts
		Employee[] awDoctors = new Employee[WEEKSPERPERIOD];
		awDoctors = scheduleAttendingWeeks(getEmployeeArray(employees));  //getAllIDs gives the attending week algorithm an array of all employees
		
		int[] awIndex = new int[WEEKSPERPERIOD * 5]; //an array of the index of all morning weekday shifts in employeeSchedule
		int[] afIndex = new int[WEEKSPERPERIOD * 5];  //index of all afternoon shifts within the 274
		int[] evIndex = new int[WEEKSPERPERIOD * 5];  //index of all evening shifts within the 274
		int[] weIndex = new int[WEEKSPERPERIOD * 4];  //index of all weekend shifts within the 274
		int weekCount = 0;

		//set all index arrays to their correct numbers
		awIndex = getAWShifts();
		afIndex = getAfternoonIndex();
		evIndex = getEveningIndex();
		weIndex = getWeekendIndex();

		//put awIndex into employeeSchedule
		for (int i = 0; i < (WEEKSPERPERIOD * 5); i++)
		{
			employeeSchedule[awIndex[i]] = awDoctors[weekCount];  //every time a doctor is put into employee schedule their shift count and hour count should be increased
			awDoctors[weekCount].shift_count += 1;
			awDoctors[weekCount].hours_count += 8;  //aw shifts are always 8 hour morning shifts

			if (i % 5 == 4)  //after every fifth shift (friday) is assigned, increment the week count
			{
				weekCount++;
			}
		}

		ArrayList<Employee> availableDoctors;
		int shiftType;  //afternoon shift is 0, evening is 1, weekend is 2

		//this for loop should fill the rest of the shifts in employeeSchedule
		for (int currShift = 0; currShift < TOTALSHIFTS; currShift++)
		{
			//first it checks if the schedule slot is already filled from the attending week schedule
			if(employeeSchedule[currShift] != 0)
			{
				continue;  //if the slot is already filled then skip it.
			}

			//assign the type of shift by cycling through max number of type of shifts (13 weeks times 5 days a week)
			for(int j = 0; j < (WEEKSPERPERIOD * 5); j++)
			{
				if(afIndex[j] == currShift)  //check afternoon shifts
				{
					shiftType = 0;
				}
				else if (evIndex[j] == currShift)  //check evening shifts
				{
					shiftType = 1;
				}
				else  //if it goes thorugh to this else it has checked every weekday shift so it must be weekend
				{
					shiftType == 2;
				}
			}

			//find all employees availability for this shift (could be empty arraylist if nobody is available)
			availableDoctors = getAvailability(employees, currShift);

			if(availableDoctors.isEmpty())
			{
				//assign a moonlighter
			}
			else  //search through available doctors and pick one based on how many shifts they have 
			{
				for (int k = 0; k < availableDoctors.size(); k++)
				{
					
				}
			}

		}

		

		//need to calculate and schedule all other shifts (mod: 1,2,4,5,7,8,10,11,13,14,15,16,17,18)
		//cannot schedule someone the same day or night before their attending week
		//generally want 2 weekend shifts, 3-4 afternoon shifts, and 3-4 night shifts
		//shifts are picked based on availability and then number of shifts worked of that type. person that is most free and has least shifts will be picked
		//
		
		
		return 0;
	}
	
	
	void addEmployee(Employee empl) {
		employees.add(empl);
	}

	//this function returns an int array of all employee IDs in the employees arraylist
	public Employee[] getEmployeeArray(ArrayList<Employee> employees)
	{
		Employee[] allIDs = new Employee[employees.size()];

		for (int i = 0; i < employees.size(); i++)
		{
			allIDs[i] = employees.get(i);  //sets each spot of allIDs to a different employee ID
		}
	
		return allIDs;
	}

	public int[] getAWIndex();
	{
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is a morning weekday shift then add it to awIndex
			if ((i % 19 == 0) || (i % 19 == 3) || (i % 19 == 6) || (i % 19 == 9) || (i % 19 == 12))
			{
				awIndex[count] = i;
				count++;
			}
		}
	}

	public int[] getAfternoonIndex();
	{
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is an afternoon weekday shift then add it 
			if ((i % 19 == 1) || (i % 19 == 4) || (i % 19 == 7) || (i % 19 == 10) || (i % 19 == 13))
			{
				afIndex[count] = i;
				count++;
			}
		}
	}

	public int[] getEveningIndex();
	{
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is an evening weekday shift then add it
			if ((i % 19 == 2) || (i % 19 == 5) || (i % 19 == 8) || (i % 19 == 11) || (i % 19 == 14))
			{
				evIndex[count] = i;
				count++;
			}
		}
	}

	public int[] getWeekendIndex();
	{
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is a weekend shift then add it
			if ((i % 19 == 15) || (i % 19 == 16) || (i % 19 == 17) || (i % 19 == 18))
			{
				weIndex[count] = i;
				count++;
			}
		}
	}

	//returns an ArrayList of all employees that are free for that shift or an array with all employees that are most free
	public ArrayList<Employee> getAvailability(ArrayList<Employees> employees, int currShift)
	{
		ArrayList<Employee> available;
		ArrayList<Employee> temp1;  //used to track availability = to 1
		ArrayList<Employee> temp2;  //used to track availability = to 2

		for(int i = 0; i < employees.size(); i++)
		{
			if(employees[i].constraints[currShift] == 3)
			{
				available.add(employees[i]);  //if employee is available add them to the list of available employees
				continue;
			}

			if(employees[i].constraints[currShift] == 2)
			{
				temp2.add(employees[i]);  //this temp will be filled with all twos and used as output if the available arraylist is empty
			}

			if(employees[i].constraints[currShift] == 1)
			{
				temp1.add(employees[i]);  //this temp will be filled with all ones and used as output if the temp2 arraylist is empty
			}  
			//note: availability of 0 is ignored because they will not be scheduled
		}

		if(available.isEmpty())
		{
			if(temp2.isEmpty())
			{
				return temp1;  //if there is no employee with availability of 3 or 2 then return all the 1s
			}

			return temp2;  //if there is no 3 availability then return the 2s
		}

		return available;  //should contain every employee with a 3 
	}
	
}
