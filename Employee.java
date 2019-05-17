package scheduler;


//7AM-3:30PM
//3:30PM-12AM

//SAT AND SUN ARE EVENINGS ONLY

public class Employee {

private int empl_id;
private String name;
//private String first_name;
//private String last_name;
private boolean moonlighter;
private boolean attend;
private int shift_count;
private int hours_count;
private int max_hrs;
private int[] priorites;

Employee(int empl_id, String name, boolean moonlighter){
    this.empl_id = empl_id;
    this.name = name;
    this.moonlighter = moonlighter;
    this.shift_count = 0; //start w/ zero
    this.hours_count = 0; //start w/ zero
    this.max_hrs = 40;
    int NUM_SHIFTS = 19;
    this.priorites = new int[NUM_SHIFTS];
}

// getters and setters
public int getEmpl_id() {
    return empl_id;
}

public String getName() {
    return name;
}

public boolean isAttending() {
    return attend;
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

public void setEmpl_id(int empl_id) {
    this.empl_id = empl_id;
}

public void setName(String name) {
    this.name = name;
}

public void setMoonlighter(boolean moonlighter) {
    this.moonlighter = moonlighter;
}

public void setIsAttending(boolean attend) {
	this.attend = attend;
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

}