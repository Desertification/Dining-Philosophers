import java.text.MessageFormat;

/**
 * Created by thoma on 12-Apr-17.
 */
public class Philosopher implements Runnable {
    private final int id;
    private static int nextID;

    private enum State {THINKING, HUNGRY, EATING}

    private State state;
    private Philosopher[] philosophers;
    private final int leftNeighbor;
    private final int rightNeighbor;

    public Philosopher(Philosopher[] philosophers) {
        this.philosophers = philosophers;
        state = State.THINKING;
        id = nextID;
        nextID++;
        leftNeighbor = (id + philosophers.length - 1) % philosophers.length;
        rightNeighbor = (id + 1) % philosophers.length;
    }

    @Override
    public void run() {
        while (true) {
            takeForks();
            eat();
            returnForks();
            think();
        }
    }

    private void takeForks() {
        System.out.println(MessageFormat.format("Philosopher {0} is hungry.", id));
        state = State.HUNGRY;
        test();
        if (state != State.EATING) {
            System.out.println(MessageFormat.format("Philosopher {0} is grumpy because he doesn't have the forks to eat.", id));
            try {
                synchronized (this) {
                    this.wait();
                }
                System.out.println(MessageFormat.format("Philosopher {0} hears his neighbor putting down a fork.", id));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(MessageFormat.format("Philosopher {0} grabs forks.", id));
        state = State.EATING;
    }

    private void returnForks() {
        System.out.println(MessageFormat.format("Philosopher {0} returns the forks.", id));
        state = State.THINKING;
        testNeighbors();
    }

    private void testNeighbors() {
        philosophers[leftNeighbor].test();
        philosophers[rightNeighbor].test();
    }

    public boolean isEating() {
        return state == State.EATING;
    }

    public void test() {
        if (!areNeighborsEating() && state == State.HUNGRY) {
            state = State.EATING;
            synchronized (this) {
                this.notify();
            }
        }
    }

    private boolean areNeighborsEating() {
        return philosophers[leftNeighbor].isEating() || philosophers[rightNeighbor].isEating();
    }

    private void think() {
        System.out.println(MessageFormat.format("Philosopher {0} is thinking.", id));
        SleepUtilities.nap();
    }

    private void eat() {
        System.out.println(MessageFormat.format("Philosopher {0} is eating.", id));
        SleepUtilities.nap();
        System.out.println(MessageFormat.format("Philosopher {0} is done eating.", id));
    }
}
