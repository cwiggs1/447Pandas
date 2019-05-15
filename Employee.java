//7AM-3:30PM
//3:30PM-12AM

//SAT AND SUN ARE EVENINGS ONLY

public class Employee {

  private int empl_id;
  private String name;
  private boolean moonlighter;
  private int shift_count;
  private int hours_count;
  private int max_hrs;
  private int[] priorites;
  private double avg_priorites;
  private int num_attending_weeks;

  Employee(int empl_id, String name, boolean moonlighter){
    this.empl_id = empl_id;
    this.name = name;
    this.moonlighter = moonlighter;
    this.shift_count = 0; //start w/ zero
    this.hours_count = 0; //start w/ zero
    this.max_hrs = 40;
    int NUM_SHIFTS = 19;
    this.priorites = new int[NUM_SHIFTS];
    this.avg_priorites = 0;
    this.num_attending_weeks = -1;
  }

  // getters and setters
  public int getHours_count() {
    return hours_count;
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

  public void setHours_count(int hours_count){
    this.hours_count = hours_count;
  }

  public void setAvgPriority(double avg_priorites) {
    this.avg_priorites = avg_priorites;
  }

  public void setNumAttendingWeek(int num_attending_weeks) {
    this.num_attending_week = num_attending_weeks;
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
  
}
