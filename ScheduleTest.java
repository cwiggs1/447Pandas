package scheduler;

import java.util.*;

public class ScheduleTest {

	public int scheduleID;
	public String name;
	public Date startDate;
	public Date endDate;
	public ArrayList<Shift> shifts;
	public ArrayList<Employee> employees;
	public static int TOTALSHIFTS = 274;
	public static int WEEKSPERPERIOD = 13;

	ScheduleTest(String name, Date start, Date end) {
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

		//declare variables for future use
		ArrayList<Employee> availableDoctors;
		int shiftType;  //afternoon shift is 0, evening is 1, weekend is 2
		int moonlighterCount = 0;
		Employee bestPick;

		//this for loop should fill the rest of the shifts in employeeSchedule
		for (int currShift = 0; currShift < TOTALSHIFTS; currShift++)
		{
			//System.out.println(currShift);
			
			//System.out.println(employeeSchedule[currShift]);
			
			//first it checks if the schedule slot is already filled from the attending week schedule
			if(employeeSchedule[currShift] != 0)
			{
				continue;  //if the slot is already filled then skip it.
			}

			//figure out the type of shift by cycling through max number of type of shifts (13 weeks times 5 days a week)
			for(int j = 0; j < (WEEKSPERPERIOD * 5); j++)
			{
				//System.out.println(j);
				
				//System.out.println(afIndex[j]);
				//System.out.println(currShift);
				//System.out.println(evIndex[j]);
				
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
			bestPick = availableDoctors[0];  //initialize bestPick to something, it will start at 0 in the following loop anyways

			//System.out.println(availableDoctors.size());
			
			if(availableDoctors.isEmpty())
			{
				//assign a moonlighter
				moonlighterCount += 1;
			}
			else  //search through available doctors and pick one based on how many shifts they have 
			{
				for (int k = 0; k < availableDoctors.size(); k++)
				{
					//System.out.println(k);
					
					//if they had a shift one or two spots ago, don't assign them
					if ((employeeSchedule[currShift - 1] == availableDoctors[k].getEmpl_id()) || (employeeSchedule[currShift - 2] == availableDoctors[k].getEmpl_id())
					{
						//not considered in bestPick
						continue;
					}
					else 
					{
						//make this doctor the best pick if they have fewer shifts in the shift type category than the current best pick
						if (availableDoctors[k].shift_type[shiftType] <= bestPick.shift_type[shiftType])  //this should be comparing two ints
						{
							bestPick = availableDoctors[k];
						}	
						else if (availableDoctors[k].shift_type[shiftType] == bestPick.shift_type[shiftType])
						{
							//if they have the same number of shifts of this type, take the one with less overall shifts
							if (availableDoctors[k].shift_count < bestPick.shift_count)
							{
								bestPick = availableDoctors[k];
							}
						}
					}	
				}

				//assign the best employee to the current shift and update all the employee variables necessary like shift count, shift type, and hours count 
				employeeSchedule[currShift] = bestPick.getEmpl_id();
				bestPick.shift_count += 1;
				bestPick.shift_type[shiftType] += 1;  //increment the number of shifts of that type the employee has

				//System.out.println(shiftType);
				
				if (shiftType == 2)
				{
					bestPick.hours_count += 12;
				}
				else 
				{
					bestPick.hours_count += 8;
				}
			}

		}

		//assign a moonlighter to all unfilled shifts
		//assign a moonlighter instead if someone has a constraint infringed upon and there haven't been too many moonlighters assigned
		

		//put every employee id into the shifts arraylist
		//System.out.println(shifts.size());
		
		for (int a = 0; a < shifts.size(); a++)  //shifts.size should be TOTALSHIFTS (274)
		{
			
			//System.out.println(a);
			shifts[a].setDoctor(employeeSchedule[a]);
		}
		
		return 0;
	}
	
	
	void addEmployee(Employee empl) {
		employees.add(empl);
	}

	//this function returns an int array of all employee IDs in the employees arraylist
	public Employee[] getEmployeeArray(ArrayList<Employee> employees)
	{
		Employee[] allIDs = new Employee[employees.size()];

		//System.out.println(employees.size());
		
		for (int i = 0; i < employees.size(); i++)
		{
			//System.out.println(i);
			//System.out.println(allIDs[i]);
			//System.out.println(employees.get(i));
			allIDs[i] = employees.get(i);  //sets each spot of allIDs to a different employee ID
			//System.out.println(allIDs[i]);
		}
	
		return allIDs;
	}

	public int[] getAWIndex();
	{
		int[] awIndex = new int[WEEKSPERPERIOD * 5];
		
		//System.out.println(TOTALSHIFTS);

		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//System.out.println(i);
			
			//if it is a morning weekday shift then add it to awIndex
			if ((i % 19 == 0) || (i % 19 == 3) || (i % 19 == 6) || (i % 19 == 9) || (i % 19 == 12))
			{
				//System.out.println(awIndex[count]);
				awIndex[count] = i;
				//System.out.println(awIndex[count]);
				
				//System.out.println(count);
				count++;
				//System.out.println(count);
			}
		}

		return awIndex;
	}

	public int[] getAfternoonIndex();
	{
		int[] afIndex = new int[WEEKSPERPERIOD * 5];

		//System.out.println(TOTALSHIFTS);
		
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//System.out.println(i);
			
			//if it is an afternoon weekday shift then add it 
			if ((i % 19 == 1) || (i % 19 == 4) || (i % 19 == 7) || (i % 19 == 10) || (i % 19 == 13))
			{
				//System.out.println(afIndex[count]);
				afIndex[count] = i;
				//System.out.println(afIndex[count]);
				
				//System.out.println(count);
				count++;
				//System.out.println(count);
			}
		}

		return afIndex;
	}

