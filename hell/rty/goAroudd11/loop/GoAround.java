package loop;

import java.util.ArrayList;
import java.util.List;
import loop.iterator.GoAroundIterator;

public class GoAround<T> implements Iterable<T> {
    private int roundCount;
    private List<T> elements;

    public GoAround(int roundCount, @SuppressWarnings("unchecked") T... elements) {
        this.roundCount = roundCount;
        this.elements = new ArrayList<>(elements.length); //new ArrayList with an initial capacity equal to the length of the provided array (pre allocate)
        for (T element : elements) { //manual copy of the array into the list
            this.elements.add(element);
        }
    }

    @Override
    public GoAroundIterator<T> iterator() {
        return new GoAroundIterator<>(roundCount, elements);
    }
}
