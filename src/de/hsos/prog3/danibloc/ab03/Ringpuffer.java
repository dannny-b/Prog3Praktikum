package de.hsos.prog3.danibloc.ab03;

import java.io.Serializable;
import java.util.*;

public class Ringpuffer<T> implements Queue<T>, Serializable, Cloneable {
    private int writePos = -1;
    private int readPos = 0;
    private int size = 0;
    private int capacity;

    private int extendCapacity = 5;
    private boolean fixedCapacity;
    private boolean discarding;
    ArrayList<T> elements;

    public Ringpuffer(int capacity, boolean fixedCapacity, boolean discarding) {
        this.capacity = capacity;
        this.fixedCapacity = fixedCapacity;
        this.discarding = discarding;

        elements = new ArrayList<>(this.capacity);
        initialize(elements);
    }

    private void initialize(ArrayList<T> elements) {
        for(int i=0;i<capacity;i++){
            elements.add(null);
        }
    }


    @Override
    public int size() {
        return (this.writePos - this.readPos) + 1;
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
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] arrayElements = new Object[capacity];
        if (!isEmpty()) {
            for (int i = 0; i < capacity; i++) {
                arrayElements[i] = elements.get(i);
            }
            return arrayElements;
        }
        return null;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if(!isEmpty() && contains(o)){
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean offer(T element) {
        if (!isFull()) {
            if(writePos==-1){
                writePos++;
            }
            elements.set((writePos % capacity), element);
            writePos++;
            size++;
            return true;
        }
        System.out.println("Puffer voll");
        // TODO: else: no empty slot ---> Overwrite, expand buffer!
        if (!fixedCapacity) {
            for(int i = 0; i < extendCapacity; i++){
                elements.add(null);
                capacity++;
            }
            return offer(element);
        }
        if (discarding) {
            ++writePos;
            return offer(element);
        }
        return false;
    }

    @Override
    public T remove() {
        if (!isEmpty()) {
            T nextValue = elements.get(readPos % capacity);
            readPos++;
            size--;
            return nextValue;
        }
        throw new NoSuchElementException();
    }

    @Override
    public T poll() {
        try {
            return remove();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public T element() {
        if (!isEmpty()) {
            return elements.get(readPos);
        }

        throw new NoSuchElementException();

    }

    @Override
    public T peek() {
        try {
            return element();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isFull() {
        return (size == this.capacity);
    }

    public void run() {
        if (!isEmpty()) {
            for (int i = 0; i < capacity; i++) {
                System.out.println(i + " : " + elements.get(i));
            }
        }else{
            System.out.println("Puffer leer!");
        }
        System.out.println("read: " + readPos%capacity + " , write: " + writePos%capacity);

    }
    public void runArray(){
        for(T o : elements){
            if(o==null){
                System.out.print("X ");
            }else{
                System.out.print(o.toString() + " ");
            }
        }
    }
}