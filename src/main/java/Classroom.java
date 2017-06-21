/**
 * Created by eranga on 6/21/17.
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Classroom {

    private final int s;
    private int numSlices = 0;
    // lock to ensure no slice is consumed by more than one student
    private final Lock lock = new ReentrantLock();
    // condition to call Kamal
    private final Condition emptyPizza = lock.newCondition();
    // condition to sleep while Kamal delivers pizza
    private final Condition studentSleep = lock.newCondition();
    // flag to identify the first student who finds pizza is gone
    private boolean sawFirst = true;


    public Classroom(int s) {
        this.s = s;
    }

    public void study() throws InterruptedException {
        lock.lock();
        try {
            if (numSlices > 0) {
                // student takes a slice and start studying
                numSlices--;
                System.out.println("Student " + Thread.currentThread().getName() +
                        " picked a slice and begin studying");
            } else {
                // there is no pizza and check who finds it first
                if(sawFirst){
                    sawFirst = false;
                    // first one to see pizza gone calls Kamal
                    emptyPizza.signal();
                    System.out.println("Student " + Thread.currentThread().getName() +
                            " ordered pizza");
                }
                System.out.println("Student " + Thread.currentThread().getName() +
                        " is going to sleep");
                // sleep the student until pizza delivered
                studentSleep.await();
            }
        } finally {
            lock.unlock();
        }
    }

    public void deliver() throws InterruptedException {
            lock.lock();
            try {
                // deliver pizza
                numSlices = s;
                System.out.println("Pizza delivered! Waking up students...");
                // wake up sleeping students
                studentSleep.signalAll();
                sawFirst = true;
                // sleep until a student order pizza
                emptyPizza.await();
            } finally {
                lock.unlock();
            }
    }
}
