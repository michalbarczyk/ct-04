import java.util.LinkedList;
import java.util.List;

public class Buffer {
    List<Integer> bufferContent;
    int maxSize;
    int currSize;
    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        this.currSize = 0;
        bufferContent = new LinkedList<>();
    }
    public void put(int item) {
        currSize++;
        bufferContent.add(item);
        System.out.println("[P] Thread " + Thread.currentThread().getId() + " produced new item = " + item);
    }
    public void get() {
        currSize--;
        int rmvd = bufferContent.remove(0);
        System.out.println("[C] Thread " + Thread.currentThread().getId() + " consumed item " + rmvd);
    }


}
