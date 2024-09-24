public class Factorial {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int res=1;
        if(args.length<1){
            System.err.printf("retard where number?");
            System.exit(1);
        }else{
        for(int i =1; i<=a;i++){
            res*=i;
        }
        System.out.println(res);
    }
    }
}
