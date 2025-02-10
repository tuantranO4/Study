public class MultithreadTest extends Thread{

    private int threadNum;
    public MultithreadTest(int n){
        this.threadNum=n;
    }

    @Override
    public void run()
    {
        for (int i=3; i<=0; i--){
            System.out.println(i + " at thread index: "+threadNum);
            if (threadNum==2){
                throw new RuntimeException(); //blow up a thread for example
            }
            try {
                Thread.sleep(450); //always surround with try-catch
            } catch (InterruptedException e) {
            }
        }

    }
}
