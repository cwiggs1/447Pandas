#include <Arduino.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

class Employee
{
	public:
	int ID;
	string Name;
	bool CanMoonlight;
	int WeeklyHourReq;
	int ShiftCount;
}

Class Schedule
{
	public:
	int ScheduleID;
	time_t StartDate;
	time_t EndDate;
}

Class Event
{
	private:
	int TypeKey;
	string TypeDescr;

	public:
	int EventID;
	time_t StartTime;
	time_t EndTime
	string EmployeeName;
	string Description
	int Priority
}

Class Location
{
	public:
	string Name;
}

class ShiftOpen
{
	public:
	time_t StartTime;
	time_t EndTime;
}


int main(int argc, const char* argv[])
{
	printf("Welcome to Scheduler!\n");

	return 0;
}
