package JHU_Scheduler;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.*;
import java.time.*;

/*
What needs to be included for CSV:
- Employee.Name

- Event.Description
- Event.StartDate
- Event.EndDate

- Location.Name (Remember to include quotes around the Location should there be a comma in between)
*/


//This Class will require Lists of the employees (or just one for the single file for the regular employee), shifts, and the Location.Name
// It will also require the change of the Event class to be within the Employee class in an array and each one should include a "LocalDateTime" variable that can be parsed for date and time of the shift the employee has
public class OpenCSWriter{

  /*
  This will make a custom schedule for an employee

  NEEDS:
  - Array of Employee objects
  - Array of shifts
  - Array of Location objects
  - How many iterations of Employees it muct do (just do employeeArray.length)
  */
  public OpenCSWriter_Master(Employee[] currEmploys, Shift[] shifts, int iterations) {

    for (int e = iterations; e > 0; e--) {

      String filePath = "c://documents//" + Integer.toString(currEmploys[e].empl_id) + "_schedule.csv";
      File file = new File(filePath);
      try (
      FileWriter outputfile = new FileWriter(file);
      CSVWriter writer = new CSVWriter(outputfile);

      String[] header {"Subject", "Start Date", "Start Time", "End Date", "End Time", "Description", "Location"};
      writer.writeNext(header);

      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      SimpleDateFormat timeFormat = new DateTimeFormatter.ofPattern("h:mm a");

      //This for loop assumes that the event times match the schedule dates...
      String start_time, end_time, start_date, end_date;
      for (int i = shifts.length - 1; i >= 0; i--) {

        //check to see if the current event belongs to this employee
        if (shifts[i].employ_id != currEmploys[e].empl_id) {
          System.out.println("This event - "+ shifts[i].shift_id+" - does not match the Employee, " +currEmploys[e].Name);
          continue;
        }

        /*
        Section sets up the times and dates so they can be loaded into an array
        */

        //'*1000' converts it to miliseconds from seconds
        start_time = (shifts[i].StartTime).format(timeFormat);
        start_date = (shifts[i].StartTime).format(dateFormat);

        end_time = (shifts[i].EndTime).format(timeFormat);
        end_date = (shifts[i].EndTime).format(dateFormat);

        /*
        Section starts putting stuff into the array and then it is written in
        */

        //assumes that the location array is the same length as the event array
        String[] line {"Shift #" + (Integer.toString(shifts[i].getShift_id())),
        start_date, start_time, end_date, end_time};
        writer.writeNext(line);

      }

      /*
      Done writing to file, so end the connection to file
      */
      writer.close();

      )

      catch (IOException | ArrayIndexOutOfBoundsException e) {
        e.printStackTrace();
      }

    }

  }

}
