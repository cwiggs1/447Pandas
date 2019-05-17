
package scheduler;


import java.util.*;

public class Shift {
  private int shift_id; // 0-18 (19 shifts total)
  private int time_slot; // 1-3 (mo-fri) & 4-5 (sa-sun)
  private String day; // mo-fri
  private int employ_id; // the id of the employee that this belongs to

  Shift(int shift_id, int time_slot, String day){
    this.shift_id = shift_id;
    this.time_slot = time_slot;
    this.day = day;
    this.employ_id = -1;
  }

  public String toString() {
    return "\n \nshift_id: " + this.shift_id + " \ntime_slot: " + this.time_slot + " | day: " + this.day ;
  }

  //  getters & setters
  public int getEmploy_id() {
    return employ_id;
  }

  public int getShift_id() {
    return shift_id;
  }

  public int getTime_slot() {
    return time_slot;
  }
  
  public Date getStartTime(Date startDate, int shiftArrIdx) {
	  
	  switch (time_slot) {
	  case 1:	break;
	  case 2:	break;
	  case 3:	break;
	  case 4:	break;
	  case 5:	break;
	  default:	break;
	  }
	  
	  return new Date();
	  
  }

  public String getDay() {
    return day;
  }

  public void setEmploy_id(int employ_id) {
    this.employ_id = employ_id;
  }

  public void setShift_id(int shift_id) {
    this.shift_id = shift_id;
  }

  public void setTime_slot(int time_slot) {
    this.time_slot = time_slot;
  }

  public void setDay(String day) {
    this.day = day;
  }

    // methods

}