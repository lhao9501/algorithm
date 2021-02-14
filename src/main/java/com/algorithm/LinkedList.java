package com.algorithm;

public class LinkedList<E> {
    private int size;
    private Node<E> first;
    private Node<E> last;
    private static final int ELEMENT_NOT_FOUND = -1;


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E get(int index) {
        return node(index).element;
    }

    public void add(E element) {
        add(size, element);
    }

    /**
     * 添加节点元素
     *      当两边没有数据时  默认在最后添加数据  所以在最后添加和第一次添加在一起考虑
     *      当在0处添加和普通添加时   在一起考虑
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (index == size) {   // 在最后添加元素  考虑size==0时
            Node<E> oldLast = last;
            last = new Node<>(element, oldLast, null);
            if (null == oldLast) {  // 如果没有元素
                first = last;
            }else {
                oldLast.next = last;
            }

        }else {
            // 下一元素
            Node<E> next = node(index);
            // 前一元素
            Node<E> prev = next.prev;  // 0处元素的prev=null
            Node<E> newNode = new Node<>(element, prev, next);
            next.prev = newNode;
            if (null == prev) {  // 在0处添加
                first = newNode;
            }else {
                prev.next = newNode;
            }
        }

        size++;
    }


    /**
     * 删除元素
     * @param index
     * @return
     */
    public E remove(int index) {
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        // 删除0、最后、普通节点  一共分为 处理前驱prev、后继next两种情况
        if (null == prev) {  // 0处删除
            first = next;
        }else {
            prev.next = next;
        }

        if (null == next) {   // 最后元素 删除
            last = prev;
        }else {
            next.prev = prev;
        }

        size--;

        return node.element;
    }


    /**
     * 查找该元素的索引值
     * @param element
     * @return
     */
    public int indexOf(E element) {
        Node<E> node = first;
        if (null == element) {
            for (int i = 0; i < size; i++) {
                if (null == node.element) return i;
                node = node.next;
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }

        return ELEMENT_NOT_FOUND;
    }

    /**
     * 清空链表
     */
    public void clear() {
        first = null;
        last = null;
        size = 0;
    }


    private Node<E> node(int index) {
        rangeCheck(index);

        if (index < (size >> 1)) {   // 从first开始搜索
            Node<E> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }

            return node;
        }else {  // 从last开始搜索
            Node<E> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }

            return node;
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node<E> node = first;
        str.append("LinkedList的 size = " + size + ", 元素为 [");
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                str.append(node.element);
            }else {
                str.append(" <-> " + node.element);
            }
            node = node.next;
        }
        str.append("]");

        return str.toString();
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("传递的index = " + index + "不正确！");
        }
    }


    /**
     * 检查边界 添加
     * @param index
     */
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("传递的index = " + index + "不正确！");
        }
    }

    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }


    // ========== 双向循环链表  add、remove 有变化 =====================================

    public void  add_circle(E element) {
        add_circle(size, element);
    }

    /**
     *
     * @param index
     * @param element
     */
    public void add_circle(int index, E element) {
        rangeCheckForAdd(index);

        if (index == size) {
            Node<E> node = new Node<>(element, last, first);
            if (size == 0) {   // 元素为0个时
                first = node;
                last = node;
                node.next = node;
                node.prev = node;
            }else {
                last.next = node;
                first.prev = node;
                last = node;
            }
        }else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> newNode = new Node<>(element, prev, next);

            if (index == 0) {  // 0处添加元素
                /*last.next = newNode;
                newNode.prev = last;*/
                first = newNode;
            }else {
                prev.next = newNode;
                next.prev = newNode;
            }
        }
    }

    /**
     *
     * @param index
     * @return
     */
    public E remove_circle(int index) {
        rangeCheck(index);

        Node<E> node = node(index);  // 要删除的节点

        if (size == 1) {
            first = null;
            last = null;
        }else {
            Node<E> prev = node.prev;
            Node<E> next = node.next;

            prev.next = next;
            next.prev = prev;

            if (node == first) {
                first = next;
            }
            if (node == last) {
                last = prev;
            }
        }

        size--;

        return node.element;
    }




}
