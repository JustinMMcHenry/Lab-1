package edu.haverford.cs.squirrelfacts;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

import edu.haverford.cs.squirrelfacts.Squirrel;
import edu.haverford.cs.squirrelfacts.SquirrelList;

public class SquirrelListTest {
    protected Squirrel mTestSquirrel;
    protected Squirrel mTestSquirrel2;
    protected Squirrel mTestSquirrel3;
    protected SquirrelList mEmptySquirrelList;

    public SquirrelListTest() {
        mTestSquirrel = new Squirrel("a","b","c");
        mTestSquirrel2 = new Squirrel ("e","f","g");
        mTestSquirrel3 = new Squirrel ("i","j","k");
        mEmptySquirrelList = new SquirrelList();
    }

    @Test
    public void list_startsLengthZero() {
        assertEquals(0, mEmptySquirrelList.size());
    }

    @Test
    public void list_addOneLengthOne() {
        assertEquals(1, mEmptySquirrelList.addToFront(mTestSquirrel).size());
    }

    @Test
    public void list_getBackFirst() {
        assertEquals(mTestSquirrel, mEmptySquirrelList.addToFront(mTestSquirrel).getFirst());
    }

    @Test
    public void list_notIsEmpty() {
        assertEquals(false, mEmptySquirrelList.addToFront(mTestSquirrel).isEmpty());
    }

    @Test
    public void list_isEmpty() {
        assertEquals(true, mEmptySquirrelList.isEmpty());
    }

    @Test
    public void list_contains() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        assertEquals(true, sl.contains(mTestSquirrel2));
    }

    @Test
    public void list_arr1()
    {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);

        Squirrel[] comp = {mTestSquirrel, mTestSquirrel2, mTestSquirrel3};

        Squirrel[] gen =(Squirrel[]) sl.toArray();
        Squirrel[] gen1 = sl.toArray(new Squirrel[0]);
        Squirrel[] gen2 = sl.toArray(new Squirrel[4]);

        for(int i = 0; i < comp.length; i++)
        {
            assertEquals(comp[i],gen[i]);
            assertEquals(comp[i],gen1[i]);
            assertEquals(comp[i],gen2[i]);
        }
        assertEquals(null, gen2[3]);
    }

    // Test that if you insert an item in the second position, `getItem` will return it properly
    @Test
    public void list_getBackInsertedPosition() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel2);
        assertEquals(mTestSquirrel,sl.getItem(2));
    }

    @Test
    public void list_removeLink() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        Iterator<Squirrel> i = sl.iterator();
        i.next();
        i.remove();
        assertEquals(mTestSquirrel3
                , sl.getItem(1));
    }

    @Test
    public void list_remove() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        sl.remove(mTestSquirrel2);
        sl.remove(mTestSquirrel);
        assertEquals(mTestSquirrel3
                , sl.getFirst());
    }

    @Test
    public void list_containsSquirrel() {
        mEmptySquirrelList.add(mTestSquirrel);
        assertEquals(true, mEmptySquirrelList.contains(mTestSquirrel));
    }

    @Test
    public void list_clearIsEmpty() {
        mEmptySquirrelList.add(mTestSquirrel);
        mEmptySquirrelList.clear();
        assertEquals(true, mEmptySquirrelList.isEmpty());
    }

    @Test
    public void list_addAll() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);

        assertEquals(false, sl.addAll(new SquirrelList()));
        assertEquals(true, sl.addAll(new SquirrelList().addToFront(mTestSquirrel)));
    }

    /**
     * Part 2: Failing test for iteration removal here..
     */
    @Test
    public void list_breakFaultyItRemove() {
        SquirrelList sl = mEmptySquirrelList;
        sl.addToFront(mTestSquirrel3);
        sl.addToFront(mTestSquirrel2);
        sl.addToFront(mTestSquirrel);
        Iterator<Squirrel> i = sl.iterator();
        i.next();
        i.remove();
        assertEquals(mTestSquirrel2
                , sl.getItem(0));
    }
}
