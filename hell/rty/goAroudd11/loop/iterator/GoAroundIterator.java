package loop.iterator;

import java.util.Iterator;
import java.util.List;

public final class GoAroundIterator<T> implements Iterator<T> {
    private int idx = 0;
    private int round = 0;

    private int roundCount;
    private List<T> elements; //T is a placeholder for a type that will be specified later
    
    public GoAroundIterator(int roundCount, List<T> elements) { //new GoAroundIterator<>(3, List.of(1, 2, 3));
        this.roundCount = roundCount;
        this.elements = elements;
    }

    @Override
    public boolean hasNext() {
        return round < roundCount;
    }

    @Override
    public T next() {
        T retval = elements.get(idx);         // Get the current element
        idx = (idx + 1) % elements.size();   // Move to the next index (wraps around using `%`) : (2 + 1) % 3 = 0, wrapping back to "A"
        if (idx == 0) ++round;               // Increment round when a full loop is completed

        return retval;                       // Return the current element
    }
}
