import java.io.Console;

public class haha {
    public static void main(String[] args) {
        if(args.length<2){
            System.err.printf("retard where number?");
            System.exit(1);
        }else{
        double a = Integer.parseInt(args[0]);
        double b = Integer.parseInt(args[1]);
        if (b==0){
            System.err.printf("fucking retard.");
        }else{
        System.out.println(a+b);
        System.out.println(a-b);
        System.out.println(a*b);
        System.out.println(a/b);
        }}
    }
}
