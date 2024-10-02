package famous.sequence;

public class Fibonacci {
    public static int fib(int n) {
        int n1 = 0, n2 = 1;
        for (int i = 2; i <= n; ++i) {
            int n3 = n1 + n2;
            n1 = n2;
            n2 = n3;
        }
        return n2;
    }
}
