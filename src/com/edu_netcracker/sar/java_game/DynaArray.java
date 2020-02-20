

package com.edu_netcracker.sar.java_game;

import java.util.Arrays;

@SuppressWarnings("unchecked")
public class DynaArray<T> extends DataSet<T> {

    private T[] array;

    public DynaArray() {
        super();
        array = (T[]) new Object[10];
    }

    public DynaArray(T[] array) {
        super();
        this.array = array;
        this.size = array.length;
    }

    
    public void add(T element) {
        if (size == array.length) {
            T[] temp = array;
            array = (T[]) new Object[temp.length * 2];
            for (int i = 0; i < temp.length; i++) {
                array[i] = temp[i];
            }
        }
        array[size++] = element;
    }

    
    public T remove(int index) {
        if (index >= 0 && index < size) {
            T value = array[index];
            for (int i = index; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            array[--size] = null;
            return value;
        }
        throw new IndexOutOfBoundsException("Index should be between 0 and " + (size() - 1) + ". Index to remove: " + index);
    }

    
    public T get(int index) {
        if (index >= 0 && index < size()) {
            return array[index];
        } else {
            throw new IndexOutOfBoundsException("Index should be between 0 and " + (size() - 1) + ". Index to get: " + index);
        }
    }

    
    public void clear() {
        super.clear();
        array = (T[]) new Object[10];
    }

    
    public T[] toArray() {
        return Arrays.copyOf(array, size);
    }
}
