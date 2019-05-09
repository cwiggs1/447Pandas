package JHU_Scheduler;

import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.text.*;
import java.io.*;

public class Employee {
  public int ID;
	public String Name;
	public boolean CanMoonlight;
	public int WeeklyHourReq;
	public int ShiftCount;
}



public class Schedule {
  public int ScheduleID;

  //NEED TO ADD DATES HERE OR IN THE "Event" CLASS
  private long StartDate;
  private long EndDate;

  

}



public class Event {
  /*
    Private variables and their member functions:
  */
  private int TypeKey;
  private String TypeDescr;

  //Getters:
  int GetTypeKey() {
    return TypeKey;
  }
  String GetTypeDescr() {
    return TypeDescr;
  }

  //Setters:
  void SetTypeKey(int newKey) {
    TypeKey = newKey;
  }
  void SetTypeDecr(String newDescr) {
    TypeDescr = newDescr;
  }

  /*
    Public variables:
  */
  public int eventID;
  //NEED TIMES HERE, BOTH START AND END
  public String EmployeeName;
  public String Description;
  public int Priority;

}



public class Location {
  public String Name;
}



public class ShiftOpen {
  //Times, again...
}
