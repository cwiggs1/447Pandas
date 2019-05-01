from shift import *

if __name__ == '__main__':
    #creates 19 shifts/wk
    shifts = []
    weekdays = ['mo', 'tu', 'we', 'th', 'fr', 'sa', 'su']
    i = 1

    for day in weekdays:
        if (day != 'sa') and (day != 'su'):
            shifts.append(Shift(day, 1, i))
            i += 1
            shifts.append(Shift(day, 2, i))
            i += 1
            shifts.append(Shift(day, 3, i))
            i += 1
        else:
            shifts.append(Shift(day, 4, i))
            i += 1
            shifts.append(Shift(day, 5, i))
            i += 1

    for s in shifts:
        print(s)
