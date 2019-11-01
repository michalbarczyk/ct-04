import java.util.Random;
import java.util.concurrent.Semaphore;

class Consumer extends Thread {
    private Buffer buffer;
    private Semaphore semProduce;
    private Semaphore semConsume;
    private Semaphore semAccess;
    private int M;
    public Consumer(Buffer buffer, Semaphore semProduce, Semaphore semConsume, Semaphore semAccess, int M) {
        this.buffer = buffer;
        this.semProduce = semProduce;
        this.semConsume = semConsume;
        this.semAccess = semAccess;
        this.M = M;
    }
    public void run() {

        int turns = new Random().nextInt(M) + 1;

        try {
            semConsume.acquire(turns);
            semAccess.acquire();
            for (int t = 0; t < turns; t++) {
                buffer.get();
            }

            System.out.println("{C}    " + turns + " items consumed by Thread " + Thread.currentThread().getId());
            semAccess.release();
            semProduce.release(turns);
        } catch (InterruptedException ex) {}


    }
}