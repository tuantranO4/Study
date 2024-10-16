package array.util;

public class ArrayUtil {
    public static int max(int[] arr){
        if (arr.length==0){
        return 0;
    }
        else{
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i]>max){
                    max = arr[i];
                }
            }
            return max;
        }
    }
    
    public static int max2(int[] arr){
        return 0;
    }
    public static int max3(int[] arr){
        return 0;
    }
    public static int max4(int[] arr){
        return 0;
    }
    
}
