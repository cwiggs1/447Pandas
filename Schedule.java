
import java.util.*;

public class Schedule {

	public int scheduleID;
	public String name;
	public Date startDate;
	public Date endDate;
	public ArrayList<Shift> shifts;
	public ArrayList<Employee> employees;//arraylist
	public static int TOTALSHIFTS = 274;
	public static int WEEKSPERPERIOD = 13;

	Schedule(String name, Date start, Date end) {
		this.name = name;
		this.startDate = start;
		this.endDate = end;

		/*
		ArrayList<Employee> test_empls = new ArrayList<Employee>();
		test_empls.add(new Employee(0, "Arthur Dent", false));
		test_empls.add(new Employee(1, "Ford Prefect", true));
		this.employees = test_empls;
		this.shifts = new ArrayList<Shift>(TOTALSHIFTS);
		*/

		ArrayList<Employee> test_employs = new ArrayList<Employee>();
		Scanner keyboard = new Scanner(System.in);
		Random rand = new Random();
		Employee currEmploy;
		int getInt;

		//one of 3
		currEmploy = new Employee(333, "Single Test", false);
		for (int j = 0; j < 19; j++) {
			currEmploy.setPriority(j, 3);
		}
		test_employs.add(currEmploy);

		//random employee
		currEmploy = new Employee(132, "Random Test", false);
		getInt = rand.nextInt();
		for (int i = 0; i < 19; i++) {
			currEmploy.setPriority(i, getInt);
		}
		test_employs.add(currEmploy);

		generate();

		for (int j = 0; j < 274; j++) {
			System.out.println("Shift #" + j + "Employ_id =" + shifts.get(j).getEmploy_id());
		}

	}


	public int generate() {

		//algo goes here
		int[] employeeSchedule = new int[TOTALSHIFTS];  //employee ID for each shift of the entire period (essentially the schedule)

		employees = new ArrayList<Employee>();
		
		//do attending week shifts

		AttendingWeek aw = new AttendingWeek();
		Employee[] awDoctors = new Employee[/*WEEKSPERPERIOD*/10];
		
		for (int k = 1; k < 11; k++) {
			Employee toAdd = new Employee(k, "fwef", false);
			employees.add(toAdd);
		}
		
		int[] awDoctorsNums;
		awDoctorsNums = getEmployeeArray(aw.ScheduleAttendingWeeks(employees));  //getAllIDs gives the attending week algorithm an array of all employees

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
		Employee bestPick = null;
        Employee currEmpl;

        //assign a moonlighter instead if someone has a constraint infringed upon and there haven't been too many moonlighters assigned
		ArrayList<Employee> allMoonlighters = fillMoonlighters(employees);
		int numMoonlighters = 0;

		//this for loop should fill the rest of the shifts in employeeSchedule
		for (int currShift = 0; currShift < TOTALSHIFTS; currShift++)
		{
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
				}
				else if (evIndex[j] == currShift)  //check evening shifts
				{
					shiftType = 1;
				}
				else  //if it goes thorugh to this else it has checked every weekday shift so it must be weekend
				{
					shiftType = 2;
				}
			}

			//find all employees availability for this shift (could be empty arraylist if nobody is available)
			availableDoctors = getAvailability(employees, currShift);

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

				int currShift = 0;
				//assign the best employee to the current shift and update all the employee variables necessary like shift count, shift type, and hours count
				employeeSchedule[currShift] = bestPick.getEmpl_id();
				bestPick.setShift_count(bestPick.getShift_count() + 1);
				bestPick.setShift_count(shiftType + 1);  //increment the number of shifts of that type the employee has

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
		for (int a = 0; a < shifts.size(); a++)  //shifts.size should be TOTALSHIFTS (274)
		{
			shifts.get(a).setEmploy_id(employeeSchedule[a]);
		}

