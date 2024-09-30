package string.main;

import string.utils.IterLetter;

public class Main {
    public static void main(String[] args) {
        
    int n = Integer.parseInt(args[0]);
        IterLetter il = new IterLetter(args[1]); // instance of IterLetter from public I.L{} btw
        il.hasNext();
        for (int i = 0; i < n; i++) //the logic while it stops printing at char number 6 while the input is 7 for s.charAt(7) for example, is because of i<n, not i<=n lol you idiot
            il.printNext(); // iterating using the method property
        il.reset();
        il.hasNext();

        int m = Integer.parseInt(args[2]);
        int k = Integer.parseInt(args[3]);
        for (int i = 0; i < m; i++)
            il.printNext(); // same shit then reset
        il.reset();
        for (int i = 0; i < k; i++)
            il.printNext();
    }
}
