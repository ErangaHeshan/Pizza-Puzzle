import static java.lang.Thread.sleep;

/**
 * Created by eranga on 6/21/17.
 */
public class Student implements Runnable{

    Classroom c;

    public Student(Classroom c) {
        this.c = c;
    }

    public void run(){
        while (true) {
            try {
                c.study();
                // time to study while eating pizza
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
