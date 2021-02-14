package com.algorithm;

/**
 * 双端队列  头尾 能进能出
 */
public class Deque<E> {
    // 使用组合  避免继承导致 双向链表的一些方法被队列使用
    private LinkedList<E> list = new LinkedList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueueFront(E element) {
        list.add(0, element);
    }

    public void enQueue(E element) {
        list.add(element);
    }

    public E deQueueRear() {
        return list.remove(list.size() - 1);
    }

    public E deQueue() {
        return list.remove(0);
    }

    public E front() {
        return list.get(0);
    }

    public E rear() {
        return list.get(list.size() - 1);
    }
}
