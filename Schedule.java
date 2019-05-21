package scheduler;

import java.util.*;
import java.time.*;

public class Schedule {

	public int scheduleID;
	public String name;
	public LocalDateTime startDate;
	public LocalDateTime endDate;
	public ArrayList<Shift> shifts;
	public ArrayList<Employee> employees;
	public static int TOTALSHIFTS = 247;
	public static int WEEKSPERPERIOD = 13;

	Schedule(String name, LocalDateTime start, LocalDateTime end) {
		this.name = name;
		this.startDate = start;
		this.endDate = end;
		this.shifts = new ArrayList<Shift>();
		this.employees = new ArrayList<Employee>();

		int count = 0;
		Shift currShift = new Shift(count, 0, "N/A");
		for (int i = 0; i < TOTALSHIFTS; i++) {
			this.shifts.add(currShift);
			count += 1;
			if (count > 18) {
				count = 0;
			}
			currShift = new Shift(count, 0, "N/A");
		}

		testTime(4);

	}

	public void testTime(int testNum) {
		int x = 0;
		Scanner keyboard = new Scanner(System.in);
		Random rand = new Random();
		Employee currEmploy;
		int getInt;

		//May have to put 'else if' instead of just 'if's

		if (testNum == 1 || testNum == 0) {

			//one with all 3
			currEmploy = new Employee(333, "Single Test", false);
			for (int j = 0; j < 247; j++) {
				currEmploy.setPriority(j, 3);
			}
			this.employees.add(currEmploy);

		}

		if (testNum == 2 || testNum == 0) {

			//3 employees with a random weeks
			for (int k = 1; k < 4; k++) {
				currEmploy = new Employee(k, "Random Test#" + k, false);
				for(int y = 0; y < 247; y++) {
					getInt = rand.nextInt(4);
					currEmploy.setPriority(y, getInt);
				}
				this.employees.add(currEmploy);
			}

		}

		if (testNum == 3 || testNum == 0) {

			//random employee
			currEmploy = new Employee(132, "Random Test", false);
			getInt = rand.nextInt(4);
			for (int i = 0; i < 247; i++) {
				currEmploy.setPriority(i, getInt);
			}
			this.employees.add(currEmploy);

		}

		if (testNum == 4 || testNum == 0) {

			//four employess with the same priorities except for the first week
			for (int i = 1; i < 5; i++) {
				currEmploy = new Employee(i, "Four Test", false);
				for (int j = 0; j < 19; j++) {
					currEmploy.setPriority(j, i-1);
				}
				for (int h = 19; h < 247; h++) {
					currEmploy.setPriority(h, 3);
				}
				this.employees.add(currEmploy);
			}

		}

		if (testNum == 5 || testNum == 0) {

			//4 employees with a random weeks and one moonlighter
			for (int k = 1; k < 5; k++) {
				currEmploy = new Employee(k, "Random Test#" + k, false);
				for(int y = 0; y < 247; y++) {
					getInt = rand.nextInt(4);
					currEmploy.setPriority(y, getInt);
				}
				this.employees.add(currEmploy);
			}

			currEmploy = new Employee(9, "Moonlighter", true);
			for(int t = 0; t < 247; t++) {
				getInt = rand.nextInt(4);
				currEmploy.setPriority(t, getInt);
			}
		}

	generate();

	printShifts();

}

public void printShifts() {
	for (int x = 0; x < 247; x++) {
		System.out.println("Shift #" + x + " Employee ID: " + (this.shifts.get(x)).getEmploy_id());
	}
}

public int generate() {

	//algo goes here
	int[] employeeSchedule = new int[TOTALSHIFTS];  //employee ID for each shift of the entire period (essentially the schedule)

	//do attending week shifts

	AttendingWeek aw = new AttendingWeek();
	Employee[] awDoctors = new Employee[WEEKSPERPERIOD];
	awDoctors = getEmployeeArray(aw.ScheduleAttendingWeeks(this.employees));  //getAllIDs gives the attending week algorithm an array of all employees

	int[] awIndex = new int[WEEKSPERPERIOD * 5]; //an array of the index of all morning weekday shifts in employeeSchedule
	int[] afIndex = new int[WEEKSPERPERIOD * 5];  //index of all afternoon shifts within the 274
	int[] evIndex = new int[WEEKSPERPERIOD * 5];  //index of all evening shifts within the 274
	int[] weIndex = new int[WEEKSPERPERIOD * 4];  //index of all weekend shifts within the 274
	int weekCount = 0;

	//set all index arrays to their correct numbers
	awIndex = getAWIndex();
	afIndex = getAfternoonIndex();
	evIndex = getEveningIndex();
	weIndex = getWeekendIndex();

	//put awIndex into employeeSchedule
	for (int i = 0; i < (WEEKSPERPERIOD * 5); i++)
	{
		employeeSchedule[awIndex[i]] = awDoctors[weekCount].getEmpl_id();  //every time a doctor is put into employee schedule their shift count and hour count should be increased
		awDoctors[weekCount].setShift_count(awDoctors[weekCount].getShift_count() + 1);
		awDoctors[weekCount].setHours_count(awDoctors[weekCount].getHours_count() + 8);  //aw shifts are always 8 hour morning shifts

		if (i % 5 == 4)  //after every fifth shift (friday) is assigned, increment the week count
		{
			weekCount++;
		}
	}

	//declare variables for future use
	ArrayList<Employee> availableDoctors;
	int shiftType = 0;  //afternoon shift is 0, evening is 1, weekend is 2
	int moonlighterCount = 0;
	Employee bestPick = new Employee(-1, "DUMMY EMPLOYEE", false);
	Employee currEmpl;
	int week = -1;

	//assign a moonlighter instead if someone has a constraint infringed upon and there haven't been too many moonlighters assigned
	ArrayList<Employee> allMoonlighters = fillMoonlighters(employees);
	int numMoonlighters = 0;

	//this for loop should fill the rest of the shifts in employeeSchedule
	for (int currShift = 0; currShift < TOTALSHIFTS; currShift++)
	{
		//this is for knowing when the attending doctor is trying to be scheduled
		if(currShift % 19 == 0) {
			week += 1;
		}
		//first it checks if the schedule slot is already filled from the attending week schedule
		if(employeeSchedule[currShift] != 0)
		{
			continue;  //if the slot is already filled then skip it.
		}

		//figure out the type of shift by cycling through max number of type of shifts (13 weeks times 5 days a week)
		for(int j = 0; j < (WEEKSPERPERIOD * 5); j++)
		{
			if(afIndex[j] == currShift)  //check afternoon shifts
			{
				shiftType = 0;
				break;
			}
			else if (evIndex[j] == currShift)  //check evening shifts
			{
				shiftType = 1;
				break;
			}
			else  //if it goes thorugh to this else it has checked every weekday shift so it must be weekend
			{
				shiftType = 2;
				break;
			}
		}

		//find all employees availability for this shift (could be empty arraylist if nobody is available) - the attending doctor
		availableDoctors = getAvailability(employees, currShift, awDoctors[week]);

		if(availableDoctors.isEmpty())
		{
			//assign a moonlighter
			for(int i = 0; i < allMoonlighters.size(); i++)
			{
				if (allMoonlighters.get(i).getShift_count() < bestPick.getShift_count())
				{
					bestPick = allMoonlighters.get(i);
				}
			}

			moonlighterCount += 1;
		}
		else  //search through available doctors and pick one based on how many shifts they have
		{
			for (int k = 0; k < availableDoctors.size(); k++)
			{

				if(currShift > 1) {

					//if they had a shift one or two spots ago, don't assign them
					if ((employeeSchedule[currShift - 1] == availableDoctors.get(k).getEmpl_id()) || (employeeSchedule[currShift - 2] == availableDoctors.get(k).getEmpl_id()))
					{
						//not considered in bestPick
						continue;
					}
					else
					{
						//make this doctor the best pick if they have fewer shifts in the shift type category than the current best pick
						if (availableDoctors.get(k).getShiftType()[shiftType] <= bestPick.getShiftType()[shiftType])  //this should be comparing two ints
						{
							bestPick = availableDoctors.get(k);
						}
						else if (availableDoctors.get(k).getShiftType()[shiftType] == bestPick.getShiftType()[shiftType]) {
							//if they have the same number of shifts of this type, take the one with less overall shifts
							if (availableDoctors.get(k).getShift_count() < bestPick.getShift_count()) {
								bestPick = availableDoctors.get(k);
							}
						}
					}
				}
				//the currShift is less than or equal to 1
				// this is so the previous block doesn't get an array out of bounds error
				else {
					//make this doctor the best pick if they have fewer shifts in the shift type category than the current best pick
					if (availableDoctors.get(k).getShiftType()[shiftType] <= bestPick.getShiftType()[shiftType])  //this should be comparing two ints
					{
						bestPick = availableDoctors.get(k);
					}
					else if (availableDoctors.get(k).getShiftType()[shiftType] == bestPick.getShiftType()[shiftType]) {
						//if they have the same number of shifts of this type, take the one with less overall shifts
						if (availableDoctors.get(k).getShift_count() < bestPick.getShift_count()) {
							bestPick = availableDoctors.get(k);
						}
					}
				}
			}

			//assign the best employee to the current shift and update all the employee variables necessary like shift count, shift type, and hours count
			employeeSchedule[currShift] = bestPick.getEmpl_id();
			bestPick.setShift_count(bestPick.getShift_count() + 1);
			bestPick.getShiftType()[shiftType] += 1;  //increment the number of shifts of that type the employee has

			if (shiftType == 2) {
				bestPick.setHours_count(bestPick.getHours_count() + 12);
			}
			else {
				bestPick.setHours_count(bestPick.getHours_count() + 8);
			}
		}

	}

	//moonlighter for loop
	for (int b = 0; b < TOTALSHIFTS; b++)
	{
		//assign a moonlighter to any shift with a constraint of 1 if there are enough extra moonlighters
		if(getPriorities(employeeSchedule[b], employees, b) == 1 && numMoonlighters <= WEEKSPERPERIOD)
		{
			//must account for the shift we are taking away from someone else
			currEmpl = getEmpl(employees, employeeSchedule[b]);

			//figure out the type of shift and decrement hours of employee with shift covered by moonlighter)
			for(int j = 0; j < (WEEKSPERPERIOD * 5); j++)
			{
				if(afIndex[j] == b)  //check afternoon shifts
				{
					shiftType = 0;
					currEmpl.setHours_count(currEmpl.getHours_count() - 8);
				}
				else if (evIndex[j] == b)  //check evening shifts
				{
					shiftType = 1;
					currEmpl.setHours_count(currEmpl.getHours_count() - 8);
				}
				else  //if it goes thorugh to this else it has checked every weekday shift so it must be weekend
				{
					shiftType = 2;
					currEmpl.setHours_count(currEmpl.getHours_count() - 12);
				}
			}

			bestPick.setShift_count(bestPick.getShift_count() - 1);
			bestPick.getShiftType()[shiftType] -= 1;  //increment the number of shifts of that type the employee has



			//assign a moonlighter
			for(int i = 0; i < allMoonlighters.size(); i++)
			{
				if (allMoonlighters.get(i).getShift_count() < bestPick.getShift_count())
				{
					bestPick = allMoonlighters.get(i);
				}
			}

			//assign the best employee to the current shift and update all the employee variables necessary like shift count, shift type, and hours count
			employeeSchedule[b] = bestPick.getEmpl_id();
			bestPick.setShift_count(bestPick.getShift_count() + 1);
			bestPick.getShift_type()[shiftType] += 1;  //increment the number of shifts of that type the employee has

			if (shiftType == 2) {
				bestPick.setHours_count(bestPick.getHours_count() + 12);
			}
			else {
				bestPick.setHours_count(bestPick.getHours_count() + 8);
			}

			numMoonlighters += 1;
		}
	}
	//moonlighter stuff done

	//put every employee id into the shifts arraylist
	for (int a = 0; a < this.shifts.size(); a++)  //shifts.size should be TOTALSHIFTS (274)
	{
		this.shifts.get(a).setEmploy_id(employeeSchedule[a]);
	}

	return 0;
}


void addEmployee(Employee empl) {
	employees.add(empl);
}

//this function returns an int array of all employee IDs in the employees arraylist
public Employee[] getEmployeeArray(ArrayList<Employee> employees) {
	Employee[] allIDs = new Employee[employees.size()];

	for (int i = 0; i < employees.size(); i++)
	{
		allIDs[i] = employees.get(i);  //sets each spot of allIDs to a different employee ID
	}

	return allIDs;
}

public int[] getAWIndex() {
	int[] awIndex = new int[(WEEKSPERPERIOD * 5) + 1];

	int count = 0;
	for (int i = 0; i < TOTALSHIFTS; i++)
	{
		//if it is a morning weekday shift then add it to awIndex
		if ((i % 19 == 0) || (i % 19 == 3) || (i % 19 == 6) || (i % 19 == 9) || (i % 19 == 12))
		{
			awIndex[count] = i;
			count++;
		}
	}

	return awIndex;
}

public int[] getAfternoonIndex()
{
	int[] afIndex = new int[(WEEKSPERPERIOD * 5) + 1];

	int count = 0;
	for (int i = 0; i < TOTALSHIFTS; i++)
	{
		//if it is an afternoon weekday shift then add it
		if ((i % 19 == 1) || (i % 19 == 4) || (i % 19 == 7) || (i % 19 == 10) || (i % 19 == 13))
		{
			afIndex[count] = i;
			count++;
		}
	}

	return afIndex;
}

public int[] getEveningIndex()
{
	int[] evIndex = new int[(WEEKSPERPERIOD * 5) + 1];

	int count = 0;
	for (int i = 0; i < TOTALSHIFTS; i++)
	{
		//if it is an evening weekday shift then add it
		if ((i % 19 == 2) || (i % 19 == 5) || (i % 19 == 8) || (i % 19 == 11) || (i % 19 == 14))
		{
			evIndex[count] = i;
			count++;
		}
	}

	return evIndex;
}

public int[] getWeekendIndex()
{
	int[] weIndex = new int[(WEEKSPERPERIOD * 4) + 1];

	int count = 0;
	for (int i = 0; i < TOTALSHIFTS; i++)
	{
		//if it is a weekend shift then add it
		if ((i % 19 == 15) || (i % 19 == 16) || (i % 19 == 17) || (i % 19 == 18))
		{
			weIndex[count] = i;
			count++;
		}
	}

	return weIndex;
}

//returns an ArrayList of all employees that are free for that shift or an array with all employees that are most free
public ArrayList<Employee> getAvailability(ArrayList<Employee> employees, int currShift, Employee attendDoc)
{
	ArrayList<Employee> available = new ArrayList<Employee>();
	ArrayList<Employee> temp1 = new ArrayList<Employee>();  //used to track availability = to 1
	ArrayList<Employee> temp2 = new ArrayList<Employee>();  //used to track availability = to 2

	for(int i = 0; i < employees.size(); i++)
	{
		if (employees.get(i).getEmpl_id() != attendDoc.getEmpl_id()) {

			if(employees.get(i).getPriority(currShift) == 3)
			{
				available.add(employees.get(i));  //if employee is available add them to the list of available employees
				continue;
			}

			if(employees.get(i).getPriority(currShift) == 2)
			{
				temp2.add(employees.get(i));  //this temp will be filled with all twos and used as output if the available arraylist is empty
			}

			if(employees.get(i).getPriority(currShift) == 1)
			{
				temp1.add(employees.get(i));  //this temp will be filled with all ones and used as output if the temp2 arraylist is empty
			}
			//note: availability of 0 is ignored because they will not be scheduled
		}

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

public ArrayList<Employee> fillMoonlighters(ArrayList<Employee> employees)
{
	ArrayList<Employee> output = new ArrayList<Employee>();

	//put all possible moonlighters in an ArrayList the is the output of this function
	for(int i = 0; i < employees.size(); i++)
	{
		if(employees.get(i).isMoonlighter())
		{
			output.add(employees.get(i));
		}
	}

	return output;
}

public int getPriorities(int id, ArrayList<Employee> employees, int shift)
{
	int[] priorities = new int[TOTALSHIFTS];

	for (int i = 0; i < employees.size(); i++)
	{
		if (id == employees.get(i).getEmpl_id())
		{
			priorities = employees.get(i).getPriorites();
			return priorities[shift];
		}
	}

	return priorities[0];
}

//returns an employee given just their id
public Employee getEmpl(ArrayList<Employee> employees, int id)
{
	for (int i = 0; i < employees.size(); i++)
	{
		if (employees.get(i).getEmpl_id() == id)
		{
			return employees.get(i);
		}
	}

	return new Employee(-1, "Dummy", false);

}

}
