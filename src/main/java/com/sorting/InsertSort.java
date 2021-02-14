package com.sorting;

/**
 * 插入排序
 *          分为已排序区间  未排序区间
 *
 *          最坏 O(n^2)   最好 O(n)    平均 O(n^2)
 */
public class InsertSort {

    public static void insertSort(int[] arraySort) {

        if (null == arraySort || arraySort.length <= 1) return;

        for (int i = 1; i < arraySort.length; i++) {
            int value = arraySort[i];

            int j = i - 1;
            for (; j >= 0; j--) {
                if (arraySort[j] > value) {
                    arraySort[j+1] = arraySort[j];
                }else {
                    break;
                }
            }
            arraySort[j+1] = value;
        }

    }


}
