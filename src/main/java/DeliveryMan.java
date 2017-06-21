/**
 * Created by eranga on 6/21/17.
 */
public class DeliveryMan implements Runnable {
    Classroom c;

    public DeliveryMan(Classroom c) {
        this.c = c;
    }

    public void run(){
        while (true) {
            try {
                c.deliver();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
