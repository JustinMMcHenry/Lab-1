package edu.haverford.cs.squirrelfacts;

import java.util.Iterator;

public interface BackIterator<T> extends Iterator<T> {
    /**
     * @return {true} if the iterator has previous elements. (I.e., returns {true} if {prev()} would
     * return an element rather than throw an exception.)
     */
    public boolean hasPrev();

    /**
     * Move the iterator one to the left and return the next element.
     * @return The next element of the collection
     * @throws java.util.NoSuchElementException if no more elements are available
     */
    public T prev();
}
