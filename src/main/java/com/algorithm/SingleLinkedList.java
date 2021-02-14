package com.algorithm;

public class SingleLinkedList<E> {
    // 节点数量
    private int size;
    // 指向第一节点
    private Node<E> first;
    private static final int ELEMENT_NOT_FOUND = -1;


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 默认添加到最后一个
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 在指定位置添加元素
     *      1、第0位
     *      2、非0位
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (index == 0) {  // 第一个元素
            /*Node<E> newNode = new Node<>(element, first);
            newNode = first;*/
            first = new Node<>(element, first);
        }else {
            Node<E> prev = node(index - 1);
            /*Node<E> newNode = new Node<>(element, prev.next);
            newNode = prev.next;*/
            prev.next = new Node<>(element, prev.next);
        }

        size++;
    }

    public E get(int index) {
        return node(index).element;
    }

    public E set(int index, E element) {
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    /**
     * 删除指定位置元素
     *      1、第0位
     *      2、非0位
     * @param index
     * @return
     */
    public E remove(int index) {
        rangeCheck(index);

        // 要删除的节点
        Node<E> old = first;
        if (index == 0) {
            first = first.next;
        }else {
            Node<E> prev = node(index - 1);
            old = prev.next;
            prev.next = old.next;
        }

        size--;

        return old.element;
    }

    /**
     * 查询该元素的索引值
     * @param element
     * @return
     */
    public int indexOf(E element) {
        Node<E> node = first;

        if (null == element) {
            for (int i = 0; i < size; i++) {
                if (null == element)
                    return i;
                node = node.next;
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element))
                    return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    public void clear() {
        first = null;
        size = 0;
    }

    /**
     * 获取该index处的值
     * @param index
     * @return
     */
    private Node<E> node(int index) {
        rangeCheck(index);

        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }

        return node;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node<E> node = first;
        str.append("SingleLinkedList的 size = " + size + ", 元素为 [");
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                str.append(node.element);
            }else {
                str.append(" -> " + node.element);
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


    /**
     * 内部类   节点
     * @param <E>
     */
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }


    // ============ 单向循环链表   add  remove 有变化 =================================================

    public void add_circle(E element) {
        add_circle(size, element);
    }

    /**
     * 添加时  在0位置处特殊  尾节点需要指向0处节点
     * @param index
     * @param element
     */
    public void add_circle(int index, E element) {
        rangeCheckForAdd(index);

        if (index == 0) {
            Node<E> newFirst = new Node<>(element, first);
            Node<E> last = (size == 0) ? newFirst : node(size - 1);  // 考虑到没有元素时
            last.next = newFirst;
            first = newFirst;
        }else {
            Node<E> prev = node(index - 1);
            prev.next = new Node<>(element, prev.next);
        }

        size++;
    }


    /**
     *
     * @param index
     * @return
     */
    public E remove_circle(int index) {
        rangeCheck(index);

        Node<E> node = first;
        if (index == 0) {
            if (size == 0) {  // 考虑到没有元素时
                first = null;
            }else {
                first = node.next;
                node(size - 1).next = first;
            }
        }else {
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
        }

        size--;

        return node.element;
    }

}
