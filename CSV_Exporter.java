package scheduler;

import java.io.IOException;
import java.io.Writer;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import scheduler.CSVReader.*;

/*
  What needs to be included for CSV:
    - Employee.Name
    - Event.Description
    - Event.StartDate
    - Event.EndDate
    - Location.Name (Remember to include quotes around the Location should there be a comma in between)
*/


//This Class will require Lists of the employees (or just one for the single file for the regular employee), events, and the Location.Name
// It will also require the change of the Event class to be within the Employee class in an array and each one should include a "LocalDateTime" variable that can be parsed for date and time of the shift the employee has
public class CSV_Exporter{
	
  /*
  This will make a custom schedule for an employee
  NEEDS:
  - Array of Employee objects
  - Array of events
  - Array of Location objects
  - How many iterations of Employees it muct do (just do employeeArray.length)
  */
  public CSV_Exporter(Employee[] currEmploys, Shift[] events, Location[] locations, Date startDate, int iterations) {

    for (int e = iterations; e > 0; e--) {

      String filePath = "c://documents//" + Integer.toString(currEmploys[e].getEmpl_id()) + "_schedule.csv";
      File file = new File(filePath);
      try {
      FileWriter outputfile = new FileWriter(file);
      CSVWriter writer = new CSVWriter(outputfile);

      String[] header = {"Subject", "Start Date", "Start Time", "End Date", "End Time", "Description", "Location"};
      writer.writeNext(header);

      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
      SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

      //This for loop assumes that the event times match the schedule dates...
      String start_time, end_time, start_date, end_date;
      for (int i = events.length - 1; i >= 0; i--) {

        //check to see if the current event belongs to this employee
        if (events[i].getEmploy_id() != currEmploys[e].getEmpl_id()) {
          System.out.println("This event - "+ events[i].getShift_id() +" - does not match the Employee, " +currEmploys[e].getName());
          continue;
        }

        /*
        Section sets up the times and dates so they can be loaded into an array
        */

        //'*1000' converts it to milliseconds from seconds
        
        /*
        start_time = (events[i].getStartTime(startDate, i)).format(timeFormat);
        start_date = (events[i].getStartTime(startDate, i)).format(dateFormat);

        end_time = (events[i].getStartTime(startDate, i)).format(timeFormat);
        end_date = (events[i].getStartTime(startDate, i)).format(dateFormat);
        */
        
        
        /*
        Section starts putting stuff into the array and then it is written in
        */

        //assumes that the location array is the same length as the event array
        
        /*
        String[] line = {events[i].Subject, start_date, start_time, end_date, end_time, events[i].Description,
          locations[i].Name};
          writer.writeNext(line);
          */

        }

        /*
        Done writing to file, so end the connection to file
        */
        writer.close();

    }

        catch (IOException | ArrayIndexOutOfBoundsException e1) {
          e1.printStackTrace();
        }

      }

    }

  }