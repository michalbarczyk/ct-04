import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class Producer extends Thread {
    private Buffer buffer;
    private Semaphore semProduce;
    private Semaphore semConsume;
    private Semaphore semAccess;
    private int M;

    private static int itemIndex = 0;

    public Producer(Buffer buffer, Semaphore semProduce, Semaphore semConsume, Semaphore semAccess, int M) {
        this.buffer = buffer;
        this.semProduce = semProduce;
        this.semConsume = semConsume;
        this.semAccess = semAccess;
        this.M = M;
    }
    public void run() {

        int turns = new Random().nextInt(M) + 1;

        try {
            semProduce.acquire(turns);
            semAccess.acquire();

            for (int t = 0; t < turns; t++) {
                buffer.put(itemIndex++);
            }
            System.out.println("{P}    " + turns + " items produced by Thread " + Thread.currentThread().getId());
            semAccess.release();
            semConsume.release(turns);
        } catch (InterruptedException ex) {}
    }
}
