import java.util.Random;
import java.util.RandomAccess;

//public class MultiThreadRunnable extends RandomAssClasses implements Runnable, RandomAccessInterface { } despite the clunky declare, it has more flexibility

public class MultiThreadRunnable implements Runnable{
    private int threadNum;
    public MultiThreadRunnable(int n){
        this.threadNum=n;
    }

    @Override
    public void run()
    {
        for (int i=0; i<=3; i++){
            System.out.println(i + " at thread index: "+threadNum);
            try {
                Thread.sleep(450); //always surround with try-catch
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Proper handling of InterruptedException.
            }
        }

    }
}
