package com.sorting;

public class BubbleSort {

    /**
     * 相邻元素进行比较并交换
     *      最好 O(n)   最坏 O(n^2)   平均 O(n^2)
     *      空间 O(1)   原地排序    具有稳定性
     *
     * @param arraySort
     */
    public static void bubbleSort(int[] arraySort) {
        if (null == arraySort || arraySort.length <= 1) return;

        for (int j = 0; j < arraySort.length - 1; j++) {  // 外层表示 轮数 = 元素个数-1
            // 如果发现前一轮中没有进行元素的交换  说明都有序  不需要后续的轮数了  直接跳出循环
            boolean flag = false;

            for (int i = 0; i < arraySort.length - 1 - j; i++) {
                if (arraySort[i] > arraySort[i + 1]) {
                    int temp = arraySort[i];
                    arraySort[i] = arraySort[i + 1];
                    arraySort[i + 1] = temp;

                    flag = true;
                }

            }
            if (!flag) {
                break;
            }
        }
    }

    /**
     * 有可能序列后面的几个数字有顺序
     * @param arraySort
     */
    public static void bubbleSort3(int[] arraySort) {
        if (null == arraySort || arraySort.length <= 1) return;

        for (int end = arraySort.length - 1; end > 0; end--) {
            int sortedIndex = 1;
            for (int begin = 0; begin < end; begin++) {
                if (arraySort[begin] > arraySort[begin + 1]) {
                    int temp = arraySort[begin];
                    arraySort[begin] = arraySort[begin + 1];
                    arraySort[begin + 1] = temp;

                    sortedIndex = begin + 1;  // end--  这里要begin+1
                }
            }

            end = sortedIndex;
        }
    }
}
