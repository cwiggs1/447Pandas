from priority_queue import PriorityQueue


class Shift(object):
    def __init__(self, day, shift_no, shift_id):
        self.shift_queue = PriorityQueue()
        self.day = day # mo tu we th fr sa su
        self.shift_no = shift_no #1, 2, 3, 4, 5. weekdays (1-3) & weekends (4-5)
        self.shift_id = shift_id

    def __str__(self):
        return 'shift_id: ' + str(self.shift_id) + ' | day: ' + str(self.day) + ' | shift_no: ' + str(self.shift_no) + '\nqueue: ' + ' '.join([str(k) + ' : ' + str(v) for k, v in self.shift_queue.queue.items()]) + '\n'

    def is_empty(self):
        return self.shift_queue.is_empty()

    def insert(self, k_id, v):
        self.shift_queue.queue[k_id] = v

    def delete(self):
        return self.shift_queue.delete()
