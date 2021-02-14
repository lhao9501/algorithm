package com.algorithm;

public class ArrayList<E> {

    private int size;
    private E[] elements;

    // 默认 容量
    private static final int CAPACITY_DEFAULT = 10;

    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList(int capacity) {
        capacity = capacity < CAPACITY_DEFAULT ? CAPACITY_DEFAULT : capacity;
        elements = (E[]) new Object[capacity];
    }

    public ArrayList() {
        this(CAPACITY_DEFAULT);
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 默认添加到数组的末尾
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    /**
     * 添加到数组的指定位置
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        // 检查范围
        rangeCheckForAdd(index);

        // 是否扩容   在原来的元素数量基础上+1  看是否需要扩容
        ensureCapacity(size+1);

        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }

        elements[index] = element;
        size++;
    }

    /**
     * 是否包含某元素
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 获取该元素的索引值
     * @param element
     * @return
     */
    public int indexOf(E element) {
        if (null == element) {   // 是否有null 元素
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) return i;
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }


    /**
     * 获取元素
     * @param index
     * @return
     */
    public E get(int index) {
        rangeCheck(index);

        return elements[index];
    }

    /**
     * 设置元素
     * @param index
     * @param element
     * @return
     */
    public E set(int index, E element) {
        rangeCheck(index);

        E oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    /**
     * 删除 索引处的元素
     * @param index
     * @return
     */
    public E remove(int index) {
        E oldElement = get(index);
        for (int i = index + 1; i < size; i++) {
            elements[i-1] = elements[i];
        }
        elements[size - 1] = null;
        size--;

        // 是否缩容
        trimCapacity();

        return oldElement;
    }

    /**
     * 清空数组元素
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
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
     * 检查边界
     * @param index
     */
    private void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("传递的index = " + index + "不正确！");
        }
    }

    /**
     * 是否扩容
     * @param capacity
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        // 如果添加元素之后还小于原始容量  不进行扩容
        if (capacity < oldCapacity) return;

        // 扩容为原来的1.5倍
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    /**
     * 数组缩容
     */
    private void trimCapacity() {
        int oldCapacity = elements.length;

        // 如果元素个数大于等于原容量的一半  或者当前容量小于默认容量  不需要缩容
        // 这里的等于：如果没有等于  那说明在等于一半的时候就要缩容为一半，那么在缩容完后再添加的时候肯定要扩容
        if (size >= oldCapacity >> 1 || oldCapacity < CAPACITY_DEFAULT) return;

        int newCapacity = oldCapacity >> 1;
        E[] newElements = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("ArrayList的 size = " + size + ", 容量为 capacity = " + elements.length + ", 元素为 [");
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
