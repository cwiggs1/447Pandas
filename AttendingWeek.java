package scheduler;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.ArrayList;


public class AttendingWeek {

    public static int daysPerPeriod = 65;
    public static int weeksPerPeriod = 13;

    private ArrayList<Employee> schedule = new ArrayList<Employee>(weeksPerPeriod);

    //get an array of approx 65 ints from each employee
    //those ints are constraints 0 being can't work, 3 being available
    //employee has num_attending_weeks that is an int (0-13) of weeks where they can work all shifts
    public ArrayList<Employee> ScheduleAttendingWeeks(ArrayList<Employee> employees) {  //inside the employee array should be a variable with a 65 int array

        int lowestAW = employees.get(0).getNumAttendingWeeks();
        Employee priorityEmpl;

        //check which employee has the lowest num_attending_weeks
        for (int i = 1; i < employees.size(); i++)  //finds the lowest num attending weeks to know which employee to schedule
        {
            if(employees.get(i).getNumAttendingWeeks() < lowestAW && employees.get(i).getNumAttendingWeeks() > 0)  
            {
                lowestAW = employees.get(i).getNumAttendingWeeks();  
                priorityEmpl = employees.get(i);
                employees.get(i).setNumAttendingWeek(14);  //resets the number this calculation is based on so the same employee won't be picked every time
            }
        }

        //assign them the earliest shift they can work
        double emplConstraints[] = new double[employees.size()];
        ArrayList<Employee> free = new ArrayList<Employee>();
        Employee leastConstrained = employees.get(0);
        double lowestConstraint = 0;  //initialize to highest value
        boolean noFree = false;

        for (int i = 0; i < weeksPerPeriod; i++)  //for loop going through each week of a period
        {
            for (int j = 0; j < employees.size(); j++)  //for loop going through each employee
            {
                emplConstraints[j] = employees.get(j).getAWconstraints()[i];
                if (emplConstraints[j] == 3)  //adds any employee that is fully available for an AW to the 'free' list
                {
                    free.add(employees.get(j));
                    noFree = false;
                }
                else if (emplConstraints[j] < 3 && emplConstraints[j] > lowestConstraint)  //if no one is fully available it will assign 'least constrained' empl to work
                {
                    leastConstrained = employees.get(j);
                    lowestConstraint = emplConstraints[j];
                    schedule.set(i, leastConstrained);
                    noFree = true;
                }
            }

            if (!noFree)  //if no Free is true the schedule was correctly updated already and the next for loop isn't necessary
            { 
                priorityEmpl = free.get(0);

                for (int k = 1; k < free.size(); k++)  //finds the free employee with the least availability
                {
                    if (employees.get(k).getNumAttendingWeeks() < priorityEmpl.getNumAttendingWeeks())
                    {
                        priorityEmpl = employees.get(k);
                    }
                }

                schedule.set(i, priorityEmpl);  //assigns best employee to work 
            }

            //must reset some values for next iteration of loop
            lowestConstraint = 0;
            free.clear();  
            noFree = false;
        }

        return schedule;
    }

}