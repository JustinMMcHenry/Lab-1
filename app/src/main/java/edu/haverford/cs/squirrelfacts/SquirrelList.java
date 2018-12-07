package edu.haverford.cs.squirrelfacts;

import android.database.DataSetObserver;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A single link of a linked list
 */
class SquirrelLink {
    protected Squirrel mSquirrel;
    protected SquirrelLink mNext;
    protected SquirrelLink mPrev;

    public SquirrelLink(Squirrel squirrel, SquirrelLink next) {
        mSquirrel = squirrel;
        mNext = next;
        mPrev = null;
    }

    public void  recon(Squirrel s, SquirrelLink n)
    {
        mSquirrel = s;
        mNext = n;
        mPrev = null;
    }

    public Squirrel getSquirrel() {
        return mSquirrel;
    }
    public SquirrelLink getNext() {
        return mNext;
    }

    public void setNext(SquirrelLink sl) { mNext = sl; }
    public void setPrev(SquirrelLink sl) { mPrev = sl; }
}

class SquirrelIterator implements Iterator<Squirrel> {
    protected SquirrelLink mCur;
    protected SquirrelLink mPrev = null;
    protected SquirrelList mList;
    protected Squirrel lastOut = null;

    public SquirrelIterator(SquirrelLink firstLink, SquirrelList in) {
        mCur = firstLink;
        mList = in;
    }

    public boolean hasNext() {
        return mCur != null;
    }

    public Squirrel next() {
        lastOut = mCur.getSquirrel();
        mPrev = mCur;
        mCur = mCur.getNext();
        return lastOut;
    }

    /**
     * Removes last returned element of the iterator from the underlying collection
     */
    public void remove() {
        if(mPrev.mPrev==null) mPrev.recon(mCur.getSquirrel(), mCur.getNext());
        else mPrev.mPrev.setNext(mCur);
        mList.notifyObservers();
    }
    /*public void remove()
    {
        mList.remove(lastOut);
    }*/
}

public class SquirrelList implements Iterable<Squirrel>, Collection<Squirrel> {
    protected SquirrelLink mFirst;
    protected List<DataSetObserver> mObservers;

    public SquirrelList() {
        mFirst = null;
        mObservers = new ArrayList<DataSetObserver>();
    }


    public void addObserver(DataSetObserver o) {mObservers.add(o);}
    public void removeObserver(DataSetObserver o) {mObservers.remove(o);}

    protected void notifyObservers()
    {
        for(DataSetObserver o: mObservers) {o.onChanged();/*o.notify();*/}
    }

    /**
     * Creates a list of squirrels from an array.
     * @param squirrels
     */
    public SquirrelList(Squirrel[] squirrels) {
        this();
        for(int i = squirrels.length-1; i>=0; i--)
        {
            addToFront(squirrels[i]);
        }
    }

    /**
     * Adds a squirrel to the front of the list.
     * @param squirrel The squirrel to add to the list
     * @return {this}, the updated object after adding the squirrel to the front of the list.
     */
    public SquirrelList addToFront(Squirrel squirrel) {
        squirrel.setId(size());
        SquirrelLink temp = genProperLink(squirrel,mFirst);
        if(mFirst!=null) mFirst.setPrev(temp);
        mFirst = temp;
        notifyObservers();
        return this;
    }

    /**
     * Gives proper version of link for polymorphism
     * @param s
     * @param l
     * @return
     */
    protected SquirrelLink genProperLink(Squirrel s, SquirrelLink l)
    {
        return new SquirrelLink(s,l);
    }

    /**
     * Get the item at the `m`th position in the list.
     * @param m The index of the squirrel to fetch
     * @return The squirrel at that index
     * @throws IndexOutOfBoundsException if `m` > getLength()-1
     */
    public Squirrel getItem(int m) {
        Iterator<Squirrel> i = iterator();
        Squirrel s = null;
        while (m >= 0 && i.hasNext()) {
            s = i.next();
            m--;
        }
        if (m >= 0) {
            // Ran out of list
            throw new IndexOutOfBoundsException();
        } else {
            return s;
        }
    }

    /**
     * Get the first Squirrel in the list
     * @return first squirrel when list not empty
     * @throws NullPointerException when list is empty
     */
    public Squirrel getFirst() {
        if (mFirst != null) {
            return mFirst.getSquirrel();
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Returns the size of the list.
     * @return
     */
    @Override
    public int size() {
        int i = 0;
        for (SquirrelLink c = mFirst; c != null; c = c.getNext()) {
            i++;
        }
        return i;
    }

    /**
     * @return Returns true if list has zero elements, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return mFirst==null;
    }

    /**
     *
     * @param o
     * @return Returns true if list contains o, false otherwise.
     */
    @Override
    public boolean contains(Object o) {
        Iterator<Squirrel> it = iterator();

        while(it.hasNext())
            if(it.next()==o) return true;

        return false;
    }

    @NonNull
    @Override
    public Iterator<Squirrel> iterator() {
        return new SquirrelIterator(mFirst,this);
    }

    /**
     * @param ts
     * @param <T>
     * @return Returns list as array.
     */
    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {


        if(size()>ts.length) ts = (T[]) new Squirrel[size()];

        Iterator<Squirrel> it = iterator();
        int i=0;
        for(; it.hasNext(); i++)
        {
            ts[i]=(T) it.next();
        }
        if(size()<ts.length) ts[i] = null;

        return ts;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        Squirrel[] arr = new Squirrel[size()];
        int j = 0;
        for (Iterator<Squirrel> i = iterator(); i.hasNext(); j++) {
            arr[j] = i.next();
        }
        return arr;
    }

    @Override
    public boolean add(Squirrel squirrel) {
        addToFront(squirrel);
        return true;
    }

    /**
     * Removes first instance o from list if o is in the list.
     * @param o
     * @return Returns true if removal is successful false otherwise.
     */
    @Override
    public boolean remove(Object o) {

        SquirrelLink ref = mFirst;

        if(mFirst!= null && mFirst.getSquirrel()==o) {mFirst = mFirst.mNext; mFirst.setPrev(null); return true;}
        while(ref!=null)
        {
            if(ref.getSquirrel()==o) {ref.mPrev.setNext(ref.mNext); ref.mNext.setPrev(ref.mPrev); notifyObservers(); return true;}
            ref = ref.mNext;
        }
        return false;
    }

    /**
     * @param collection The collection to be added to the Squirrel List.
     * @return Squirrel List containing original elements plus those in collection.
     */
    @Override
    public boolean addAll(@NonNull Collection<? extends Squirrel> collection) {
        int ogSize = size();
        Iterator<Squirrel> it = (Iterator<Squirrel>) collection.iterator();

        while(it.hasNext()){
            add(it.next());
        }

        return  ogSize!=size();
    }

    /**
     *Clears all elements from list.
     */
    @Override
    public void clear() {
        mFirst = null;
    }

    public ArrayList<Squirrel> toArrayList() {
        ArrayList<Squirrel> l = new ArrayList<Squirrel>();
        l.addAll(this);
        return l;
    }

    // No need to implement the following three methods

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    // No need to implement the above three methods
}
