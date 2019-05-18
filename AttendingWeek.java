import java.lang.reflect.Array;
import java.util.Objects;
import java.util.ArrayList;


public Class AttendingWeek {

    public static int daysPerPeriod = 65;
    public static int weeksPerPeriod = 13;

    private Employee[] schedule = new Employee[weeksPerPeriod];

    //get an array of approx 65 ints from each employee
    //those ints are constraints 0 being can't work, 3 being available
    //employee has num_attending_weeks that is an int (0-13) of weeks where they can work all shifts
    public Employee[] ScheduleAttendingWeeks(Employee[] employees) {  //inside the employee array should be a variable with a 65 int array

        int lowestAW = employees[0].getNumAttendingWeeks();
        Employee priorityEmpl;

        //check which employee has the lowest num_attending_weeks
        for (int i = 1; i < len(employees); i++)  //finds the lowest num attending weeks to know which employee to schedule
        {
            if(employees[i].getNumAttendingWeeks() < lowestAW && employees[i].getNumAttendingWeeks() > 0)  
            {
                lowestAW = employees[i].getNumAttendingWeeks();  
                priorityEmpl = employees[i];
                setNumAttendingWeek(14);  //resets the number this calculation is based on so the same employee won't be picked every time
            }
        }

        //assign them the earliest shift they can work
        int emplConstraints[];
        List<Employee> free[];
        Employee leastConstrained = employees[0];
        int lowestConstraint = 0;  //initialize to highest value
        boolean noFree = false;

        for (int i = 0; i < weeksPerPeriod; i++)  //for loop going through each week of a period
        {
            for (int j = 0; j < len(employees); j++)  //for loop going through each employee
            {
                emplConstraints[j] = employees[j].getAWconstraints();
                if (emplConstraints[j] == 3)  //adds any employee that is fully available for an AW to the 'free' list
                {
                    free.add(employees[j]);
                    noFree = false;
                }
                else if (emplConstraints[j] < 3 && emplConstraints[j] > lowestConstraint)  //if no one is fully available it will assign 'least constrained' empl to work
                {
                    leastConstrained = employees[j];
                    lowestConstraint = emplConstraints[j];
                    schedule[i] = leastConstrained;
                    noFree = true;
                }
            }

            if (!noFree)  //if no Free is true the schedule was correctly updated already and the next for loop isn't necessary
            { 
                priorityEmpl = free[0];

                for (int k = 1; k < free.size(); k++)  //finds the free employee with the least availability
                {
                    if (employees[k].getNumAttendingWeeks() < priorityEmpl.getNumAttendingWeeks())
                    {
                        priorityEmpl = employees[k];
                    }
                }

                schedule[i] = priorityEmpl;  //assigns best employee to work 
            }

            //must reset some values for next iteration of loop
            lowestConstraint = 0;
            free.clear();  
            noFree = false;
        }

        return schedule;
    }

}
