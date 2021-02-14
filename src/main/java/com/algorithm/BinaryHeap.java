package com.algorithm;

import java.util.Comparator;

/**
 * 二叉堆 用数组实现(利用完全二叉树的性质)
 * 默认是大顶堆  小顶堆由Comparator实现
 * @param <E>
 */
public class BinaryHeap<E> implements Heap<E> {
    private int size;
    private E[] elements;
    private Comparator<E> comparator;

    // 默认 容量
    private static final int CAPACITY_DEFAULT = 10;


    public BinaryHeap() {
        this(null, null);
    }

    public BinaryHeap(E[] elements) {
        this(elements, null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public BinaryHeap(E[] elements, Comparator<E> comparator) {
        this.comparator = comparator;

        if (elements == null || elements.length == 0) {
            this.elements = (E[]) new Object[CAPACITY_DEFAULT];
        }else {
            size = elements.length;

            // 这里进行深拷贝  外界的数组不影响这里的数组
            int capacity = Math.max(elements.length, CAPACITY_DEFAULT);
            this.elements = (E[]) new Object[capacity];

            for (int i = 0; i < elements.length; i++) {
                this.elements[i] = elements[i];
            }

            heapify();
//            heapify2();
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        elementCheckNotNull(element);
        ensureCapacity(size + 1);

        elements[size] = element;
        size++;

        siftUp(size - 1);
    }

    @Override
    public E get() {
        return elements[0];
    }

    @Override
    public E remove() {
        emptyCheck();
        int lastIndex = --size;
        E oldElement = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;  // 把最后一位置空

        siftDown(0);
        return oldElement;
    }

    @Override
    public E replace(E element) {
        elementCheckNotNull(element);
        E root = null;

        if (size == 0) {  // 考虑空数组的情况
            elements[0] = element;
            size++;
        } else {
            root = elements[0];
            elements[0] = element;
            siftDown(0);
        }

        return root;
    }


    /**
     * 批量建堆   自下而上的下滤  本质是删除
     */
    private void heapify() {
        // 从非叶子节点开始
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 批量建堆   自上而下的上滤   本质是添加
     */
    private void heapify2() {
        // 从第二个节点(非根节点)开始
        for (int i = 1; i < size; i++) {
            siftUp(i);
        }
    }

    /**
     * 下滤
     * @param index
     */
    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;  // 非叶子节点个数就是第一个叶子节点位置

        // 左右子节点中较大的那个节点如果比父节点大 则交换  所以这里仅仅考虑有子节点的节点
        // index指向的节点只有两种情况：
        // 1、同时又左右子节点  2、仅有左节点
        while (index < half) {
            int childIndex = (index << 1) + 1;
            E leftChild = elements[childIndex];

            int rightIndex = childIndex + 1;
            // rightIndex < size 比较左右节点时 保证要有右节点
            if (rightIndex < size && compare(leftChild, elements[rightIndex]) < 0) {
                childIndex = rightIndex;
            }

            if (compare(elements[childIndex], element) > 0) {
                elements[index] = elements[childIndex];

                index = childIndex;
            }else {
                break;
            }
        }

        elements[index] = element;
    }

    /**
     * 上滤
     * @param index
     */
    private void siftUp(int index) {
        // 先备份节点值
        E element = elements[index];
        while (index > 0) {
            int pIndex = (index - 1) >> 1;

            // 如果比父节点大  把父节点拿下来(其实就是交换)  如果比父节点小 退出循环
            if (compare(element, elements[pIndex]) > 0) {
                elements[index] = elements[pIndex];

                index = pIndex;
            }else {
                break;
            }
        }

        elements[index] = element;
    }

    private void elementCheckNotNull(E element) {
        if (null == element)
            throw new IllegalArgumentException("element must be not null");
    }


    private void emptyCheck() {
        if (elements.length == 0) {
            throw new IllegalArgumentException("element must be not null");
        }
    }


    /**
     * 扩容
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;

        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    /**
     * 元素比较    二叉堆中的元素必须具备可比较性
     * @param e1
     * @param e2
     * @return
     */
    private int compare(E e1, E e2) {
        if (null != comparator) {
            return comparator.compare(e1, e2);
        }else {
            return ((Comparable<E>)e1).compareTo(e2);
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("BinaryHeap的 size = " + size + ", 容量为 capacity = " + elements.length + ", 元素为 [");
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
