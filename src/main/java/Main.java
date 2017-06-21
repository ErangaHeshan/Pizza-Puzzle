/**
 * Created by eranga on 6/21/17.
 */
public class Main {

    public static void main(String args[]) {
        final Classroom c = new Classroom(6);
        final int numStudents = 10;

        for(int i=0; i<numStudents; i++) {
            (new Thread(new Student(c))).start();
        }
        (new Thread(new DeliveryMan(c))).start();
    }

}
