package com.company;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyCollection<E> implements Collection<E> {

    private int size;

    private Object[] elementData = new Object[10];

    @Override
    public boolean add(E e) {
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, (int) (size * 1.5f));
        }
        elementData[size++] = e;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E ob : c) {
            add(ob);
        }
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator<>();
    }

    @Override
    public boolean contains(Object o) {
        boolean b;
        for (int i = 0; i < size; i++) {
            b = elementData[i].equals(o);
            if (b) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object ob : c) {
            if (!contains(ob)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {

        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int sizeA = a.length;
        int result = Math.max(sizeA, size);
        T[] newA = Arrays.copyOf(a, result);
        for (int i = 0; i < size; i++) {
            newA[i] = (T) elementData[i];
        }
        return newA;
    }

    @Override
    public boolean remove(Object o) {
        boolean b = false;
        for (int i = 0; i < size; i++) {
            b = elementData[i].equals(o);
            if (b) {
                for (int j = i; j <= size; j++) {
                    if (j == size) {
                        elementData[j] = null;
                    }
                    elementData[j] = elementData[j + 1];
                }
                size = size - 1;
                break;

            }
        }
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean boo;
        boolean isRemove = false;
        for (Object ob : c) {
            boo = remove(ob);
            if (boo) {
                isRemove = true;
            }

        }
        return isRemove;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean b = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elementData[i])) {
                remove(elementData[i]);
                b = true;
            }
        }
        return b;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    private class MyIterator<T> implements Iterator<T> {

        int cursor = 0;
        boolean isRemoveWasCalled;

        @Override
        public boolean hasNext() {

            return cursor < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            isRemoveWasCalled = false;
            return (T) elementData[cursor++];
        }

        @Override
        public void remove() {
            if (isRemoveWasCalled) {
                throw new UnsupportedOperationException("remove");
            } else {
                for (int i = cursor - 1; i <= size; i++) {
                    if (i == size) {
                        elementData[i] = null;
                    }
                    elementData[i] = elementData[i + 1];
                }
                size = size - 1;
                isRemoveWasCalled = true;


            }
        }
    }
}
