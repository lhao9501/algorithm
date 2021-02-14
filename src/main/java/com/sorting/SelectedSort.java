package com.sorting;

public class SelectedSort {

    /**
     * 选择排序  在未排序区间中 默认选中第一个为最小min  然后找出最小的  最后进行交换
     *      最坏 O(n^2)   最好 O(n^2)    平均 O(n^2)
     *
     * @param arraySort
     */
    public static void selectedSort(int[] arraySort) {
        if (null == arraySort || arraySort.length <= 1) return;


        int min = 0;
        for (int i = 0; i < arraySort.length; i++) {
            min = i;

            for (int j = i + 1; j < arraySort.length; j++) {
                if (arraySort[j] < arraySort[min]) {
                    min = j;
                }
            }

            if (min != i) {
                int temp = arraySort[i];
                arraySort[i] = arraySort[min];
                arraySort[min] = temp;
            }
        }
    }
}
