package editor;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LinesIterator implements Iterator<String> {

    private List<String> collection;
    private int current;
    private int last;

    public LinesIterator(List<String> collection){
        this.collection = collection;
        this.current = 0;
        this.last = collection.size();
    }

    public LinesIterator(List<String> collection, int min, int max){
        this.collection = collection;
        this.current = min;
        this.last = max;
    }

    @Override
    public boolean hasNext() {
        return current < last;
    }

    @Override
    public String next() {
        if(hasNext())
            return collection.get(current++);
        throw new NoSuchElementException();
    }
}
