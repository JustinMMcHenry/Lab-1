package edu.haverford.cs.squirrelfacts;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class SquirrelDoublyLinkedListTest extends SquirrelListTest {
    public SquirrelDoublyLinkedListTest() {
        super();
        mEmptySquirrelList = new SquirrelDoublyLinkedList();
        mTestSquirrel = new Squirrel("a","b","c");
        mTestSquirrel2 = new Squirrel ("e","f","g");
        mTestSquirrel3 = new Squirrel ("i","j","k");

    }

    /**
     * This test should test a basic property: that you can go forward and back in the list and
     * get to the same element.
     */
    @Test
    public void list_forwardBack() {
        SquirrelDoublyLinkedList sl = (SquirrelDoublyLinkedList) mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        BackIterator<Squirrel> i = sl.iterator();

        Squirrel first = i.next();
        Squirrel firstAgain = null;
        while(i.hasNext()) i.next();
        while(i.hasPrev()) firstAgain = i.prev();
        assertEquals(first,firstAgain);
    }

    @Test
    public void list_nextAndPrev() {
        SquirrelDoublyLinkedList sl = (SquirrelDoublyLinkedList) mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        BackIterator<Squirrel> i = sl.iterator();

        boolean check = i.next()== i.prev();

        assertEquals(true, check);
    }

    @Test
    public void list_moreWithRemove() {
        SquirrelDoublyLinkedList sl = (SquirrelDoublyLinkedList) mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        BackIterator<Squirrel> i = sl.iterator();

        i.next();
        i.prev();
        i.remove();
        i.next();
        i.next();
        i.prev();
        i.remove();

        assertEquals(mTestSquirrel2
                , sl.getItem(0));
    }

}
