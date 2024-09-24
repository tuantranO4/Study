import java.io.Console;

public class Half {
    public static void main(String[] args) {
        if(args.length<2){
            System.console().printf("retard where number?");
            System.exit(1);
        }else{
        double a = Integer.parseInt(args[0]);
        double b = Integer.parseInt(args[1]);
        for(double i=a; i<=b;i++){
            System.out.println(i/2);
        }
        }
    }
}
