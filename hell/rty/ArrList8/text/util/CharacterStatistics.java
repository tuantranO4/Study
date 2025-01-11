package text.util;

import java.util.HashMap;
import java.util.Map.Entry;

public class CharacterStatistics {
    private HashMap<Character, Integer> charToCount; // HashMap<Key, Val>: entry.getKey() + ": " + entry.getValue() 

    public CharacterStatistics(String text) { //hello -> h(1) e(1) l(2) o(1)
        charToCount = new HashMap<>();
        for (char c : text.toCharArray()) { //converts this string to a new character array (text: input param)
            int count = 1 + charToCount.getOrDefault(c, 0); //if this char c exist, get the value, else 0.
            charToCount.put(c, count);
        }
    }

    public int getCount(char c) {
        return charToCount.getOrDefault(c, 0); 
    }

    @Override
    public String toString() {
        StringBuilder retval = new StringBuilder();
        for (Entry<Character, Integer> entry : charToCount.entrySet()) { //return all pairs of key and value and loop with (charToCount.entrySet())
            retval.append("%s(%d) ".formatted(entry.getKey(), entry.getValue()));
        }
        return retval.toString();
    }
}
