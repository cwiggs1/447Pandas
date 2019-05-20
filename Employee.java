//7AM-3:30PM
//3PM-11:30PM
//11PM-7:30AM

//SAT AND SUN ARE EVENINGS ONLY

package scheduler;

public class Employee {


  private int empl_id;
  private String name;
  private boolean moonlighter;
  private int shift_count;
  private int hours_count;
  private int[] shift_type;
  private int max_hrs;
  private int[] priorites;
  private double[] morning_priorites;
  private double avg_priorites;
  private int num_attending_weeks;
  private double avg_morning_priority;
  private double[] AWconstraints;  //should be 13 doubles with the average priority availability for each week in the period in order
  static public final int NUM_SHIFTS = 19;
  static public final int NUM_ATTENDING_WEEKS = 13;
  
  public static int weeksPerPeriod = 13;

  Employee(int empl_id, String name, boolean moonlighter){
    this.empl_id = empl_id;
    this.name = name;
    this.moonlighter = moonlighter;
    this.shift_count = 0; //start w/ zero
    this.hours_count = 0; //start w/ zero
    this.shift_type = new int[3]; //this should initialize all values to 0. index0 is afternoon shifts, index1 is evening, index2 is weekend
    this.max_hrs = 40;
    //int NUM_SHIFTS = 19;
    this.priorites = new int[NUM_SHIFTS * NUM_ATTENDING_WEEKS];
    this.avg_priorites = 0;
    this.num_attending_weeks = 0;
    this.avg_morning_priority = 0;
    //int NUM_ATTENDING_WEEKS = 13;
    this.morning_priorites = new double[NUM_ATTENDING_WEEKS];
    this.AWconstraints = new double[weeksPerPeriod]; //should set AW constraints to the avg constraint value per week
  }

  // getters and setters
  public void setAWconstraints(int index, int value)
  {
    AWconstraints[index] = value;
  }
  public void setAvgMorningPriority(double number) {
    this.avg_morning_priority = number;
  }

  public void setMorningPriority(int index, double number) {
    this.morning_priorites[index] = number;
  }

  public double getMorningPriority(int index) {
    return morning_priorites[index];
  }

  public int getAvgMorningPriority() {
    return (int) avg_morning_priority;
  }

  public int getHours_count() {
    return hours_count;
  }

  public int[] getShiftType() {
    return shift_type;
  }

  public double getAvgPriority() {
    return avg_priorites;
  }

  public int getNumAttendingWeeks(){
    return num_attending_weeks;
  }

  public int getEmpl_id() {
    return empl_id;
  }

  public String getName() {
    return name;
  }

  public boolean isMoonlighter() {
    return moonlighter;
  }

  public int getShift_count() {
    return shift_count;
  }

  public int[] getPriorites() {
    return priorites;
  }

  public double[] getAWconstraints() { 
    return AWconstraints;
  }

  public void setHours_count(int hours_count){
    this.hours_count = hours_count;
  }

  public void setAvgPriority(double avg_priorites) {
    this.avg_priorites = avg_priorites;
  }

  public void setNumAttendingWeek(int num_attending_weeks) {
    this.num_attending_weeks = num_attending_weeks;
  }

  public void setEmpl_id(int empl_id) {
    this.empl_id = empl_id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMoonlighter(boolean moonlighter) {
    this.moonlighter = moonlighter;
  }

  public void setShift_count(int shift_count) {
    this.shift_count = shift_count;
  }

  public void setPriority(int index, int value){
    priorites[index] = value;
  }

  public int getPriority(int index){
    return priorites[index]; // value = priorities[index]
  }

  //question?


  // methods

  public double findAvgPriority() {
    for (int i = 0; i < NUM_SHIFTS; i++) {
      this.avg_priorites += priorites[i];
    }
    this.avg_priorites = this.avg_priorites / NUM_SHIFTS;
    return avg_priorites;
  }

  public double loopAttendWeekPriority(int numWeeks) {
    double tempAvg = 0;
    for (int i = (numWeeks * 19); i <= ((numWeeks * 19) + 15); i += 3) {
      tempAvg += getPriority(i);
    }
    return tempAvg;
  }

  public double setupAttendingAvgPriority() {
    double tempNumber = 0;
    //for the number of weeks during a scheduling session (13 weeks)
    for (int i = 0; i < 13; i++) {
      //call attending week function to calculate the average priority over that week
      tempNumber += loopAttendWeekPriority(i);
      setMorningPriority(i, tempNumber);
    }
    setAvgMorningPriority((tempNumber / 65));
    
    return tempNumber;
  }

  public double[] avgContraintPerWeek(double[] constraints) //should set AWconstraints variable in Employee
  { 

    int weekCount = 0;
    double weekTotal = 0;
    double[] constraintPerWeek = new double[NUM_ATTENDING_WEEKS];  //len should be 13

    for (int i = 0; i < weeksPerPeriod; i++) {

      weekTotal += constraints[i];  

        if (i % 5 == 4)  //if its a friday constraint, take the average and store it in the output array
        {
            constraintPerWeek[weekCount] = weekTotal / 5;
            weekTotal = 0;
            weekCount += 1;
        }
    }

    return constraintPerWeek;
  }

}