import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        final int M = 3;
        final int BUFFER_MAX_SIZE = 2 * M;
        Buffer buffer = new Buffer(BUFFER_MAX_SIZE);
        List<Thread> threads = new ArrayList<>();

        Semaphore semProduce = new Semaphore(BUFFER_MAX_SIZE);
        Semaphore semConsume = new Semaphore(0);
        Semaphore semAccess = new Semaphore(1);

        for (int i = 0 ; i < 3; i++)
            threads.add(new Consumer(buffer, semProduce, semConsume, semAccess, M));
        for (int i = 0 ; i < 3; i++)
            threads.add(new Producer(buffer, semProduce, semConsume, semAccess, M));
        for (Thread thread : threads)
            thread.start();
        try {
            for (Thread thread : threads)
                thread.join();
        } catch (InterruptedException ex) {}
    }
}
