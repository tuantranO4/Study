public class Greet2 {
    public static void main(String[] args) {
    if(args.length>0){
    System.console().printf("wassup gang, %s.\n", args[0]);
    }else{
        System.console().printf("it's empty holy shit.\n");
    }
}
}
