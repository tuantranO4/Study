package data.structure;

import java.util.HashMap;

public class MultiSet<E> {
    private HashMap<E, Integer> elemToCount;

    @SafeVarargs
    public MultiSet(E... elems) {
        elemToCount = new HashMap<>();
        for (E elem: elems) {
            add(elem);
        }
    }

    public int add(E elem) {
        int count = elemToCount.getOrDefault(elem, 0);
        elemToCount.put(elem, count + 1);
        return count + 1;
    }

    public int getCount(E elem) {
        return elemToCount.getOrDefault(elem, 0);
    }

    public MultiSet<E> intersect(MultiSet<E> otherMultiSet) {
        MultiSet<E> retVal = new MultiSet<>();

        for (E key: elemToCount.keySet()) {
            if (!otherMultiSet.elemToCount.containsKey(key)) {
                continue;
            }

            int count1 = elemToCount.get(key);
            int count2 = otherMultiSet.elemToCount.get(key);
            retVal.elemToCount.put(key, Math.min(count1, count2));
        }

        return retVal;
    }

    public int size() {
        int retVal = 0;
        for (int count: elemToCount.values()) {
            retVal += count;
        }
        return retVal;
    }
}
