import java.text.MessageFormat;
import java.util.concurrent.Semaphore;

/**
 * Created by thoma on 12-Apr-17.
 */
public class Philosopher implements Runnable {
    private static int philosophers;
    private final int id;
    private Semaphore[] chopsticks;
    private final int leftChopstick;
    private final int rightChopstick;

    public Philosopher(Semaphore[] chopsticks) {
        this.chopsticks = chopsticks;
        id = philosophers;
        philosophers++;
        leftChopstick = id;
        rightChopstick = (id + 1) % chopsticks.length;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(MessageFormat.format("Philosopher {0} wants to eat.", id));
                // get left chopstick
                chopsticks[leftChopstick].acquire();
                System.out.println(MessageFormat.format("Philosopher {0} grabs his left chopstick {1}.", id, leftChopstick));
                // get right chopstick
                chopsticks[rightChopstick].acquire();
                System.out.println(MessageFormat.format("Philosopher {0} grabs his right chopstick {1}.", id, rightChopstick));

                eating();

                // return left chopstick
                System.out.println(MessageFormat.format("Philosopher {0} returns his left chopstick {1}.", id, leftChopstick));
                chopsticks[leftChopstick].release();
                // return right chopstick
                System.out.println(MessageFormat.format("Philosopher {0} returns his right chopstick {1}.", id, rightChopstick));
                chopsticks[rightChopstick].release();

                thinking();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void thinking() {
        System.out.println(MessageFormat.format("Philosopher {0} is thinking.", id));
        SleepUtilities.nap();
    }

    private void eating() {
        System.out.println(MessageFormat.format("Philosopher {0} is eating.", id));
        SleepUtilities.nap();
        System.out.println(MessageFormat.format("Philosopher {0} is done eating.", id));
    }
}
