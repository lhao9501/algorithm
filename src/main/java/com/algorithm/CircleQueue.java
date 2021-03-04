package com.algorithm;

/**
 * 用数组实现
 * @param <E>
 */
public class CircleQueue<E> {
    private int size;
    private int front;
    private E[] elements;

    private static final int DEFAULT_CAPACITY  = 10;

    public CircleQueue() {
        // elements = (E[]) new Object[DEFAULT_CAPACITY];
        this(0);
    }

    public CircleQueue(int capacity) {
        int newCapacity = capacity > DEFAULT_CAPACITY ? capacity : DEFAULT_CAPACITY;
        elements = (E[]) new Object[newCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueue(E element) {
        ensureCapacity(size + 1);

        elements[index(size)] = element;
        size++;
    }

    public E deQueue() {
        E frontElement = elements[front];
        elements[front] = null;
        /*front++;  // 注意循环移动*/
        front = index(1);
        size--;
        return frontElement;
    }

    public E front() {
        return elements[front];
    }

    /**
     * 元素索引
     * @param index
     * @return
     */
    private int index(int index) {
        if (size != 0) {
            return (index + front) % elements.length;
        }else {
            return index + front;
        }
    }

    /**
     * 是否扩容
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;

        if (capacity < oldCapacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);

        E[] newElements = (E[]) new Object[newCapacity];
        for(int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }

        elements = newElements;
        // 重新清空 front
        front = 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("CircleQueue size = " + size + ", 容量为 capacity = " + elements.length + ", 元素为 [");
        for (int i = 0; i < elements.length; i++) {
            if (i == 0) {
                str.append(elements[i]);
            }else {
                str.append(", " + elements[i]);
            }
        }
        str.append("]");

        return str.toString();
    }
}
