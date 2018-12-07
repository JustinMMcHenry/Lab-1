package edu.haverford.cs.squirrelfacts;

import java.util.Iterator;

/**
 * TODO: Implement the rest of this iterator
 * Note that we extend from SquirrelIterator so that we do not duplicate the code in that class
 */
class SquirrelDoublyLinkedListIterator extends SquirrelIterator implements BackIterator<Squirrel> {

    public SquirrelDoublyLinkedListIterator(SquirrelLink firstLink, SquirrelList in) {
        super(firstLink,in);
    }

    @Override
    public boolean hasPrev() {
        return mPrev!=null;
    }

    @Override
    public Squirrel next() {
        return super.next();
    }

    @Override
    public Squirrel prev() {
        lastOut = mPrev.getSquirrel();
        mCur = mPrev;
        mPrev = ((SquirrelDoubleLink) mPrev).getPrev();
        return lastOut;
    }

    @Override
    public void remove() {
        if(mCur==null || mCur.getSquirrel()!=lastOut)super.remove();
        else {
            next();
            remove();
        }
    }
}


class SquirrelDoubleLink extends SquirrelLink {


    public SquirrelDoubleLink(Squirrel squirrel, SquirrelLink next) {
        super(squirrel, next);
    }

    public SquirrelLink getPrev(){return mPrev;}
}

public class SquirrelDoublyLinkedList extends SquirrelList {
    SquirrelDoublyLinkedList() {
        super();
    }

    @Override
    public SquirrelList addToFront(Squirrel squirrel) {
        return super.addToFront(squirrel);
    }

    @Override
    protected SquirrelLink genProperLink(Squirrel s, SquirrelLink l) {
        return new SquirrelDoubleLink(s,l);
    }

    @Override
    public Squirrel getItem(int m) {
        return super.getItem(m);
    }

    public Squirrel getFirst() {
        return super.getFirst();
    }

    @Override
    public BackIterator<Squirrel> iterator() {
        return new SquirrelDoublyLinkedListIterator(mFirst,this);
    }

}
