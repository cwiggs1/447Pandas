import java.lang.reflect.Array;
import java.util.Objects;
import java.util.ArrayList;

public class Motion {

    public static void do_stuff(){

        ArrayList<Shift> shifts = new ArrayList<Shift>();

        String[] weekdays = {"mo", "tu", "we", "th", "fr", "sa", "su"};

        int i = 0;

        for (String day: weekdays) {

            if (!day.equals("sa") && !day.equals("su")) {

                shifts.add(new Shift(i, 1, day));
                i += 1;
                shifts.add(new Shift(i, 2, day));
                i += 1;
                shifts.add(new Shift(i, 3, day));
                i += 1;
            }
            else {

                shifts.add(new Shift(i, 4, day));
                i += 1;
                shifts.add(new Shift(i, 5, day));
                i += 1;
            }
        }

        System.out.println(shifts);

    }



}
