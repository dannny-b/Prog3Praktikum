import javax.lang.model.type.NullType;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

import static java.util.Collections.copy;

public class CircularBuffer<E> implements Queue<E>, Serializable, Cloneable {
    private ArrayList<E> elements;
    private int writePos;
    private int readPos;
    private int size;
    private int capacity;
    private final boolean fixedCapacity;
    private final boolean discarding;

    public CircularBuffer(int capacity, boolean fixedCapacity, boolean discarding) {
        this.elements = new ArrayList<>(this.capacity = capacity);
        for (int i = 0; i < capacity; i++) {
            elements.add(null);
        }
        this.fixedCapacity = fixedCapacity;
        this.discarding = discarding;
    }

    @Override
    public Object clone() {
        try {
            CircularBuffer<E> c = (CircularBuffer<E>) super.clone();
            c.elements = new ArrayList<>(c.capacity);
            copy(c.elements, elements);
            return c;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        return (indexOf(o) >= 0);
    }

    private int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements.get(i) == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (elements.get(i) == o) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> itr = new Iterator<E>() {
            private int index = readPos;

            @Override
            public boolean hasNext() {
                if (index != writePos) return true;
                return false;
            }

            @Override
            public E next() {
                E el = elements.get(readPos + 1);
                index = (index + 1) % capacity;
                return el;
            }
        };
        return itr;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int tmp = readPos;
        for (int i = 0; i < size; i++) {
            array[i] = elements.get(tmp);
            tmp = (tmp + 1) % capacity;
        }
        return array;
    }

    @Override
    public <E> E[] toArray(E[] a) {
        if (a.length < size) {
            a = (E[]) new Object[capacity];
        }
        int tmp = readPos;
        for (int i = 0; i < size; i++) {

            a[i] = (E) elements.get(tmp);
            tmp = (tmp + 1) % capacity;
        }

        if (a.length > capacity) {
            a[writePos + 1] = null;
        }
        return a;
    }

    private boolean writeInto(int pos, Object o) {
        if ((pos >= 0) && (pos < capacity)) {
            elements.set(pos, (E) o);
            writePos = (writePos + 1) % capacity;
            size++;
            return true;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean add(E e) {
        if (!isFull()) {
            return writeInto(writePos, e);
        } else {
            if (discarding) {
                return writeInto(writePos, e);
            }
            if (!fixedCapacity) {
                grow();
                return writeInto(writePos, e);
            }
        }
        throw new IllegalArgumentException();
    }

    private void grow() {
        ArrayList<E> tmp = (ArrayList<E>) elements.clone();
        elements = new ArrayList<>(capacity++);
        for (int i = 0; i < capacity; i++) {
            elements.add(null);
        }
        copy(elements, tmp);
        writePos = capacity - 1;
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty() || elements.get(readPos) != o || o == null) {
            return false;
        }
        try {
            remove();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.size() != this.size) return false;
        Iterator<?> iterator = c.iterator();
        while (iterator.hasNext()) {
            if (!this.contains(iterator)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c != null) {
            c.forEach(this::add);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<?> iterator = c.iterator();
        while (iterator.hasNext()) {
            this.remove(iterator);
        }
        return true;

    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (int i = readPos; i < size; i++) {
            if (!c.contains(elements.get(i))) {
                poll();
            }
        }
        return true;
    }

    @Override
    public void clear() {
        elements = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            elements.add(null);
        }
        size = writePos = readPos = 0;
    }

    @Override
    public boolean offer(E e) {
        try {
            return add(e);
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public E remove() {
        if (!isEmpty()) {
            E e = elements.get(readPos);
            readPos = (readPos + 1) % capacity;
            this.size--;
            return e;
        }
        throw new NoSuchElementException();
    }

    @Override
    public E poll() {
        try {
            return remove();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public E element() {
        if (!isEmpty()) {
            return elements.get(readPos);
        }
        throw new NoSuchElementException();
    }

    @Override
    public E peek() {
        try {
            return element();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isFull() {
        return (size == capacity);
    }

    public void showBuffer() {
        for (int i = 0; i < capacity; i++) {
            System.out.println(i + " : " + elements.get(i));
        }
        System.out.println(" write: " + writePos + " , read: " + readPos + " ,Size: " + this.size());
    }
}