	public int[] getEveningIndex();
	{
		int[] evIndex = new int[WEEKSPERPERIOD * 5];

		//System.out.println(TOTALSHIFTS);
		
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//System.out.println(i);
			
			//if it is an evening weekday shift then add it
			if ((i % 19 == 2) || (i % 19 == 5) || (i % 19 == 8) || (i % 19 == 11) || (i % 19 == 14))
			{
				//System.out.println(evIndex[count]);
				evIndex[count] = i;
				//System.out.println(evIndex[count]);
				
				//System.out.println(count);
				count++;
				//System.out.println(count);
			}
		}

		return evIndex;
	}

	public int[] getWeekendIndex();
	{
		int[] weIndex = new int[WEEKSPERPERIOD * 4];

		//System.out.println(TOTALSHIFTS);
		
		for (int i = 0; i < TOTALSHIFTS; i++)
		{
			//System.out.println(i);
			
			//if it is a weekend shift then add it
			if ((i % 19 == 15) || (i % 19 == 16) || (i % 19 == 17) || (i % 19 == 18))
			{
				//System.out.println(weIndex[count]);
				//System.out.println(i);
				weIndex[count] = i;
				//System.out.println(weIndex[count]);
				
				//System.out.println(count);
				count++;
				//System.out.println(count);
			}
		}

		return weIndex;
	}

	//returns an ArrayList of all employees that are free for that shift or an array with all employees that are most free
	public ArrayList<Employee> getAvailability(ArrayList<Employees> employees, int currShift)
	{
		ArrayList<Employee> available;
		ArrayList<Employee> temp1;  //used to track availability = to 1
		ArrayList<Employee> temp2;  //used to track availability = to 2

		for(int i = 0; i < employees.size(); i++)
		{
			//System.out.println(i);
			//System.out.println(employees[i].constraints[currShift]);
			
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

		//System.out.println(available.size());
		
		if(available.isEmpty())
		{
			//System.out.println(temp2.size());
			if(temp2.isEmpty())
			{
				return temp1;  //if there is no employee with availability of 3 or 2 then return all the 1s
			}

			return temp2;  //if there is no 3 availability then return the 2s
		}

		return available;  //should contain every employee with a 3 
	}

}