		return 0;
	}


	void addEmployee(Employee empl) {
		employees.add(empl);
		
		/*Employee newArray[] = new Employee[employees.length+1];
		
		for (int i = 0; i < employees.size(); i++) {
			newArray[i] = employees[i];
		}
		
		newArray[newArray.length-1] = empl;*/
	}

	//this function returns an int array of all employee IDs in the employees arraylist
	public int[] getEmployeeArray(Employee[] employees2) {
		int[] allIDs = new int[employees2.length];

		for (int i = 0; i < employees2.length; i++)
		{
			allIDs[i] = employees2.length;  //sets each spot of allIDs to a different employee ID
		}

		return allIDs;
	}

	public int[] getAWIndex() {
		int[] awIndex = new int[WEEKSPERPERIOD * 5];

		int count = 0;
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is a morning weekday shift then add it to awIndex
			if ((i % 19 == 0) || (i % 19 == 3) || (i % 19 == 6) || (i % 19 == 9) || (i % 19 == 12))
			{
				
				if (count < awIndex.length)
					awIndex[count] = i;
				count++;
			}
		}

		return awIndex;
	}

	public int[] getAfternoonIndex()
	{
		int[] afIndex = new int[WEEKSPERPERIOD * 5];

		int count = 0;
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is an afternoon weekday shift then add it
			if ((i % 19 == 1) || (i % 19 == 4) || (i % 19 == 7) || (i % 19 == 10) || (i % 19 == 13))
			{
				if (count < afIndex.length)
					afIndex[count] = i;
				count++;
			}
		}

		return afIndex;
	}

	public int[] getEveningIndex()
	{
		int[] evIndex = new int[WEEKSPERPERIOD * 5];

		int count = 0;
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is an evening weekday shift then add it
			if ((i % 19 == 2) || (i % 19 == 5) || (i % 19 == 8) || (i % 19 == 11) || (i % 19 == 14))
			{
				if (count < evIndex.length)
					evIndex[count] = i;
				count++;
			}
		}

		return evIndex;
	}

	public int[] getWeekendIndex()
	{
		int[] weIndex = new int[WEEKSPERPERIOD * 4];

		int count = 0;
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//if it is a weekend shift then add it
			if ((i % 19 == 15) || (i % 19 == 16) || (i % 19 == 17) || (i % 19 == 18))
			{
				if (count < weIndex.length)
					weIndex[count] = i;
				count++;
			}
		}

		return weIndex;
	}

	//returns an ArrayList of all employees that are free for that shift or an array with all employees that are most free
	public ArrayList<Employee> getAvailability(ArrayList<Employee> employees2, int currShift)
	{
		ArrayList<Employee> available = new ArrayList<Employee>();
		ArrayList<Employee> temp1 = new ArrayList<Employee>();  //used to track availability = to 1
		ArrayList<Employee> temp2 = new ArrayList<Employee>();  //used to track availability = to 2

		for(int i = 0; i < employees2.size(); i++)
		{
			if(employees2.get(i).getPriority(currShift) == 3)
			{
				available.add(employees2.get(i));  //if employee is available add them to the list of available employees
				continue;
			}

			if(employees2.get(i).getPriority(currShift) == 2)
			{
				temp2.add(employees2.get(i));  //this temp will be filled with all twos and used as output if the available arraylist is empty
			}

			if(employees2.get(i).getPriority(currShift) == 1)
			{
				temp1.add(employees2.get(i));  //this temp will be filled with all ones and used as output if the temp2 arraylist is empty
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

    public ArrayList<Employee> fillMoonlighters(ArrayList<Employee> employees2)
	{
		ArrayList<Employee> output = null;

		//put all possible moonlighters in an ArrayList the is the output of this function
		for(int i = 0; i < employees2.size(); i++)
		{
			if(employees2.get(i).isMoonlighter())
			{
				output.add(employees2.get(i));
			}
		}
		return output;
	}

	public int getPriorities(int id, ArrayList<Employee> employees2, int shift)
	{
		int[] priorities = new int[TOTALSHIFTS];

		for (int i = 0; i < employees2.size(); i++)
		{
			if (id == employees2.get(i).getEmpl_id())
			{
				priorities = employees2.get(i).getPriorites();
				return priorities[shift];
			}
		}
		return shift;
	}

	//returns an employee given just their id
	public Employee getEmpl(ArrayList<Employee> employees2, int id)
	{
		for (int i = 0; i < employees2.size(); i++)
		{
			if (employees2.get(i).getEmpl_id() == id)
			{
				return employees2.get(i);
			}
		}
		return employees2.get(0);
	}

}
