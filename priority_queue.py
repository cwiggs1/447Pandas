# A simple implementation of Priority Queue
# using Queue.


class PriorityQueue(object):
    def __init__(self):
        self.queue = {}

    def __str__(self):
        return '\n'.join([str(k) + ' : ' + str(v) for k, v in self.queue.items()])

    # for checking if the queue is empty
    def is_empty(self):
        return len(self.queue) == 0

    # for inserting an element in the queue
    # O(1)
    def insert(self, k_id, v):
        self.queue[k_id] = v

    # for popping an element based on Priority
    # O(n)
    def delete(self):
        try:
            min = 100 #over upper bound for priority
            min_id = -1
            for k, v in self.queue.items():
                if v < min:
                    min = v
                    min_id = k
            del self.queue[min_id]
            return min_id
        except IndexError:
            print()
            exit()


if __name__ == '__main__':
    myQueue = PriorityQueue()
    myQueue.insert(1, 12)
    myQueue.insert(2, 1)
    myQueue.insert(3, 14)
    myQueue.insert(4, 7)
    print(myQueue)
    while not myQueue.is_empty():
        print(myQueue.delete())

