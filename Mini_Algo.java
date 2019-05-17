//don't know how to import the Employee class and the Shift class...

//this class is to be able to return two arrays
public class Mini_Algo {

  //this requires an array of already ordered morning shifts - the same order as they are added in the
  // Employee's 'morning_priorites' array
  Mini_Algo(Employee[] employs, int limitEmploy, Shift[] morningShifts, int limitShifts) {

    Employee maxEmploy = employs[0]; //used to keep track of the employee that has
    // the highest priority during that one week and will be scheduled in for that date
    Shift currShift;
    Employee currEmploy;

    for (int s = 0; s < limitShifts; s++) {
      currShift = morningShifts[s];
      for (int e = 0; e < limitEmploy; e++) {
        currEmploy = employs[e];
        if (maxEmploy.getMorningPriority(s) < currEmploy.getMorningPriority(s)
        && maxEmploy.getNumAttendingWeeks() >= currEmploy.getNumAttendingWeeks()) {
          maxEmploy = currEmploy;
        }
      }
      currShift.setEmploy_id(maxEmploy.getEmpl_id());
    }
  }
}
