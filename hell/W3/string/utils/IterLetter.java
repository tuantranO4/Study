package string.utils;
import java.util.*;
public class IterLetter{
    protected String itering;
    private int idx=0;
    public IterLetter(String s){
        if (s == null){
            throw new IllegalArgumentException("can't be null struct.");
        }    
        else{this.itering = s;}
    }

    public void printNext(){
        if(idx<itering.length()){
            System.out.println(itering.charAt(idx++));
        }else{
            System.out.println(" ");
        }
    }

    public boolean hasNext(){
        return idx < itering.length();
    }

    public void reset(){
        idx=0;
    }

}