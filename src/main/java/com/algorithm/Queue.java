package com.algorithm;

/**
 * FIFO   尾进头出  双头操作  用双向链表实现较好
 * @param <E>
 */
public class Queue<E> {
    // 使用组合  避免继承导致 双向链表的一些方法被队列使用
    private LinkedList<E> list = new LinkedList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enQueue(E element) {
        list.add(element);
    }

    public E deQueue() {
        return list.remove(0);
    }

    public E front() {
        return list.get(0);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
