import java.util.concurrent.Semaphore;

/**
 * Created by thoma on 12-Apr-17.
 */
public class Main {
    static final int PHILOSOPHERS = 5;

    public static void main(String[] args) {
        Semaphore[] chopsticks = new Semaphore[5];
        for (int i = 0; i < PHILOSOPHERS; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        Thread[] threads = new Thread[PHILOSOPHERS];
        for (int i = 0; i < PHILOSOPHERS; i++) {
            Thread thread = new Thread(new Philosopher(chopsticks));
            threads[i] = thread;
            thread.start();
            SleepUtilities.nap(1); // we don't want a deadlock right from the start
        }
    }
}
