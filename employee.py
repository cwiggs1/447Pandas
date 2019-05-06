

class Employee(object):
    def __init__(self, empl_id):
        self.empl_id = empl_id
        self.event_priorities = {}

    def set_event_priority(self, event_id, priority):
        self.event_priorities[event_id] = priority

    def compute_average_pri(self):
        summy = 0
        size = 0
        for k, v in self.event_priorities.items():
            summy += v
            size += 1
        avg = summy/size
        return avg

    
