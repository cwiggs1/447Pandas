public class Shift {
  private int shift_id; // 0-18 (19 shifts total)
  private int time_slot; // 1-3 (mo-fri) & 4-5 (sa-sun)
  private String day; // mo-fri
  private int employ_id; // the id of the employee that this belongs to
  private LocalDateTime StartDateTime;
  private LocalDateTime EndDateTime;

  Shift(int shift_id, int time_slot, String day){
    this.shift_id = shift_id;
    this.time_slot = time_slot;
    this.day = day;
    this.employ_id = -1;
    this.StartDateTime = null;
    this.EndDateTime = null;
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

  public void assignTime(LocalDateTime startDate, int numWeeks) {
    //will bring in a starting date and time for the period - will be
    // the 0 index

    //idea: 1) calculate the days using the 'numWeeks' and the type of
    // shift with a switch statement and within that one switch statement
    // 2) have it calculate the time based on the type of shift given

    //1) convert weeks to days
    long newDays = numWeeks * 7;

    //2) figure out the day of the week that this Shift class falls on
    int dayOfWeek = seekDOW();

    //3) change the date with add 'newDays' and 'dayOfWeek'
    newDays += dayOfWeek;

    //4) use 'plusDays(newDays)' to make a new LocalDateTime for the Start and End
    LocalDateTime newDateTime = startDate.plusDays(newDays);

    //5) now need to find out the timeslot
    // findTimeSlot();

    //6) convert the time_slot to actual times
    convertTime(newDateTime.toLocalDate());
  }

  //this will load up the start and end times for those days
  public LocalTime convertTime(LocalDate date) {
    LocalTime time;
    String timeString_1;
    String timeString_2;

    if (this.time_slot == 1) {
      timeString_1 = "07:00";
      timeString_2 = "15:30";
    }
    else if (this.time_slot == 2) {
      timeString_1 = "15:00";
      timeString_2 = "23:30";
    }
    else if (this.time_slot == 3) {
      timeString_1 = "23:00";
      timeString_2 = "07:30";
      date = LocalDate.plusDays(1);
    }
    else if (this.time_slot == 4) {
      timeString_1 = "07:00";
      timeString_2 = "19:00";
    }
    else if (this.time_slot == 5) {
      timeString_1 = "19:00";
      timeString_2 = "07:00";
      date = LocalDate.plusDays(1);
    }

    time = LocalTime.parse(timeString_1);

    this.StartDateTime = LocalDateTime.of(time, date);

    time = LocalTime.parse(timeString_2);

    this.EndDateTime = LocalDateTime.of(time, date)
  }

  public void findTimeSlot() {
    if (this.shift_id < 15) {
      this.time_slot = (this.shift_id % 3) + 1;
    }
    else {
      if (this.shift_id % 2 == 1) {
        this.time_slot = 4;
      }
      else {
        this.time_slot = 5;
      }
    }
  }

  public int seekDOW(){

    int day = 0;

    for (int i = 3; i < 21; i += 3) {
      if (this.shift_id < i) {
        return day;
      }
      day += 1;
    }
  }
}
