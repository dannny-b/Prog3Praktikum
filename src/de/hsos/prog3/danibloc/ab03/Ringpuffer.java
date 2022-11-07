package de.hsos.prog3.danibloc.ab03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class Ringpuffer<E> implements Queue, Serializable, Cloneable {
    private int size = 0;
    private int writePos = 0;
    private int readPos = 0;
    private int capacity;
    private boolean fixedCapacity;
    private boolean discarding;
    private boolean isFull = false;
    private ArrayList<E> elements;

    public Ringpuffer(int capacity, boolean fixedCapacity, boolean discarding) {
        this.capacity = (capacity < 1) ? 5 : capacity;
        this.readPos = 0;
        this.writePos = -1;
        this.fixedCapacity = fixedCapacity;
        this.discarding = discarding;

        this.elements = new ArrayList<E>(capacity);
        this.isFull = isFull();
    }

    @Override
    public Ringpuffer<E> clone() {
        try {
            Ringpuffer clone = (Ringpuffer) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (this.writePos < this.readPos);
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        if (isFull) {
            // TODO: überschreiben
            if (discarding) {

            } else {
                if (!fixedCapacity) {
                    // TODO: kapazität erweitern
                } else {
                    throw new IndexOutOfBoundsException("Zu viele Elemente");
                    // TODO: return false;
                }
            }
        }
        this.elements.set(writePos, (E) o);
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public boolean offer(Object o) {
        if (!isFull) {
            int nextWritePos = this.writePos + 1;
            this.elements.set(nextWritePos % capacity, (E) o);
            writePos++;
            return true;
        }
        return false;
    }

    @Override
    public Object remove() {
        return null;
    }

    @Override
    public Object poll() {
        if (!isEmpty()) {
            E nextValue = this.elements.get(this.readPos % this.capacity);
            this.readPos++;
            return nextValue;
        }
        return null;
    }

    @Override
    public Object element() {
        return null;
    }

    @Override
    public Object peek() {
        return null;
    }

    private int updateSize() {
        return (this.writePos - this.readPos) + 1;
    }

    private boolean isFull() {
        return ((this.writePos - this.readPos) + 1) == this.capacity;
    }

}
