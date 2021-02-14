package com.algorithm;

import java.util.Comparator;

/**
 * 优先级队列
 * @param <E>
 */
public class PriorityQueue<E> {
    private BinaryHeap<E> binaryHeap;  // 二叉堆实现

    public PriorityQueue(Comparator<E> comparator) {
        binaryHeap = new BinaryHeap<>(comparator);
    }

    public int size() {
        return binaryHeap.size();
    }

    public boolean isEmpty() {
        return binaryHeap.isEmpty();
    }

    public void clear() {
        binaryHeap.clear();
    }

    public void enQueue(E element) {
        binaryHeap.add(element);
    }

    public E deQueue() {
        return binaryHeap.remove();
    }

    public E front() {
        return binaryHeap.get();
    }
}
