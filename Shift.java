public class Shift {
    private int shift_id; // 0-18 (19 shifts total)
    private int time_slot; // 1-3 (mo-fri) & 4-5 (sa-sun)
    private String day; // mo-fri

    Shift(int shift_id, int time_slot, String day){
        this.shift_id = shift_id;
        this.time_slot = time_slot;
        this.day = day;
    }

    public String toString() {
        return "\n \nshift_id: " + this.shift_id + " \ntime_slot: " + this.time_slot + " | day: " + this.day ;
    }

    //  getters & setters
    public int getShift_id() {
        return shift_id;
    }

    public int getTime_slot() {
        return time_slot;
    }

    public String getDay() {
        return day;
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



