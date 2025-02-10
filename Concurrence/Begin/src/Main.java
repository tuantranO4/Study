public class Main {
    public static void main(String[] args) {
//use .run() and it prints each one, but .start() will utilise all n threads (in this case 5) to print n numbers at once
        for (int i =0 ; i<=4; i++) {
            MultithreadTest mt = new MultithreadTest(i);
            MultiThreadRunnable mtr = new MultiThreadRunnable(i);
            Thread myThread = new Thread(mtr); //pass mtr to implement runnable
            //no passing mtr = using run() interface method = do nothing
            myThread.setDaemon(true);
            myThread.start();
//            try {
//            myThread.join(200); //put the n ms time for a delay, the thread 1 will be paused for n ms before thread 2 begins
//            }catch (InterruptedException e){
//            }
            mt.start();
            System.out.println(myThread.isAlive()); //returns 'true' if the thread is still doing its tasks and 'false' if it has completed
//            try {
//            myThread.join(); //ensuring that a thread has completed its execution before the next part of the program continues
//            }catch (InterruptedException e){
//            }
            //mt.run();
        }
        System.out.println(1/0); //despite the error at thread main, the program still run -> proof of running independently

    }
}
/*
* \the Runnable tells the Thread what to do, and the Thread is the one that actually does it.
* By passing the Runnable (in this case: mtr) to the Thread, you're saying, "Here's the list of things I want you to do."
* When you call start() on the Thread, that's like telling the person, "Okay, start working on these tasks now."
*
* Constructor(s) used: Thread(Runnable target);
 * */

/*
* A daemon thread in Java is a special type of thread that runs in the background and doesn't prevent the program from ending.
* Once all the non-daemon (regular) threads finish running, the program will shut down, and all daemon threads stop abruptly, regardless of what they are doing.
* */