/**
 * Created by thoma on 13-Apr-17.
 */
public class Gathering {

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];

        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(philosophers);
        }

        for (Philosopher philosopher : philosophers) {
            new Thread(philosopher).start();
        }
    }
}
